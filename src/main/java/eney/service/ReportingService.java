package eney.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;        
import java.util.List;

import javax.annotation.Resource;

import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;

import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.OrderBy;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

@Service
public class ReportingService extends Exception{
	
	private static final Logger logger = LoggerFactory.getLogger(ReportingService.class);
	private static final String APPLICATION_NAME = "patchcallBI";
	//development
	private static final String KEY_FILE_LOCATION = "C:/var/keystore/analyticstest-e31cd6bab2a7.json";
	//beta , production 
	//private String KEY_FILE_LOCATION = "/usr/local/keystore/analyticstest-e31cd6bab2a7.json";
	private static final String VIEW_ID = "103612926";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	
	@Resource
	ReportingService reportingService;
	
	/***
	 * TODO 하루 한번 24시 기준으로 전날까지 기록된 GA 데이터에 대해 insert
	 * @return
	 */
	
	//@Scheduled(cron="5 * * * * *")
	public void getloadData(){	
		
		
		try {
			AnalyticsReporting service = initializeAnalyticsReporting();
		    GetReportsResponse response = getReport(service);
		    reportingService.printResponse(response);
	    	logger.info("[GA 데이터 기록 확인] " 

	    			);
		    
		    
		}catch(Exception e) {
		}
		
		
		
		//List<Map<String, Object>> res = new LinkedList<>();
		
		//return res;
	}
	
	  /**
	   * Analytics Reporting API V4 서비스 개체를 초기화
	   * 
	   * @return An authorized Analytics Reporting API V4 service object.
	   * @throws IOException
	   * @throws GeneralSecurityException
	   */
	public AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

	    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    GoogleCredential credential = GoogleCredential
	        .fromStream(new FileInputStream(KEY_FILE_LOCATION))
	        .createScoped(AnalyticsReportingScopes.all());

	    // Construct the Analytics Reporting service object.
	    return new AnalyticsReporting.Builder(
	    		httpTransport, JSON_FACTORY, credential)
	        .setApplicationName(APPLICATION_NAME).build();
	  }
	
	  /**
	   * Queries the Analytics Reporting API V4.
	   *
	   * @param service An authorized Analytics Reporting API V4 service object.
	   * @return GetReportResponse The Analytics Reporting API V4 response.
	   * @throws IOException
	   */
	  
	  private static GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
		  	
		  
		    // Create the DateRange object.
		    DateRange dateRange = new DateRange();
		    dateRange.setStartDate("Today");
		    dateRange.setEndDate("Today");

		    //Expressions = 
		    List<String> Expressions= new ArrayList<>();
		    Expressions.add("organic");
		    Expressions.add("not provided");
		    Expressions.add("not set");
		    
		    
		    
		    
		    // Create the Metrics object.
		    Metric sessions = new Metric()
		        .setExpression("ga:sessions")
		        .setAlias("sessions");
		   
		    //Dimension setting
		    Dimension Keyword = new Dimension().setName("ga:keyword");
		    Dimension ops = new Dimension().setName("ga:operatingSystem");
		    Dimension source = new Dimension().setName("ga:source");
		    Dimension SocialNetwork = new Dimension().setName("ga:socialNetwork");
		    
		    //Dimension Filter
		    
		  //Medium 관련 Filter 1 선언
		    DimensionFilter mediumFilter = new DimensionFilter()
		    		.setDimensionName("ga:source")
		    		.setOperator("EXACT")
		    		.setExpressions(Arrays.asList("organic"));
		    
		    //Filter 2
		    DimensionFilter EventFilter = new DimensionFilter()
		    		.setDimensionName("ga:eventLabel")
		    		.setOperator("EXACT")
		    		.setExpressions(Arrays.asList("3X/1ptBRz"));
		    
		    //Filter 3
		    DimensionFilter socialNetworkFilter = new DimensionFilter()
		    		.setDimensionName("ga:socialNetwork")
		    		.setOperator("EXACT")
		    		.setExpressions(Arrays.asList("(not set)"));
		  //Dimension Filter Clause 선언
		    DimensionFilterClause KeywordFilterClause = new DimensionFilterClause()
		    		.setFilters(Arrays.asList(EventFilter))
		    		.setFilters(Arrays.asList(mediumFilter));
		    
		    DimensionFilterClause SocialNetworkFilterClause = new DimensionFilterClause()
		    		.setFilters(Arrays.asList(EventFilter))
		    		.setFilters(Arrays.asList(socialNetworkFilter));
		    
		    DimensionFilterClause opsFilterClause = new DimensionFilterClause()
		    		.setFilters(Arrays.asList(EventFilter));
		    
		  //Session을 기준으로 내림차순 배열
		    OrderBy ordering = new OrderBy()
		    	    .setFieldName("ga:sessions")
		    	    .setSortOrder("DESCENDING");
		    ReportRequest socialnetworkfromEvent = new ReportRequest()
		            .setViewId(VIEW_ID)
		            .setDateRanges(Arrays.asList(dateRange))
		            .setMetrics(Arrays.asList(sessions))
		            .setDimensions(Arrays.asList(SocialNetwork))
		            .setDimensionFilterClauses(Arrays.asList(SocialNetworkFilterClause)) // Dimension Filter caluses (DFC) 사용 선언
		            .setOrderBys(Arrays.asList(ordering)); // order
		    
		    ReportRequest requestops = new ReportRequest()
		            .setViewId(VIEW_ID)
		            .setDateRanges(Arrays.asList(dateRange))
		            .setMetrics(Arrays.asList(sessions))
		            .setDimensions(Arrays.asList(source))
		            .setDimensionFilterClauses(Arrays.asList(opsFilterClause)) // Dimension Filter caluses (DFC) 사용 선언
		            .setOrderBys(Arrays.asList(ordering)); // order
		    

		    ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		    requests.add(requestops);

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
	  private static void printResponse(GetReportsResponse response) {

	    for (Report report: response.getReports()) {
	      ColumnHeader header = report.getColumnHeader();
	      List<String> dimensionHeaders = header.getDimensions();
	      List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
	      List<ReportRow> rows = report.getData().getRows();

	      if (rows == null) {
	         System.out.println("No data found for " + VIEW_ID);
	         return;
	      }

	      for (ReportRow row: rows) {
	        List<String> dimensions = row.getDimensions();
	        List<DateRangeValues> metrics = row.getMetrics();

	        for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
	          System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
	        }

	        for (int j = 0; j < metrics.size(); j++) {
	          System.out.print("Date Range (" + j + "): ");
	          DateRangeValues values = metrics.get(j);
	          for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
	            System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
	          }
	        }
	      }
	    }
	  }
	}