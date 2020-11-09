<?php

function curl_post($url, $params)
{
  $ch = curl_init();

  curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
  curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
  curl_setopt($ch, CURLOPT_URL, $url);

  $data = array();

  foreach ($params as $key => $value)
  {
    $data[$key] = $value;
  }

  curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

  $contents = curl_exec($ch);

  curl_close($ch);

  return $contents;
}

function get_curl_result($url, $method = FALSE)
{
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
    curl_setopt($ch, CURLOPT_URL, $url);

    if ($method != 'GET')
    {
        $data = array();

        foreach ($_POST as $key => $value)
        {
                $data[$key] = $value;
        }

        curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    }

    $contents = curl_exec($ch);

    curl_close($ch);

    return $contents;
}

$table_list = get_curl_result('http://04cb7a5a.ngrok.io/api/service/batch/getTableList');
$table_list = json_decode($table_list);

$columns = array();
$user_info = array();

foreach ($table_list->table as $item)
{
  $arr = array(
    'dimensions' => array(),
    'metrics' => array()
  );

  $client_id = $item->api_key;
  $dimension_split = explode(',', $item->dimensions);
  $metric_split = explode(',', $item->metrics);
  $user_info[] = $item;

  foreach ($dimension_split as $dimension)
  {
    $arr['dimensions'][] = trim($dimension);
  }
  foreach ($metric_split as $metric)
  {
    $arr['metrics'][] = trim($metric);
  }

  $columns[] = $arr;
}

print_r($columns);
exit;



// Load the Google API PHP Client Library.
require_once __DIR__ . '/vendor/autoload.php';

$analytics = initializeAnalytics();

$j = 0;
foreach ($columns as $column)
{
  $response = getReport($analytics, $column);

  $ga_data = $response->reports[0]->data->rows;
  $new_ga_data = array();

  foreach ($ga_data as $item)
  {
    $arr = array();
    $i = 0;
    foreach ($item as $item2)
    {
      if ($i < count($column['dimensions']))
      {
        $arr[$column['dimensions'][$i]] = $item2;
      }
      else
      {
        $arr[$column['metrics'][$i - count($column['dimensions'])]] = $item2;
      }

      $new_ga_data[] = $arr;

      $i++;
    }
  }

  // data insert
  $param_data = (object) [
    'info' => json_encode($user_info[$j]),
    'gaData' => json_encode($new_ga_data)
  ];
  $params = array(
    'data' => $param_data
  );
  $result = curl_post('http://04cb7a5a.ngrok.io/api/service/batch/insertGAData', $params);

  $j++;
}

//$response = getReport($analytics);
//printResults($response);



/**
 * Initializes an Analytics Reporting API V4 service object.
 *
 * @return An authorized Analytics Reporting API V4 service object.
 */
function initializeAnalytics()
{

  // Use the developers console and download your service account
  // credentials in JSON format. Place them in this directory or
  // change the key file location if necessary.
  $KEY_FILE_LOCATION = __DIR__ . '/service-account-credentials.json';

  // Create and configure a new client object.
  $client = new Google_Client();
  $client->setApplicationName("Hello Analytics Reporting");
  $client->setAuthConfig($KEY_FILE_LOCATION);
  $client->setScopes(['https://www.googleapis.com/auth/analytics.readonly']);
  $analytics = new Google_Service_AnalyticsReporting($client);

  return $analytics;
}


/**
 * Queries the Analytics Reporting API V4.
 *
 * @param service An authorized Analytics Reporting API V4 service object.
 * @return The Analytics Reporting API V4 response.
 */
function getReport($analytics, $column) {

  // Replace with your view ID, for example XXXX.
  $VIEW_ID = "156511616";

  // Create the DateRange object.
  $dateRange = new Google_Service_AnalyticsReporting_DateRange();
  $dateRange->setStartDate("1daysAgo");
  $dateRange->setEndDate("1daysAgo");


  //Create the Dimensions object.
  $browsers = array();
  foreach ($column['dimensions'] as $dimension)
  {
    $browser = new Google_Service_AnalyticsReporting_Dimension();
    $browser->setName($dimension);
    $browsers[] = $browser;
  }

  // Create the Metrics object.
  $sessions = array();
  foreach ($column['metrics'] as $metric)
  {
    $session = new Google_Service_AnalyticsReporting_Metric();
    $session->setExpression($metric);
    $sessions[] = $session;
  }

  // Create the ReportRequest object.
  $request = new Google_Service_AnalyticsReporting_ReportRequest();
  $request->setViewId($VIEW_ID);
  $request->setDateRanges($dateRange);
  $request->setDimensions($browsers);
  $request->setMetrics($sessions);

  $body = new Google_Service_AnalyticsReporting_GetReportsRequest();
  $body->setReportRequests( array( $request) );
  return $analytics->reports->batchGet( $body );
}


/**
 * Parses and prints the Analytics Reporting API V4 response.
 *
 * @param An Analytics Reporting API V4 response.
 */
function printResults($reports) {
  for ( $reportIndex = 0; $reportIndex < count( $reports ); $reportIndex++ ) {
    $report = $reports[ $reportIndex ];
    $header = $report->getColumnHeader();
    $dimensionHeaders = $header->getDimensions();
    $metricHeaders = $header->getMetricHeader()->getMetricHeaderEntries();
    $rows = $report->getData()->getRows();

    for ( $rowIndex = 0; $rowIndex < count($rows); $rowIndex++) {
      $row = $rows[ $rowIndex ];
      $dimensions = $row->getDimensions();
      $metrics = $row->getMetrics();
      for ($i = 0; $i < count($dimensionHeaders) && $i < count($dimensions); $i++) {
        print($dimensionHeaders[$i] . ": " . $dimensions[$i] . "\n");
      }

      for ($j = 0; $j < count($metrics); $j++) {
        $values = $metrics[$j]->getValues();
        for ($k = 0; $k < count($values); $k++) {
          $entry = $metricHeaders[$k];
          print($entry->getName() . ": " . $values[$k] . "\n");
        }
      }
    }
  }
}

