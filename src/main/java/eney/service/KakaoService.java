package eney.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eney.domain.KakaoAlimtalkTemplateVO;
import eney.domain.KakaoSenderProfileVO;
import eney.mapper.KakaoDao;
import eney.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KakaoService {

    @Resource
    KakaoDao kakaoDao;


    public void insertKakaoTemplate(Map<String, Object> template) {
        kakaoDao.insertKakaoTemplate(template);
    }

    public void insertSenderProfile(Map<String, Object> profile) {
        kakaoDao.insertSenderProfile(profile);
    }

    public void deleteSenderProfile(Map<String, Object> param) {
        kakaoDao.deleteSenderProfile(param);
    }

    public Map<String, Object> getSenderProfileList(KakaoSenderProfileVO kakaoSenderProfileVO) {
        Map<String, Object> map = new HashMap<>();


        kakaoSenderProfileVO.setPageSize(10);
        kakaoSenderProfileVO.setTotalCount(kakaoDao.getSenderProfileListCount(kakaoSenderProfileVO));
        List<KakaoSenderProfileVO> kakaoSenderProfileList = kakaoDao.getSenderProfileList(kakaoSenderProfileVO);
        System.out.println("kakaoSenderProfileList" + kakaoSenderProfileList.toString());


        map.put("list", kakaoSenderProfileList);
        map.put("page", kakaoSenderProfileVO);


//        Map<String, Object> map = new HashMap<>();

/*        customerEventVO.setPageSize(9999);
        customerEventVO.setTotalCount(customerDao.getCustomerEventCount(customerEventVO));

        List<CustomerEventVO> customerEventList = customerDao.getCustomerEventList(customerEventVO);

        map.put("list", customerEventList);
        map.put("page", customerEventVO);*/

        return map;
//        return kakaoDao.getSenderProfileList(kakaoSenderProfileVO);
    }

    /*public List<KakaoAlimtalkTemplateVO> getAlimtalkTemplateList(KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO) {
        return kakaoDao.getAlimtalkTemplateList(kakaoAlimtalkTemplateVO);
    }*/

    public Map<String, Object> getAlimtalkTemplateList(KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO) {
        Map<String, Object> map = new HashMap<>();

        kakaoAlimtalkTemplateVO.setPageSize(10);
        kakaoAlimtalkTemplateVO.setTotalCount(kakaoDao.getAlimtalkTemplateListCount(kakaoAlimtalkTemplateVO));
        List<KakaoAlimtalkTemplateVO> vo = kakaoDao.getAlimtalkTemplateList(kakaoAlimtalkTemplateVO);

        for (int i = 0; i < vo.size(); i++) {


            Map<String, Object> param = new HashMap<>();
            param.put("senderKey", vo.get(i).getSender_key());
            param.put("templateCode", vo.get(i).getTemplate_code());
            String body = HttpRequestUtil.sejongKakaoGet("/template", param, null);

//            JsonObject res_json = new JsonObject();
            System.out.println(body);
//            JsonObject res_json = new JsonParser().parse(body).getAsJsonObject().get("data").getAsJsonObject();
            try {
                JsonObject res_json = new JsonParser().parse(body).getAsJsonObject().get("data").getAsJsonObject();
                vo.get(i).setInspectionStatus(res_json.get("inspectionStatus").getAsString());
                vo.get(i).setStatus(res_json.get("status").getAsString());
                vo.get(i).setCreatedAt(res_json.get("createdAt").getAsString());
                vo.get(i).setModifiedAt(res_json.get("modifiedAt").getAsString());
            } catch (NullPointerException e) {
                System.out.println("삭제된 데이터");
            }

        }
        map.put("list", vo);
        map.put("page", kakaoAlimtalkTemplateVO);


        return map;

//        return kakaoDao.getAlimtalkTemplateList(kakaoAlimtalkTemplateVO);
    }

    public KakaoAlimtalkTemplateVO getAlimtalkTemplateListCount (KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO) {

        System.out.println("begin kakaoAlimtalkTemplateVO :: " + kakaoAlimtalkTemplateVO);


        kakaoAlimtalkTemplateVO.setPageSize(10);
        kakaoAlimtalkTemplateVO.setTotalCount(kakaoDao.getAlimtalkTemplateListCount(kakaoAlimtalkTemplateVO));

        System.out.println("return kakaoAlimtalkTemplateVO :: " + kakaoAlimtalkTemplateVO);


        return kakaoAlimtalkTemplateVO;


    }
}
