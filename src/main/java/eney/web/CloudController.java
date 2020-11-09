package eney.web;

import com.google.gson.JsonObject;
import eney.domain.OpenStackInstanceVO;
import eney.domain.ServiceApplyVO;
import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.service.*;
import eney.util.ExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/service/cloud")
public class CloudController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private SupplyService supplyService;

    @Resource
    private FileService fileService;

    @Resource
    private MailService mailService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private IvrService switchService;

    @Resource
    private ExcelView excelView;

    @Resource
    private UserService userService;

    @Resource
    private AcsService acsService;

    @Resource
    private MessageService messageService;

    @Resource
    private ServiceApplyService serviceApplyService;

    @Resource
    private CloudService cloudService;


    @RequestMapping(value="/instance_create", method= RequestMethod.POST)
    public ResponseEntity<?> instanceCreate(@RequestBody  int applyIdx) throws Exception {

        try{
            cloudService.instanceCreate(applyIdx);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/instance_remove", method= RequestMethod.POST)
    public ResponseEntity<?> instanceRemove(@RequestBody  int applyIdx) throws Exception {

        try{
            if(cloudService.instanceRemove(applyIdx)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/instance", method= RequestMethod.GET)
    public ResponseEntity<?> getInstanceList(Authentication authentication) throws Exception {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());


        try{
            OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();
            openStackInstanceVO.setUser_id(user.getUserid());
            List<Map<String,Object>> infoList = cloudService.getInstanceInfoList(openStackInstanceVO);

            return new ResponseEntity<>(infoList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/instance_start", method= RequestMethod.POST)
    public ResponseEntity<?> instanceStart(@RequestBody  String instanceId , Authentication authentication) throws Exception {

        OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();

        openStackInstanceVO.setInstance_id(instanceId);
        if(cloudService.instanceStart(openStackInstanceVO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/instance_stop", method= RequestMethod.POST)
    public ResponseEntity<?> instanceStop(@RequestBody  String instanceId , Authentication authentication) throws Exception {

        OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();

        openStackInstanceVO.setInstance_id(instanceId);
        if(cloudService.instanceStop(openStackInstanceVO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/instance_restart", method= RequestMethod.POST)
    public ResponseEntity<?> instanceRestart(@RequestBody  String instanceId , Authentication authentication) throws Exception {

        OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();

        openStackInstanceVO.setInstance_id(instanceId);
        if(cloudService.instanceRestart(openStackInstanceVO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
