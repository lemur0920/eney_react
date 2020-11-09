package eney.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import eney.domain.*;
import eney.service.KakaoService;
import eney.service.MessageService;
import eney.util.HttpRequestUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/service/kakao")
public class KakaoController {

    @Resource
    KakaoService kakaoService;
//    MessageService messageService;

    //    String baseUrl = "https://api.wideshot.co.kr/api/v1";
    //    final String apiKey = "YUhIR3dLQm9CdnlhRDlGYzM3cWNtUTlLQm9yRy8wcGpHd00vN3lLREU2ZElOUytKM1M1KzRzZ21zQjhCdnlZbWdtV2M3bjdGOGlvWUpsZHZOVFl6Y1E9PQ==";


    @RequestMapping(value = "/category/all", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getCategoryList(Authentication authentication) {

        String requestUrl = "/sender/category/all";

        String body = HttpRequestUtil.sejongKakaoGet(requestUrl, null, null);

        return new ResponseEntity<>(body, HttpStatus.OK);


    }


    @RequestMapping(value = "/sender/profileList", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getSenderProfileList(@RequestParam(value = "page", defaultValue = "1") Integer page, Authentication authentication) {

        System.out.println("profileList in ");
        KakaoSenderProfileVO kakaoSenderProfileVO = new KakaoSenderProfileVO();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        System.out.println("page :: " + page);

        kakaoSenderProfileVO.setUserid(user.getUserId());
        kakaoSenderProfileVO.setPageNo(page);
        System.out.println("kakaoSenderProfileVO :: " + kakaoSenderProfileVO);

        Map<String, Object> vo = kakaoService.getSenderProfileList(kakaoSenderProfileVO);
        System.out.println("vo :: " + vo);

        return new ResponseEntity<>(vo, HttpStatus.OK);


    }


    @RequestMapping(value = "/template/list", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getKakaoTemplateList(@RequestParam(value = "page", defaultValue = "1") Integer page, Authentication authentication) {

        KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO = new KakaoAlimtalkTemplateVO();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        kakaoAlimtalkTemplateVO.setUserid(user.getUserId());
        kakaoAlimtalkTemplateVO.setPageNo(page);

        System.out.println("page :: " + page);

        System.out.println("kakaoAlimtalkTemplateVO :: " + kakaoAlimtalkTemplateVO);
//        List<KakaoAlimtalkTemplateVO> vo = kakaoService.getAlimtalkTemplateList(kakaoAlimtalkTemplateVO);

        Map<String, Object> map = kakaoService.getAlimtalkTemplateList(kakaoAlimtalkTemplateVO);


        System.out.println("map :: " + map);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }


    @RequestMapping(value = "/sender/token", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAuthToken(@RequestParam(value = "yellowId") String yellowId, @RequestParam(value = "phoneNumber") String phoneNumber, Authentication authentication) {

        String requestUrl = "/sender/token";
        Map<String, Object> param = new HashMap<>();

        param.put("yellowId", yellowId);
        param.put("phoneNumber", phoneNumber);

        String body = HttpRequestUtil.sejongKakaoGet(requestUrl, param, null);
        System.out.println("body :: " + body);

        return new ResponseEntity<>(body, HttpStatus.OK);


    }


    @RequestMapping(value = "/sender/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> createPlusFriend(@RequestParam(value = "yellowId") String yellowId, @RequestParam(value = "phoneNumber") String phoneNumber,
                                       @RequestParam(value = "token") String token, @RequestParam(value = "categoryCode") String categoryCode,
                                       Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String requestUrl = "/sender/create";

        Map<String, Object> param = new HashMap<>(); // parameter map
        param.put("yellowId", yellowId);
        param.put("categoryCode", categoryCode);

        Map<String, Object> header = new HashMap<>(); // header map
        header.put("phoneNumber", phoneNumber);
        header.put("token", token);

        JsonObject body = HttpRequestUtil.sejongKakaoPost(requestUrl, param, header);
        System.out.println("body ::: ");
        System.out.println(body);


//        JsonObject code = HttpRequestUtil.createSjRes()

//        String code = new JsonParser().parse(body).getAsJsonObject().get("code").getAsString();
        Map<String, Object> returnBody = new HashMap<>();

        if (body.get("code").getAsString().equals("200")) {
            JsonObject resObj = body.getAsJsonObject().get("data").getAsJsonObject();
            Map<String, Object> profile = new HashMap<>();
            profile.put("userid", user.getUserId());
            profile.put("sender_key", resObj.get("senderKey").getAsString());
            profile.put("uuid", resObj.get("uuid").getAsString());
            profile.put("name", resObj.get("name").getAsString());
            profile.put("created_at", resObj.get("createdAt").getAsString());

            kakaoService.insertSenderProfile(profile);
            returnBody.put("code", "200");
            returnBody.put("message", "등록되었습니다.");
        } else {
            System.out.println(body);
            returnBody.put("code", body.getAsJsonObject().get("code").getAsString());
            returnBody.put("message", body.getAsJsonObject().get("message").getAsString());
//            e24517f41c3a970fe668aea43306f3474855b7ef
        }


        return new ResponseEntity<>(returnBody, HttpStatus.OK);

    }


    @RequestMapping(value = "/sender/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> deletePlusFriend(@RequestParam(value = "senderKey") String senderKey, Authentication authentication) {

        String requestUrl = "/sender/delete";
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        Map<String, Object> param = new HashMap<>(); // parameter map
        param.put("senderKey", senderKey);

        JsonObject data = HttpRequestUtil.sejongKakaoPost(requestUrl, param, null);
        Map<String, Object> body;

        if (data.get("code").getAsString().equals("200")) {
            Map<String, Object> deleteParam = new HashMap<>();
            deleteParam.put("userid", user.getUserId());
            deleteParam.put("senderKey", senderKey);

            kakaoService.deleteSenderProfile(deleteParam);
            System.out.println("data" + data);
            body = HttpRequestUtil.createSjRes(data.get("code").getAsString(), "프로필 삭제 완료");
        } else {
            body = HttpRequestUtil.createSjRes(data.get("code").getAsString(), data.get("message").getAsString());
        }

        System.out.println("body ::: ");
        System.out.println(body);

        return new ResponseEntity<>(body, HttpStatus.OK);

    }

    @RequestMapping(value = "/template/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> createKakaoTemplate(@RequestBody KakaoTemplateVO kakaoTemplateVO, Authentication authentication) {

        String requestUrl = "/template/create";
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();


        Map<String, Object> param = new HashMap<>(); // parameter map
        param.put("senderKey", kakaoTemplateVO.getSenderKey());
        param.put("templateName", kakaoTemplateVO.getTemplateName());
        param.put("templateContent", kakaoTemplateVO.getTemplateContent());

        for (int i = 0; i < kakaoTemplateVO.getButtons().size(); i++) {

            KakaoAttachVO attach = kakaoTemplateVO.getButtons().get(i);
            System.out.println(" attach :: " + attach);

            System.out.println(" i :: " + i);
            String key = "buttons[" + i + "]";

            param.put(key + ".name", attach.getName());
            param.put(key + ".linkType", attach.getLinkType());
            param.put(key + ".ordering", i + 1);
            switch (attach.getLinkType()) {
                case "WL":
                    if (!attach.getLinkMo().equals("")) param.put(key + ".linkMo", attach.getLinkMo());
                    if (!attach.getLinkPc().equals("")) param.put(key + ".linkPc", attach.getLinkPc());
                case "AL":
                    if (!attach.getLinkAnd().equals("")) param.put(key + ".linkAnd", attach.getLinkAnd());
                    if (!attach.getLinkIos().equals("")) param.put(key + ".linkIos", attach.getLinkIos());
                    break;

                default:
                    break;
            }

        }

        JsonObject body = HttpRequestUtil.sejongKakaoPost(requestUrl, param, null);
        Map<String, Object> resBody = new HashMap<>();
        System.out.println("body ::: " + body);

        if (body.get("code").getAsString().equals("200")) {

            JsonObject resObj = body.get("data").getAsJsonObject();
            Map<String, Object> template = new HashMap<>();
            template.put("userid", user.getUserId());
            template.put("uuid", kakaoTemplateVO.getUuid());
            template.put("name", kakaoTemplateVO.getName());
            template.put("template_code", resObj.get("templateCode").getAsString());
            template.put("template_name", resObj.get("templateName").getAsString());
            template.put("template_content", resObj.get("templateContent").getAsString());
            template.put("sender_key", resObj.get("senderKey").getAsString());
            kakaoService.insertKakaoTemplate(template);
            resBody.put("code", body.get("code").getAsString());
            resBody.put("message", "메세지를멀러하지");


        } else {
            resBody.put("code", body.get("code").getAsString());
            resBody.put("message", body.get("message").getAsString());

        }

        System.out.println("resBody ::: " + resBody);

        return new ResponseEntity<>(resBody, HttpStatus.OK);

    }


    @RequestMapping(value = "/template/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> deleteKakaoTemplate(@RequestParam(value = "senderKey") String senderKey, @RequestParam(value = "templateCode") String templateCode, Authentication authentication) {

        String requestUrl = "/template/delete";
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        Map<String, Object> param = new HashMap<>(); // parameter map
        param.put("senderKey", senderKey);
        param.put("templateCode", templateCode);

        JsonObject data = HttpRequestUtil.sejongKakaoPost(requestUrl, param, null);
//        Map<String, Object> body = new HashMap<>();
        Map<String, Object> body;

        System.out.println(data.get("code").getAsString());

        if (data.get("code").getAsString().equals("200")) {
            Map<String, Object> deleteParam = new HashMap<>();
            deleteParam.put("userid", user.getUserId());
            deleteParam.put("senderKey", senderKey);

//            kakaoService.deleteSenderProfile(deleteParam);
            body = HttpRequestUtil.createSjRes(data.get("code").getAsString(), "템플릿 삭제 완료");

//            body.put("code", data.get("code").getAsString());
//            body.put("message", "프로필 삭제 완료");
        } else {
//            body = HttpRequestUtil.createSjRes(data.get("code").getAsString(), "프로필 삭제 완료");
            body = HttpRequestUtil.createSjRes(data.get("code").getAsString(), data.get("message").getAsString());

//            body.put("code", data.get("code").getAsString());
//            body.put("message", data.get("message").getAsString());
        }

        System.out.println("data ::: " + data);
        System.out.println("body ::: ");
        System.out.println(body);
        System.out.println(body.getClass().getName());

        return new ResponseEntity<>(body, HttpStatus.OK);

    }

    @RequestMapping(value = "/template/update", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> updateKakaoTemplate(@RequestBody KakaoTemplateVO kakaoTemplateVO, Authentication authentication) {

        String requestUrl = "/template/update";
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();


        Map<String, Object> param = new HashMap<>(); // parameter map
        System.out.println("template/update :: " + kakaoTemplateVO.toString());
        param.put("senderKey", kakaoTemplateVO.getSenderKey());
        param.put("templateCode", kakaoTemplateVO.getTemplateCode());
        param.put("newTemplateName", kakaoTemplateVO.getTemplateName());
        param.put("newTemplateContent", kakaoTemplateVO.getTemplateContent());

        for (int i = 0; i < kakaoTemplateVO.getButtons().size(); i++) {

            KakaoAttachVO attach = kakaoTemplateVO.getButtons().get(i);
            System.out.println(" attach :: " + attach);

            String key = "buttons[" + i + "]";

            param.put(key + ".name", attach.getName());
            param.put(key + ".linkType", attach.getLinkType());
            param.put(key + ".ordering", i + 1);
            switch (attach.getLinkType()) {
                case "WL":
                    if (!attach.getLinkMo().equals("")) param.put(key + ".linkMo", attach.getLinkMo());
                    if (!attach.getLinkPc().equals("")) param.put(key + ".linkPc", attach.getLinkPc());
                case "AL":
                    if (!attach.getLinkAnd().equals("")) param.put(key + ".linkAnd", attach.getLinkAnd());
                    if (!attach.getLinkIos().equals("")) param.put(key + ".linkIos", attach.getLinkIos());
                    break;

                default:
                    break;
            }

        }

        JsonObject body = HttpRequestUtil.sejongKakaoPost(requestUrl, param, null);
        Map<String, Object> resBody = new HashMap<>();
        System.out.println("body ::: " + body);

        /*if (body.get("code").getAsString().equals("200")) {

            JsonObject resObj = body.get("data").getAsJsonObject();
            Map<String, Object> template = new HashMap<>();
            template.put("userid", user.getUserId());
            template.put("uuid", kakaoTemplateVO.getUuid());
            template.put("name", kakaoTemplateVO.getName());
            template.put("template_code", resObj.get("templateCode").getAsString());
            template.put("template_name", resObj.get("templateName").getAsString());
            template.put("template_content", resObj.get("templateContent").getAsString());
            template.put("sender_key", resObj.get("senderKey").getAsString());
            kakaoService.insertKakaoTemplate(template);
            resBody.put("code", body.get("code").getAsString());
            resBody.put("message", "메세지를멀러하지");


        } else {
            resBody.put("code", body.get("code").getAsString());
            resBody.put("message", body.get("message").getAsString());

        }*/

        System.out.println("resBody ::: " + resBody);

        return new ResponseEntity<>(resBody, HttpStatus.OK);

    }


    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getKakaoTemplate(@RequestParam(value = "senderKey") String senderKey, @RequestParam(value = "templateCode") String templateCode, Authentication authentication) {

        String requestUrl = "/template";
        Map<String, Object> param = new HashMap<>();

        param.put("senderKey", senderKey);
        param.put("templateCode", templateCode);

        String body = HttpRequestUtil.sejongKakaoGet(requestUrl, param, null);
        System.out.println("body :: " + body);

        return new ResponseEntity<>(body, HttpStatus.OK);


    }
}
