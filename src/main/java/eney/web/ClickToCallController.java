package eney.web;
import eney.service.ClickToCallService;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/ClickToCall")
@Controller
public class ClickToCallController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Resource
    private ClickToCallService clickTocallService;

    @ResponseBody
    @RequestMapping(value="/keywordData", method=RequestMethod.POST)
    public void getGAKeywordData(){
//        clickTocallService.runGAKeyWordDataBatch();
        System.out.println("실행됨");

    }

    @ResponseBody
    @RequestMapping(value="/getReferer", method=RequestMethod.GET)
    public void getReferer(HttpServletRequest request){
        System.out.println(request.getHeader("REFERER"));
        System.out.println("실행됨");

    }



}
