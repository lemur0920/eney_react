package eney.web;

import eney.domain.*;
import eney.service.FileService;
import eney.service.SupplyService;
import eney.service.UserService;
import eney.util.EncryptUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/serviceApply")
@PreAuthorize("isAuthenticated()")
public class ServiceApplyController {

    @Resource
    HttpServletRequest request;

    @Resource
    SupplyService supplyService;
    @Resource
    UserService userService;
    @Resource
    FileService fileService;

    @RequestMapping(value="/item/{cate}", method= RequestMethod.GET)
    public ResponseEntity<?> serviceItemList(@PathVariable("cate") String cate) throws Throwable{
        try{
            PaymentVO paymentVO = new PaymentVO();
            paymentVO.setCategory(cate);

            List<PaymentVO> itemList = supplyService.getPaymentListWithCate(paymentVO);

            return new ResponseEntity<>(itemList, HttpStatus.OK);

        }catch (Throwable e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/patchcall/regist_patchcall" ,method=RequestMethod.POST)
    public ResponseEntity<?> patchcallRegist (ServicePatchcallVO service, Authentication authentication) throws Exception{


        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        service.setUserid(user.getUserid());
        service.setUsername(user.getName());
        service.setPay_state(PaymentLogVo.PayStatus.standby);


            if(service.getService_type().equals("test")){
                service.setPay_state(PaymentLogVo.PayStatus.approve);
                service.setPay_way("test");
                userService.insertPatchcall(service);
            }

            if(service.getPay_way().equals("auto_transfer") || service.getPay_way().equals("account_transfer")){
                if(service.getService_type().equals("callback")){
                    CallbackSmsVO callbackSmsVO = new CallbackSmsVO();
                    callbackSmsVO.setUserid(user.getUserid());
                    if(userService.checkCallbackSmsService(user.getUserid()) == null){
                        userService.insertCallbacksms(service);
                    }else{
                        callbackSmsVO.setPay_way(service.getPay_way());
                        callbackSmsVO.setEnd_date(service.getEnd_date());
                        userService.deleteCallback(user);
                        userService.insertCallbacksms(service);
                    }
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_CALLBACK", userService.checkCallbackSmsService(user.getUserid()).getIdx(), request);
                    userService.sendEmail(user);

                }else if(service.getService_type().equals("record")){
                    RecordVO recordVO = new RecordVO();
                    recordVO.setUserid(user.getUserid());
                    recordVO.setPay_state(PaymentLogVo.PayStatus.standby);
                    recordVO.setPay_way(service.getPay_way());
                    recordVO.setEnd_date(service.getEnd_date());
                    if(userService.selectRecordServiceListByUserVO(recordVO) == null){
                        userService.insertRecord(recordVO);
                    }else{
                        userService.deleteRecord(user);
                        userService.insertRecord(recordVO);
                    }
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_RECORD", userService.selectRecordServiceListByUserVO(recordVO).getIdx(), request);

                }else if(service.getService_type().equals("dashboard")){
                    if(userService.selectPatchcallDashBoardByUserid(service) == null){
                        userService.insertPatchcallDashBoard(service);
                    }else{
                        userService.deletePatcahcallDashboard(user);
                        userService.insertPatchcallDashBoard(service);
                    }

//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_DASHBOARD", userService.selectPatchcallDashBoardByUserid(service).getIdx(), request);

                }else if(service.getCategory().equals("patchcall_inteligence")){
                    if(userService.selectPatchInteligenceByUserVO(service) == null){
                        userService.insertPatchInteligence(service);
                    }else{
                        userService.deletePatcahInteligence(service);
                        userService.insertPatchInteligence(service);
                    }
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_INTELIGENCE", userService.selectPatchInteligenceByUserVO(service).getIdx(), request);

                }else if(service.getCategory().equals("cloud")){
//                    if(userService.selectCloudByUserVO(service) == null){
//                        userService.insertCloud(service);
//                    }else{
//                        userService.deleteCloud(service);
//                        userService.insertCloud(service);
//                    }
                    userService.insertCloud(service);
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_CLOUD", userService.selectCloudByUserVO(service).getIdx(), request);

                }else if(service.getCategory().equals("3rdpart")){
                    if(userService.select3rdPartByUserVO(service) == null){
                        userService.insert3rdPart(service);
                    }else{
                        userService.delete3rdPart(service);
                        userService.insert3rdPart(service);
                    }
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_3RDPART", userService.select3rdPartByUserVO(service).getIdx(), request);

                }else if(service.getCategory().equals("general_directory")){
                    if(userService.selectGeneralDirectoryByUserVO(service) == null){
                        userService.insertGeneralDirectory(service);
                    }else{
                        userService.deleteGeneralDirectory(service);
                        userService.insertGeneralDirectory(service);
                    }
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_GENERALDIRECTORY", userService.selectGeneralDirectoryByUserVO(service).getIdx(), request);

                }else{
                    service.setPay_state(PaymentLogVo.PayStatus.standby);
                    userService.insertPatchcall(service);
//                    List<MultipartFile> files = service.getFiles();
//                    List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_PATCHCALL", service.getPatchcall_idx(), request);
                    userService.sendEmail(user);
                }

//                public int insertPatchInteligence(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.insertPatchInteligence(servicePatchcallVO);
//                }
//                public int deletePatcahInteligence(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.deletePatcahInteligence(servicePatchcallVO);
//                }
//                public ServicePatchcallVO selectPatchInteligenceByUserVO(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.selectPatchInteligenceByUserVO(servicePatchcallVO);
//                }
//                public int insertCloud(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.insertCloud(servicePatchcallVO);
//                }
//                public int deleteCloud(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.deleteCloud(servicePatchcallVO);
//                }
//                public ServicePatchcallVO selectCloudByUserVO(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.selectCloudByUserVO(servicePatchcallVO);
//                }
//
//                public int insert3rdPart(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.insert3rdPart(servicePatchcallVO);
//                }
//                public int delete3rdPart(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.delete3rdPart(servicePatchcallVO);
//                }
//                public ServicePatchcallVO select3rdPartByUserVO(ServicePatchcallVO servicePatchcallVO) {
//                    return userDao.select3rdPartByUserVO(servicePatchcallVO);
//                }
            }else{
                service.setPay_state(PaymentLogVo.PayStatus.standby);
                userService.insertPatchcall(service);
            }
        return new ResponseEntity<>(service.getPay_way(),HttpStatus.OK);
    }

    @RequestMapping(value="/coloring", method=RequestMethod.POST)
    public ResponseEntity<?> coloringRegisterSubmit(@RequestBody ColoringRegisterVO registerVO, Authentication authentication){
        System.out.println("신청");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        registerVO.setUserid(user.getUserid());
        String res = supplyService.submitColoringRegister(registerVO,user);

        if(res != null){
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }

//        if(res==null)
//            mav.addObject("script", "alert('신청이 완료되었습니다.')");
//        else{
//            mav.addObject("script", "alert('"+res+"')");
//        }
//        return mav;
    }
}
