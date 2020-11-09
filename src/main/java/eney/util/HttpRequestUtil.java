package eney.util;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {

    //post 요청(json data)
    public static Map<String, Object> sendHttpPost(String requestURL, JsonObject json, String token, String openStackToken) throws ClientProtocolException, IOException {
        String body = "";

        Map<String, Object> res = new HashMap<>();
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL 새성
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Content-Type", "application/json");

            if (token != null) {
                postRequest.addHeader("Authorization", token); // token 이용시
            }

            if (openStackToken != null) {
                postRequest.addHeader("X-Auth-Token", openStackToken); // token 이용시
            }

            postRequest.setEntity(new StringEntity(json.toString())); //json 메시지 입력

            HttpResponse response = client.execute(postRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201 || response.getStatusLine().getStatusCode() == 202) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                body = handler.handleResponse(response);
                res.put("response", response);
                res.put("body", body);
                System.out.println(body);
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return res;
    }

    //get 요청(json data)
    public static Map<String, Object> sendHttpGet(String requestURL, String param, String token, String openStackToken) {
        String body = "";
        Map<String, Object> res = new HashMap<>();
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성

            String url = requestURL;

            if (param != null) {
                url = url + "/" + param;
            }
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
//            getRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력
            if (token != null) {
                getRequest.addHeader("Authorization", token); // token 이용시
            }
            if (openStackToken != null) {
                getRequest.addHeader("X-Auth-Token", openStackToken); // token 이용시
            }


            HttpResponse response = client.execute(getRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                body = handler.handleResponse(response);
                res.put("response", response);
                res.put("body", body);

                System.out.println(res);
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return res;

    }

    //get 요청(json data)
    public static Map<String, Object> sendHttpDelete(String requestURL, String param, String token, String openStackToken) {
        String body = "";
        Map<String, Object> res = new HashMap<>();
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성

            String url = requestURL;

            if (param != null) {
                url = url + "/" + param;
            }
            HttpDelete deleteRequest = new HttpDelete(url); //GET 메소드 URL 생성
//            deleteRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력
            if (token != null) {
                deleteRequest.addHeader("Authorization", token); // token 이용시
            }
            if (openStackToken != null) {
                deleteRequest.addHeader("X-Auth-Token", openStackToken); // token 이용시
            }


            System.out.println(deleteRequest);
            HttpResponse response = client.execute(deleteRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                body = handler.handleResponse(response);
                res.put("response", response);
                res.put("body", body);

                System.out.println(res);
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return res;

    }


    //sejong kakao api get 요청
    public static String sejongKakaoGet(String requestUrl, Map<String, Object> param, Map<String, Object> header) {

        String baseUrl = "https://api-dev.wideshot.co.kr/api/v1"; // 개발서버
        final String apiKey = "aWhMWDBGd3RtZU1ac2tLWXVLWm4vTFFYWTVXS2s4czMrMnNFa21abkl2U21PNkxTTzRuUE9HUWJGSjg4RDc2WjNsejYrd0RVTlNRS1RJWTFDd1YzSnc9PQ=="; // dev api

        String url = baseUrl + requestUrl;


        String res = "";

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성

            URIBuilder builder = new URIBuilder(url);
            if (param != null) { // 파라미터 URI ADD

                for (String key : param.keySet()) {

                    Object value = param.get(key);

//                    System.out.println(key + " : " + value.getClass().getName());
                    builder.setParameter(key, value.toString());

                }

            }

//            System.out.println("url :: " + url);
            HttpGet getRequest = new HttpGet(builder.build()); //GET 메소드 URL 생성
            getRequest.addHeader("sejongApiKey", apiKey); //KEY 입력

            if (header != null) {

                for (String key : header.keySet()) {

                    Object value = header.get(key);

//                    System.out.println(key + " : " + value.getClass().getName());
                    getRequest.addHeader(key, value.toString());

                }

            }


            HttpResponse response = client.execute(getRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                res = handler.handleResponse(response);

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return res;

    }


    //sejong kakao api post 요청
    public static JsonObject sejongKakaoPost(String requestUrl, Map<String, Object> param, Map<String, Object> header) {

        String baseUrl = "https://api-dev.wideshot.co.kr/api/v1"; // 개발서버
        final String apiKey = "aWhMWDBGd3RtZU1ac2tLWXVLWm4vTFFYWTVXS2s4czMrMnNFa21abkl2U21PNkxTTzRuUE9HUWJGSjg4RDc2WjNsejYrd0RVTlNRS1RJWTFDd1YzSnc9PQ=="; // dev api

        String url = baseUrl + requestUrl;


        JsonObject res_json = new JsonObject();

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성

            URIBuilder builder = new URIBuilder(url);
            if (param != null) { // 파라미터 URI ADD

                for (String key : param.keySet()) {

                    Object value = param.get(key);

                    System.out.println(key + " : " + value);
                    builder.setParameter(key, value.toString());

                }

            }

            System.out.println("url :: " + url);
            HttpPost postRequest = new HttpPost(builder.build()); //GET 메소드 URL 생성
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.addHeader("sejongApiKey", apiKey); //KEY 입력

            if (header != null) {

                for (String key : header.keySet()) {

                    Object value = header.get(key);

                    System.out.println(key + " : " + value.getClass().getName());
                    postRequest.addHeader(key, value.toString());

                }

            }


            HttpResponse response = client.execute(postRequest);
            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
//                res = handler.handleResponse(response);
                res_json =  new JsonParser().parse(handler.handleResponse(response)).getAsJsonObject();

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return res_json;

    }


    public static Map<String, Object> createSjRes(String code, String message) {

        Map<String, Object> returnRes = new HashMap<>();

        returnRes.put("code", code);
        returnRes.put("message", message);


        return returnRes;
    }
}