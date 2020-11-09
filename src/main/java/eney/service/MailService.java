package eney.service;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
//import org.springframework.ui.velocity.VelocityEngineUtils;

import eney.domain.MailManagerVo;
import eney.domain.ServicePatchcallOtherVO;
import eney.domain.ServicePatchcallVO;
import eney.domain.ServiceWebHostingVO;
import eney.domain.UserVO;
import eney.mapper.MailDao;

@Service
public class MailService {
    private static final String fromAddr = "help@eney.co.kr";
    private static final String fromName = "주식회사 에네이";
    /*
    * localhost 테스트 시
    * src.main.java.CommonConfig.java 설정 추가
    * */

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);


    @Resource
    private JavaMailSenderImpl mailSender;
    @Autowired
    //private VelocityEngine velocityEngine;

    @Resource
    private MailDao mailDao;

    /**
     * 템플릿 메일 전송
     *
     * @param userInfo 사용자 정보
     * @param title    메일 제목
     * @param templet  템플릿명
     * @param model    템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(UserVO userInfo, String title, String templet, Map<String, Object> model) {
        String emailAddress = (userInfo.getEmail() != null) ? userInfo.getEmail() : userInfo.getEmail_address();

        return sendTempletMail(emailAddress, userInfo.getName(), title, templet, model);
    }

    /**
     * 템플릿 메일 전송 (웹호스팅)
     *
     * @param userInfo IDC 호스팅 서비스 신청자 정보
     * @param title    메일 제목
     * @param templet  템플릿명
     * @param model    템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(ServiceWebHostingVO userInfo, String title, String templet, Map<String, Object> model) {
        String emailAddress = userInfo.getPublish_email();

        return sendTempletMail(emailAddress, userInfo.getUserid(), title, templet, model);
    }

    /**
     * 템플릿 메일 전송 (패치콜)
     *
     * @param userInfo IDC 호스팅 서비스 신청자 정보
     * @param title    메일 제목
     * @param templet  템플릿명
     * @param model    템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(ServicePatchcallVO userInfo, String title, String templet, Map<String, Object> model) {
        String emailAddress = userInfo.getPublish_email();

        return sendTempletMail(emailAddress, userInfo.getUserid(), title, templet, model);
    }

    /**
     * 템플릿 메일 전송 (대표번호,080수신자부담번호)
     *
     * @param userInfo IDC 호스팅 서비스 신청자 정보
     * @param title    메일 제목
     * @param templet  템플릿명
     * @param model    템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(ServicePatchcallOtherVO userInfo, String title, String templet, Map<String, Object> model) {
        String emailAddress = userInfo.getPublish_email();

        return sendTempletMail(emailAddress, userInfo.getUserid(), title, templet, model);
    }

    /**
     * 템플릿 메일 전송 (웹호스팅)
     * <p>
     * //     * @param userInfo IDC 호스팅 서비스 신청자 정보
     *
     * @param title   메일 제목
     * @param templet 템플릿명
     * @param model   템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(String title, String templet, Map<String, Object> model) {
        String emailAddress = "sungjoonlee@eney.co.kr";

        return sendTempletMail(emailAddress, "관리자", title, templet, model);
    }


    /**
     * 템플릿 메일 전송
     *
     * @param userMailAddress 받는사람 이메일 주소
     * @param userName        받는사람 이름
     * @param title           메일 제목
     * @param templet         템플릿명
     * @param model           템플릿에서 사용되는 오브젝트 맵
     */
    public Boolean sendTempletMail(String userMailAddress, String userName, String title,
                                   String templet, Map<String, Object> model) {

        boolean returnflag = false;

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/email/");

        StringWriter stringWriter = new StringWriter();
        try {
            Template template = cfg.getTemplate(templet + ".ftl", "UTF-8"); //
            template.process(model, stringWriter);
            String templateContent = stringWriter.toString();

            returnflag = sendMail(userMailAddress, userName, title, templateContent);


        } catch (Exception e) {
            logger.error("[메일 전송] 실패 - 메일 생성중 예상치 않은 오류가 발생하였습니다.."
                    + "(ExceptionMessage: " + e.getMessage()
                    + ", userMailAddress: " + userMailAddress
                    + ", userName: " + userName
                    + ", title: " + title
                    + ", templet: " + templet + ")");
        }

        return returnflag;
    }

    /**
     * 메일 전송 (첨부파일 없음)
     *
     * @param userMailAddress 받는사람 이메일 주소
     * @param userName        받는사람 이름
     * @param title           메일 제목
     * @param content         메일 내용 (HTML)
     */
    public Boolean sendMail(String userMailAddress, String userName, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(fromAddr, fromName, "UTF-8"));
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(new InternetAddress(userMailAddress, userName, "UTF-8"));
            helper.setSubject(title);
            helper.setText(content, true);

            try {
                mailSender.send(message);
                logger.info("[메일 전송] 성공 "
                        + "(userMailAddress: " + userMailAddress
                        + ", userName: " + userName
                        + ", title: " + title
                        + ", content: " + content + ")");
                return true;
            } catch (MailException ex) {
                logger.error("[메일 전송] 실패 - 전송중 오류가 발생하였습니다."
                        + "(ExceptionMessage: " + ex.getMessage()
                        + ", userMailAddress: " + userMailAddress
                        + ", userName: " + userName
                        + ", title: " + title
                        + ", content: " + content + ")");
            }
        } catch (UnsupportedEncodingException | MessagingException e) {
            logger.error("[메일 전송] 실패 - 메일 생성중 오류가 발생하였습니다.."
                    + "(ExceptionMessage: " + e.getMessage()
                    + ", userMailAddress: " + userMailAddress
                    + ", userName: " + userName
                    + ", title: " + title
                    + ", content: " + content + ")");
        } catch (Exception e) {
            logger.error("[메일 전송] 실패 - 메일 생성중 예상치 않은 오류가 발생하였습니다.."
                    + "(ExceptionMessage: " + e.getMessage()
                    + ", userMailAddress: " + userMailAddress
                    + ", userName: " + userName
                    + ", title: " + title
                    + ", content: " + content + ")");
        }
        return false;
    }

    /** 관리자 메일 발송 관련 부분 **/

    /**
     * 사용자에게 보낼 메일 작성
     *
     * @param mailInfo 보낼 메일 정보
     * @param userInfo 작성자 계정 정보
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Integer insertMailManagerInfo(MailManagerVo mailInfo, UserVO userInfo) {
        mailInfo.setMail_creat_userid(userInfo.getUserid());
        mailInfo.setMail_send(false);

        Integer mail_idx = mailDao.insertMailManagerInfo(mailInfo);

        return mail_idx;
    }

    /**
     * 사용자에게 보낼 메일 수정
     *
     * @param mailInfo 수정할 메일정보
     * @param userInfo 사용자 정보
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Integer updateMailMangerInfo(MailManagerVo mailInfo, UserVO userInfo) {
        return mailDao.updateMailManagerInfo(mailInfo);
    }

    /**
     * 메일 관리 정보 상세보기
     *
     * @param selectQuery
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MailManagerVo selectMailManagerInfo(MailManagerVo selectQuery) {
        return mailDao.selectMailManagerInfo(selectQuery);
    }

    /**
     * 메일 관리 정보 리스트 조회
     *
     * @param selectQuery
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MailManagerVo> selectMailManagerInfoList(MailManagerVo selectQuery) {
        selectQuery.setTotal_item_num(mailDao.getMailManagerInfoCnt(selectQuery));
        return mailDao.selectMailManagerInfoList(selectQuery);
    }

    /**
     * 메일 미리보기
     *
     * @param mailIdx  메일 IDX
     * @param userInfo 사용자 정보
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MailManagerVo mailPreview(Integer mailIdx, UserVO userInfo) {
        MailManagerVo mailInfo = mailDao.selectMailManagerInfo(mailIdx);
        if (mailInfo == null || mailInfo.getMail_idx() == null) {
            /* 메일 관리 정보가 없는 경우 */
            return null;
        }

        mailInfo.setMail_content(mailContentReplace(mailInfo.getMail_content(), userInfo));

        return mailInfo;
    }

    /**
     * 메일 관리 정보로 사용자에게 메일 전송
     *
     * @param mailIdx  메일 IDX
     * @param userInfo
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Boolean sendMailByMangerInfo(Integer mailIdx, UserVO userInfo) {

        System.out.println("SEND MAIL BY MANAGER INFO _ PROGRESS 01 :: ");
        MailManagerVo mailInfo = mailDao.selectMailManagerInfo(mailIdx);

        if (mailInfo == null || mailInfo.getMail_idx() == null) {
            /* 메일 관리 정보가 없는 경우 */
            return false;
        }

        if (mailInfo.getMail_send() == true) {
            /* 이미 메일을 보낸 경우 */
            return false;
        }
        UserVO targetSelectQuery = new UserVO();
        if (MailManagerVo.MAIL_TYPE_AD.equals(mailInfo.getMail_type())) {
            targetSelectQuery.setEmail_agree(true);
        }
        targetSelectQuery.setMember_kind("ALL".equals(mailInfo.getMail_target_group()) ? null : mailInfo.getMail_target_group());

        /* 대상자 정보 조회 */
        List<UserVO> targetUserInfoList = mailDao.getMailTargetInfo(targetSelectQuery);

        /* 메일 발송 */
        int seccessCount = 0;
        int errorCount = 0;
        for (UserVO userInfoEach : targetUserInfoList) {
            if (userInfoEach.getEmail() != null) {
                if (sendMail(userInfoEach.getEmail(), userInfoEach.getUsername(),
                        mailInfo.getMail_title(), mailContentReplace(mailInfo.getMail_content(), userInfoEach))) {
                    seccessCount++;
                    System.out.println("SEND MAIL BY MANAGER INFO __ SUCCESS :: " + seccessCount);
                } else {
                    errorCount++;
                    System.out.println("SEND MAIL BY MANAGER INFO __ FAILED :: " + errorCount);
                }
            }
        }

        MailManagerVo updateMailManagerInfoQuery = new MailManagerVo();
        updateMailManagerInfoQuery.setMail_idx(mailInfo.getMail_idx());
        updateMailManagerInfoQuery.setMail_send(true);
        updateMailManagerInfoQuery.setMail_send_userid(userInfo.getUserid());
        updateMailManagerInfoQuery.setMail_success_count(seccessCount);
        updateMailManagerInfoQuery.setMail_error_count(errorCount);

        mailDao.updateMailManagerInfo(updateMailManagerInfoQuery);

        System.out.println("DB :: " + mailDao.updateMailManagerInfo(updateMailManagerInfoQuery));

        logger.info("[비정기 메일 전송] 완료"
                + "(updateMailManagerInfoQuery: " + updateMailManagerInfoQuery
                + ", mailInfo: " + mailInfo
                + ", userInfo: " + userInfo + ")");
        return true;
    }

    /**
     * 사용자에 맞는 메일 내용 생성
     *
     * @param mailContent 메일 폼
     * @param userInfo    사용자 정보
     * @return 사용자 내용이 들어간 메일 내용
     */
    public String mailContentReplace(String mailContent, UserVO userInfo) {
        String replaceMailContent = mailContent.toString();

        replaceMailContent = replaceMailContent.replace("!{이름}", userInfo.getName());
        replaceMailContent = replaceMailContent.replace("!{아이디}", userInfo.getUserid());
        replaceMailContent = replaceMailContent.replace("!{회사명}", (userInfo.getCompany_name() == null) ? "" : userInfo.getCompany_name());

        return replaceMailContent;
    }
}
