package eney.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import eney.domain.*;
import eney.exception.MalFormedDataException;
import eney.exception.PaymentLackException;
import eney.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eney.domain.MessageVO;
import eney.exception.MalFormedDataException;
import eney.exception.PaymentLackException;
import eney.mapper.MessageDao;
import eney.mapper.SupplyDao;
import eney.mapper.UserDao;
import eney.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessageService {

    public static final String ADDR_ENEY_OFFICE = "07079331616";

    @Resource
    MessageDao messageDao;
    @Resource
    SupplyDao supplyDao;
    @Resource
    UserDao userDao;
    @Resource
    SupplyService supplyService;
    @Resource
    UserService userService;
    @Resource
    PaymentService paymentService;

    /**
     * 회원가입시 인증번호 생성
     *
     * @return 6자리 인증번호
     */
    private String createRandomNumber() {
        int a = (int) (Math.random() * 10);
        int b = (int) (Math.random() * 10);
        int c = (int) (Math.random() * 10);
        int d = (int) (Math.random() * 10);
        int e = (int) (Math.random() * 10);
        int f = (int) (Math.random() * 10);
        String res = "" + a + b + c + d + e + f;
        return res;
    }

    /**
     * SMS 전송
     */
    public void sendMsg(MessageVO messageVO) {
        messageDao.pushMessage(messageVO);
    }

    public Map<String, String> getMsgType(MessageVO messageVO) {
        String type = "";
        Integer charge_point = 0;
        Map<String, String> map = new HashMap<>();

        switch (messageVO.getMsg_type()) {
            case "1":
                type = "SMS";
                charge_point = 20;
                break;
            case "3":
                if (Integer.parseInt(messageVO.getFilecnt()) > 0) {
                    type = "MMS";
                    charge_point = 100;
                } else {
                    type = "LMS";
                    charge_point = 30;
                }
                break;
            case "6":
                type = "K_AL";
                charge_point = 8;
                break;

            case "7":
                if (Integer.parseInt(messageVO.getFilecnt()) > 0) {
                    type = "K_FR_I";
                    charge_point = 25;
                } else {
                    type = "K_FR_T";
                    charge_point = 17;
                }
                break;
        }

        map.put("type", type);
        map.put("charge_point", String.valueOf(charge_point));

        return map;
    }

    public void sendSmsEpontCharge(MessageVO messageVO) {

        //msg_type
        //1 : SMS , 3 : MMS (file 없을 시 LMS)
        //6 : 알림톡, 7:친구톡

        Map<String, String> map = getMsgType(messageVO);

        try {

            paymentService.deductEpointForMsg(map);

        } catch (PaymentLackException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("message push");
        messageDao.pushMessage(messageVO);

    }

    /**
     * 본인인증번호 보내기
     *
     * @param msgVO
     */
    public void sendCertifyMsg(MessageVO msgVO) {
        String randomNumber = createRandomNumber();
        msgVO.setExt_col2(randomNumber);
        msgVO.setCallback("07079331616");
        msgVO.setText("[주식회사 에네이] \n본인인증번호는 \n" + randomNumber + " 입니다.\n정확히\n입력해주세요.");
        messageDao.pushMessage(msgVO);
    }

    /**
     * 회원가입 시 인증번호 전송
     *
     * @param msgVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> checkCertifyNum(MessageVO msgVO) throws Exception {
        Map<String, Object> res = new HashMap<>();
        String check = "SUCCESS";
        String yyyymm = DateUtil.getTodateString().substring(0, 6);
        msgVO.setYyyymm(yyyymm);
        MessageVO queue = messageDao.getMessageQueue(msgVO);
        MessageVO result = messageDao.getMessageResult(msgVO);


        if (msgVO.getExt_col2().equals(result.getExt_col2()) || msgVO.getExt_col2().equals(queue.getExt_col2())) {

            long diff = 0;
            if (msgVO.getExt_col2().equals(result.getExt_col2()))
                diff = DateUtil.diffOfTime(result.getRequest_time(), DateUtil.getTodateString("yyyy-MM-dd HH:mm:ss"));
            else if (msgVO.getExt_col2().equals(queue.getExt_col2()))
                diff = DateUtil.diffOfTime(queue.getRequest_time(), DateUtil.getTodateString("yyyy-MM-dd HH:mm:ss"));

            System.out.println("*********diff***********\n" + diff);
            if (diff > 180) {
                check = "FAIL:TIMEOUT";
            } else {
                try {
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("data1", msgVO.getExt_col3());
                    paramMap.put("data2", msgVO.getDstaddr());
                    res.put("key", getCertificationKey(paramMap));
                } catch (MalFormedDataException e) {
                    check = "FAIL:MALFOREMEDDATA";
                }
            }

        } else {
            check = "FAIL:NOT_CORRESPOND";
        }
        res.put("check", check);
        return res;
    }

    /**
     * 일반 회원 가입시 생일과 휴대폰번호 확인
     *
     * @param paramMap
     * @return
     * @throws MalFormedDataException
     */
    public String getCertificationKey(Map<String, String> paramMap) throws MalFormedDataException {
        String birthDay = paramMap.get("data1").replaceAll("-", "");
        String phoneNum = paramMap.get("data2").replaceAll("-", "");
        if (birthDay.length() != 8) {
            throw new MalFormedDataException("ext_col3");
        }

        char[] birthDay_arr = birthDay.toCharArray();
        char[] arr = new char[]{birthDay_arr[6], birthDay_arr[0], birthDay_arr[4], birthDay_arr[1], birthDay_arr[3], birthDay_arr[7], birthDay_arr[2], birthDay_arr[5]};
        String transformedVal = String.valueOf(arr);

        //String key_ref = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";

        String key_ref2 = "MLPNKObjiVHUCgyxftzdrsEaWQ";
        String key_ref3 = "QWErtyUIOpzXCVBNmasdFghjkl";


        String res = new String();


        for (int i = 0; i < transformedVal.length(); i++) {
            int idx = transformedVal.charAt(i);
            idx *= idx;
            idx = idx % 26;
            res += key_ref2.charAt(idx);
        }

        for (int i = 0; i < phoneNum.length(); i++) {
            int idx = phoneNum.charAt(i);
            idx *= idx;
            idx *= idx;
            idx = idx % 26;
            res += key_ref3.charAt(idx);
        }

        return res;
    }


    public List<Map<String, String>> getMessageList(Map<String, String> map) {
        return messageDao.getMessageList(map);
    }

    public Map<String, Object> getMessageTemplateList(MessageTemplateVO messageTemplateVO) {

        messageTemplateVO.setPageSize(10);
        messageTemplateVO.setTotalCount(messageDao.getMessageTemplateListCnt(messageTemplateVO));
        Map<String, Object> map = new HashMap<>();

        List<MessageTemplateVO> agentList = messageDao.getMessageTemplateList(messageTemplateVO);

        map.put("list", agentList);
        map.put("page", messageTemplateVO);

        return map;
    }

    public MessageTemplateVO getMessageTemplateDetail(int idx) {

        Map<String, Object> map = new HashMap<>();

        MessageTemplateVO agentList = messageDao.getMessageTemplateDetail(idx);

        return agentList;
    }

    public int deleteMessageTemplate(int idx) {

        int resultReturn = messageDao.deleteMessageTemplate(idx);

        return resultReturn;
    }


    public String insertMessageTemplateList(MessageTemplateVO messageTemplateVO) {

        String res = null;

        try {
            System.out.println("try insert template");
            messageDao.insertMessageTemplateList(messageTemplateVO);
        } catch (Exception e) {
            res = "템플릿 등록에 실패하였음";
        } finally {
            System.out.println("res :: " + res);
            return res;
        }
    }

    public String updateMessageTemplateList(MessageTemplateVO messageTemplateVO) {

        String res = null;

        int isSuccess = messageDao.updateMessageTemplateList(messageTemplateVO);

        if (isSuccess == 0) res = "템플릿 수정에 실패하였음";

        return res;
    }

    public Map<String, Object> getMessageResultList(Map<String, Object> map) {

        MsgResultVO msgResultVO = new MsgResultVO();

//        msgResultVO.setPageSize(10);
//        System.out.println("map ::: " + map);

//        msgResultVO.setTotalCount(messageDao.getMessageResultListCnt(map));
//        System.out.println("msgResultVO.getTotalCount() :: " + msgResultVO.getTotalCount());


        PageVO pageVO = new PageVO();
        pageVO.setPageSize(10);
        pageVO.setPageNo(Integer.parseInt(map.get("pageNo").toString()));
        pageVO.setTotalCount(messageDao.getMessageResultListCnt(map));

        map.put("startRowNo", pageVO.getStartRowNo());
        map.put("endRowNo", pageVO.getEndRowNo());

        Map<String, Object> resultMap = new HashMap<>();

        List<MsgResultVO> agentList = messageDao.getMessageResultList(map);


        resultMap.put("list", agentList);
        resultMap.put("page", pageVO);

        return resultMap;
    }

    public Map<String, Object> webSendSMS(List<WebSmsSendVO> webSmsSendVOList) {

        Map<String, Object> resultMap = new HashMap<>();
        int point = 0;
        int success = 0;
        int failed = 0;
        System.out.println("try insert template");
        System.out.println(webSmsSendVOList.size());
        for (int i = 0; i <= webSmsSendVOList.size() - 1; i++) {
            System.out.println("for :: " + i);
            System.out.println(webSmsSendVOList.get(i));
            int result = messageDao.webSendSMS(webSmsSendVOList.get(i));
            System.out.println(result);
            if (0 < result) {
                point += result;
                success++;
            } else {
                failed++;
            }

        }

        resultMap.put("point", point);
        resultMap.put("success", success);
        resultMap.put("failed", failed);


        return resultMap;
    }


    public List<Map> getMsgResultTableList() {

//        Map<String,String> map = new HashMap<>();
        List<Map> map = messageDao.getMsgResultTableList();

        return map;
    }


    public Map<String, Object> getBlockNumberList(BlockNumberVO blockNumberVO) {

//        Map<String,String> map = new HashMap<>();
        blockNumberVO.setPageSize(10);
        blockNumberVO.setTotalCount(messageDao.getBlockNumberListCnt(blockNumberVO));

        Map<String, Object> map = new HashMap<>();

        List<BlockNumberVO> list = messageDao.getBlockNumberList(blockNumberVO);
        map.put("list", list);
        map.put("page", blockNumberVO);


        return map;
    }


}
