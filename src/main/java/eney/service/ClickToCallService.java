package eney.service;

import eney.domain.*;
import eney.mapper.AnalyticsDao;
import eney.mapper.CustomerDao;
import eney.mapper.IvrDao;
import eney.mapper.SupplyDao;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.*;
import eney.domain.GAKeyWordLogVO;
import eney.domain.GAKeyWordVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service
public class ClickToCallService {

    @Resource
    private SupplyDao supplyDao;
    @Resource
    private IvrDao ivrDao;
    @Resource
    private CustomerDao customerDao;

    @Resource
    private AnalyticsDao analyticsDao;

    private final String APPLICATION_NAME = "Hello Analytics Reporting";
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private final String KEY_FILE_LOCATION = "/Users/eney/Documents/client_secret.json";
    //개발
    private final String KEY_FILE_LOCATION = "/Users/eney/Documents/google_ga_key.json";
    //운영
//    private final String KEY_FILE_LOCATION = "/server_data/google_ga_key.json";
    private String searchDate = "";
    private String searchMonth = "";


    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Java cron job expression:: " + strDate);
    }

    @Scheduled(cron=" 0 20 22 * * *")
    public void runGAKeyWordDataBatch() {

        Calendar c1 = new GregorianCalendar();
        System.out.println(c1.get(Calendar.HOUR));

        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷
        SimpleDateFormat sdf02 = new SimpleDateFormat ( "MM");

        this.searchDate = sdf.format(c1.getTime()); // String으로 저장
        this.searchMonth = sdf02.format(c1.getTime());

        List<GAViewIdVO> viewIdList = analyticsDao.getGAViewIdList();

        for(int i=0; i<= viewIdList.size()-1;i++){

            String userId = viewIdList.get(i).getUserId();
            String viewId = viewIdList.get(i).getViewId();

            try {

                AnalyticsReporting service = initializeAnalyticsReporting();
                GetReportsResponse response = getReport(service,viewId);

                insertGAKeywordData(response,userId,viewId);

            } catch (Exception e) {

                GAKeyWordLogVO gaKeyWordLogVO = new GAKeyWordLogVO();
                gaKeyWordLogVO.setLog(e.getMessage());
                gaKeyWordLogVO.setViewId(viewId);

                System.out.println("===로그로그");
                System.out.println(gaKeyWordLogVO);



                analyticsDao.insertGAKeywordDataLog(gaKeyWordLogVO);
                e.printStackTrace();
                continue;
            }

        }
    }

    private AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
                .createScoped(AnalyticsReportingScopes.all());

        // Construct the Analytics Reporting service object.
        return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    private GetReportsResponse getReport(AnalyticsReporting service, String viewId) throws IOException {
        // Create the DateRange object.
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(this.searchDate);
        dateRange.setEndDate(this.searchDate);
//        dateRange.setStartDate("2019-05-10");
//        dateRange.setEndDate("2019-05-27");

        // Create the Metrics object.
        Metric totalEvents = new Metric()
                .setExpression("ga:totalEvents")
                .setAlias("totalEvents");

        Dimension[] dimensionArray = new Dimension[7];

        Dimension eventCategory = new Dimension().setName("ga:eventCategory");
        Dimension eventLabel = new Dimension().setName("ga:eventLabel");
        Dimension keyword = new Dimension().setName("ga:keyword");
        Dimension browser = new Dimension().setName("ga:browser");
        Dimension os = new Dimension().setName("ga:operatingSystem");
        Dimension source = new Dimension().setName("ga:source");
        Dimension deviceCategory = new Dimension().setName("ga:deviceCategory");
        Dimension city = new Dimension().setName("ga:city");
        Dimension date = new Dimension().setName("ga:date");


        dimensionArray[0] = eventCategory;
        dimensionArray[1] = eventLabel;
        dimensionArray[2] = keyword;
        dimensionArray[3] = browser;
        dimensionArray[4] = os;
        dimensionArray[5] = source;
        dimensionArray[6] = date;

        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setMetrics(Arrays.asList(totalEvents))
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


    private void insertGAKeywordData(GetReportsResponse response, String userId, String usedViewId) {

        List<GAKeyWordVO> gaKeyWordVOList = new ArrayList<>();

        int count = 0;

        for (Report report: response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows == null) {

                GAKeyWordLogVO gaKeyWordLogVO = new GAKeyWordLogVO();
                gaKeyWordLogVO.setLog("No data found for");
                gaKeyWordLogVO.setViewId(usedViewId);

                analyticsDao.insertGAKeywordDataLog(gaKeyWordLogVO);


                System.out.println("No data found for " + usedViewId);
                return;
            }

            System.out.println(rows);

            for (ReportRow row: rows) {

                GAKeyWordVO gaKeyWordVO = new GAKeyWordVO();

                List<String> dimensions = row.getDimensions();

                List<DateRangeValues> metrics = row.getMetrics();

                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                    //Dimension
                    System.out.println(dimensions.get(i));
//                    if(i == 0){
//                        String vno = dimensions.get(i);
//                        gaKeyWordVO.setVno(vno);
//                    }else if (i == 1){
//                        String keyWord = dimensions.get(i);
//
//                        if(keyWord.equals("(not provided)")){
//                            gaKeyWordVO.setKeyword("추적 불가");
//                        }else{
//                        gaKeyWordVO.setKeyword(keyWord);
//                        }
//                    }

                    switch (i){
                        case 0 : String vno = dimensions.get(i);
                            gaKeyWordVO.setVno(vno);
                            break;
                        case 1 : String label = dimensions.get(i);
                            gaKeyWordVO.setSearchDate(label);
                            break;
                        case 2 : String keyWord = dimensions.get(i);
                            gaKeyWordVO.setKeyword(keyWord);
                            break;
                        case 3 : String browser = dimensions.get(i);
                            gaKeyWordVO.setBrowser(browser);
                            break;
                        case 4 : String os = dimensions.get(i);
                            gaKeyWordVO.setOs(os);
                            break;
                        case 5 : String source = dimensions.get(i);
                            gaKeyWordVO.setSource(source);
                            break;
                        case 6 : String deviceCategory = dimensions.get(i);
                            gaKeyWordVO.setDevice_category(deviceCategory);
                            break;
                        case 7 : String city = dimensions.get(i);
                            gaKeyWordVO.setCity(city);
                            break;
                        case 8 : String date = dimensions.get(i);
//                            gaKeyWordVO.setSearchDate(date);
                            break;
                    }

                }

//                for (int j = 0; j < metrics.size(); j++) {
//                    System.out.print("Date Range (" + j + "): ");
//                    DateRangeValues values = metrics.get(j);
//                    for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
//                        //Metric
//                        int keywordCnt = Integer.parseInt(values.getValues().get(k));
//                        count += keywordCnt;
//                        gaKeyWordVO.setKeywordCnt(keywordCnt);
//                        System.out.println(values.getValues().get(k));
//                    }
//                }

//                gaKeyWordVO.setSearchDate(this.searchDate.replaceAll("-","" ));
                gaKeyWordVO.setUserId(userId);

                gaKeyWordVO.setUsedViewId(usedViewId);

                gaKeyWordVOList.add(gaKeyWordVO);
            }

            try{
                analyticsDao.insertGAKeywordData(gaKeyWordVOList);
                customerDao.updateCustomerEventWithGA(gaKeyWordVOList);

                GAKeyWordLogVO gaKeyWordLogVO = new GAKeyWordLogVO();
                gaKeyWordLogVO.setLog("GA Keyword List Insert Success");
                gaKeyWordLogVO.setViewId(usedViewId);

                System.out.println(gaKeyWordVOList);

                analyticsDao.insertGAKeywordDataLog(gaKeyWordLogVO);


//                Map<String, Object> tmpMap = new HashMap<String, Object>();
//                tmpMap.put("month", this.searchMonth);
//                tmpMap.put("searchDate", this.searchDate);
//                tmpMap.put("vno", gaKeyWordVOList.get(0).getVno());

//                CallLogVO callLogVO = ivrDao.getCallLog(tmpMap);

//                System.out.println("콜로그 조회!!!");
//                System.out.println(callLogVO);
//
//                if(callLogVO != null){
//
//                }


            }catch (Exception e){

                GAKeyWordLogVO gaKeyWordLogVO = new GAKeyWordLogVO();
                gaKeyWordLogVO.setLog("GA Keyword List Insert Fail" + e.getMessage());
                gaKeyWordLogVO.setViewId(usedViewId);

                System.out.println(e);

                analyticsDao.insertGAKeywordDataLog(gaKeyWordLogVO);

            }

        }
    }


}
