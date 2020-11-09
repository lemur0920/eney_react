package eney.web;


import eney.domain.AcsTransmitVO;
import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.domain.XTechApply;
import eney.service.FileService;
import eney.service.XTechApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/apply")
public class XTechApplyController {

    @Autowired
    XTechApplyService xTechApplyService;

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/x_tech", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> insertXTechForm(XTechApply xTechApply, HttpServletRequest request)  throws IOException, ParseException {
        Map<String, Object> map = new HashMap<String, Object>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        String date = format.format(currentTime);

        String end_dt = "2020-09-14 00:00:00.0";

        Date endDate = format.parse(end_dt);
        Date todate = format.parse(date);

        int compare = todate.compareTo(endDate);

        if (compare == 1 || compare == 0) {
            try{
                String [] names = new String[1];
                names[0] = xTechApply.getCompanyName()+todate;
                fileService.processUpload(xTechApply.getPlan_file(), "X_TECH", 0, request,names);
            }catch(Exception e){
                map.put("success", false);
                map.put("msg", "에러 발생. 관리자에게 문의하세요");
                System.out.println("처음 신청 실패");
                return new ResponseEntity<>(map, HttpStatus.OK);

            }
            xTechApply.setData();
            xTechApplyService.XtechApplySave(xTechApply);
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else if (compare == -1) {
            map.put("success", false);
            map.put("msg", "접수기간이 아닙니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            System.out.println("신청 실패");
            map.put("success", false);
            map.put("msg", "에러 발생. 관리자에게 문의하세요");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

    }
}
