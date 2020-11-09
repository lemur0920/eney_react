package eney.util;

import eney.domain.ServiceBIVO;
import eney.service.BatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.*;

import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;

import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Component
public class GABatch {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private BatchService batchService;

    private static final String APPLICATION_NAME = "Eney Patchcall BI";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    public String getKeyFile(String file_name) throws Exception {
        Path path = fileUploadUtil.getFileUploadPath();

        path = path.resolve("ga");
        path = path.resolve(file_name);

        return String.valueOf(path.toUri().getPath());

    }




    /**
     * Initializes an Analytics Reporting API V4 service object.
     *
     * @return An authorized Analytics Reporting API V4 service object.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public AnalyticsReporting initializeAnalyticsReporting(String key_file) throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(key_file))
                .createScoped(AnalyticsReportingScopes.all());

        // Construct the Analytics Reporting service object.
        return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Queries the Analytics Reporting API V4.
     *
     * @param service An authorized Analytics Reporting API V4 service object.
     * @return GetReportResponse The Analytics Reporting API V4 response.
     * @throws IOException
     */
    public GetReportsResponse getReport(AnalyticsReporting service, Map<String, Object> map, String view_id) throws IOException {
        // Create the DateRange object.
        DateRange dateRange = new DateRange();
        dateRange.setStartDate("2018-10-01");
        dateRange.setEndDate("2018-10-09");

        String dimensionStr = (String)map.get("dimensions");
        dimensionStr.replaceAll(" ", "");
        String metricStr = (String) map.get("metrics");
        metricStr.replaceAll(" ", "");

        String[] metricList = metricStr.split(",");
        Metric[] metricArray = new Metric[metricList.length];

        for(int i = 0 ; i < metricList.length ; i ++){
            Metric metric = new Metric().setExpression(metricList[i]);
            metricArray[i] = metric;
        }

        String[] dimensionList = dimensionStr.split(",");
        Dimension[] dimensionArray = new Dimension[dimensionList.length];
        for(int j = 0; j < dimensionList.length ; j ++){
            Dimension dimension = new Dimension().setName(dimensionList[j]);
            dimensionArray[j] = dimension;
        }

        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(view_id)
                .setDateRanges(Arrays.asList(dateRange))
                .setMetrics( Arrays.asList(metricArray))
                .setDimensions(Arrays.asList(dimensionArray));

        ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
        requests.add(request);

        // Create the GetReportsRequest object.
        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(requests);

        // Call the batchGet method.
        GetReportsResponse response = service.reports().batchGet(getReport).execute();

        // Return the response.
        return response;
    }

    /**
     * Parses and prints the Analytics Reporting API V4 response.
     *
     * @param response An Analytics Reporting API V4 response.
     */
    public  void printResponse(GetReportsResponse response, Map<String, Object> table) throws Exception{
        for (Report report: response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            Integer day_value = 0;
            String compare_date = "";
            String value_key = "";
            String cal_keyword = "";

            List<String> key_list = new ArrayList<String>();
            String headerName = "";
            Integer nullvalue = 0;

            String[] compare = {
                    "1.일", "1.주","1.1개월","1.3개월","1.6개월","1.년",
                    "2.비교_1일", "2.비교_7일","2.비교_1개월","2.비교_3개월","2.비교_6개월","2.비교_1년",
                    "3.증감_1일","3.증감_7일","3.증감_1개월","3.증감_3개월","3.증감_6개월","3.증감_1년",
                    "4.평균_7일","4.평균_1개월","4.평균_3개월","4.평균_6개월","4.평균_1년"
            };

            if (rows == null) {
                System.out.println(table.get("table_name")+"No data found ");
                return;
            }

            for (ReportRow row : rows) {

                LinkedHashMap map = new LinkedHashMap();

                List<String> dimensions = row.getDimensions();
                List<DateRangeValues> metrics = row.getMetrics();

                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {

                    map.put(dimensionHeaders.get(i), dimensions.get(i));

                    if(dimensionHeaders.get(i).equals("ga:date")){
                        compare_date = dimensions.get(i);
                    }else if(dimensionHeaders.get(i).equals("ga:deviceCategory")){
                        cal_keyword = dimensions.get(i);
                    }
                }

                Map<String,Object> searchMap = new HashMap<>();
                for (int j = 0; j < metrics.size(); j++) {
                    DateRangeValues values = metrics.get(j);
                    for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
                        map.put(metricHeaders.get(k).getName(), values.getValues().get(k));

                        if(Arrays.asList(batchService.gaValueArray()).contains(metricHeaders.get(k).getName())){
                            key_list.add(metricHeaders.get(k).getName());
                        }

                    }
                }

                batchService.insertGAData(table, map);
                if(table.get("using_compare").equals("using")){
                    for(int l = 0; l < compare.length; l ++){

                        List<String> list =  new ArrayList<String>();
                        map.put("통계값", compare[l]);

                        map.put("날짜_비교일", DateUtil.getDateString(DateUtil.getStringToDate(compare_date), "yyyy-MM-dd"));

                        searchMap.put("date", compare_date);
                        searchMap.put("key_list", key_list);
                        searchMap.put("key_value", String.valueOf(day_value));
                        searchMap.put("cal_name", compare[l]);
                        searchMap.put("cal_keyword", cal_keyword);
                        searchMap.put("table_name", (String) table.get("table_name"));

                        if(!compare[l].equals("1.일")){
                            Map<String,Object> result = batchService.getCompareValue(searchMap);

                            Map<String,Object> resultValue = (Map<String, Object>) result.get("value");

                            if(resultValue == null || resultValue.isEmpty()){
                                 list = (List<String>) searchMap.get("key_list");

                                for ( int i = 0 ; i < list.size(); i ++) {
                                    if(batchService.getGAMap().containsKey(list.get(i))){
                                        map.put(batchService.gaValue(list.get(i)), 0);
                                    }

                                }

                            }else{
                                list = new ArrayList<String>(resultValue.keySet());

                                for(int i = 0 ; i < list.size(); i ++){
                                    if(batchService.getGAMap().containsKey(list.get(i))){
                                        map.put(batchService.gaValue(list.get(i)), resultValue.get(list.get(i)));
                                    }
                                }
                            }
                            if(!result.get("start_date").equals("") || !(result.get("start_date") == null)){
                                map.put("날짜_비교일", result.get("start_date"));
                            }

                        }
                        batchService.insertGAData(table, map);
                        list.clear();
                    }
                }else{

                }

            }
        }
    }
}