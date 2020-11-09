package eney.web;



import eney.domain.CustomerCaseVO;
import eney.domain.ServiceApplyVO;
import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.service.CustomerCaseService;
import eney.service.FileService;
import eney.service.UserService;
import eney.util.FileUploadUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/customer_case")
public class CustomerCaseController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Resource
    private CustomerCaseService customerCaseService;
    @Resource
    FileService fileService;
    @Resource
    private FileUploadUtil fileUploadUtil;

    @Resource
    UserService userService;


    @RequestMapping(value="/subscribe_check", method= RequestMethod.GET)
    public ResponseEntity<?> subscribeCheck(Authentication authentication) throws Exception {

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        ServiceApplyVO serviceApplyVO = new ServiceApplyVO();
        serviceApplyVO.setUserid(userVO.getUserid());
        serviceApplyVO.setService_type("consulting");

        if(customerCaseService.subscribeCheck(serviceApplyVO) != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/customer_type", method= RequestMethod.GET)
    public ResponseEntity<?> getCustomerCaseCate() throws Exception {
        return new ResponseEntity<>(customerCaseService.getCustomerCaseCate(), HttpStatus.OK);
    }

    @RequestMapping(value="/selectOne", method= RequestMethod.GET)
    public ResponseEntity<?> getCustomerCase( @RequestParam(value="idx") Integer idx) throws Exception {


        CustomerCaseVO customerCaseVO = new CustomerCaseVO();
        try {
            System.out.println("=== getCustomerCase" + idx +"번 글");
            customerCaseVO = customerCaseService.getCustomerCase(idx);
            System.out.println(customerCaseVO);
        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(customerCaseVO, HttpStatus.OK);
    }


    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<?> getCustomerCaseList( @RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="cate") Integer cate) throws Exception {
        Map<String, Object> map = new HashMap<>();

        try {
//            CustomerCaseVO customerCaseVO = boardService.getBoardConfigByPath(type);
            CustomerCaseVO customerCaseVO = new CustomerCaseVO();
            customerCaseVO.setPageNo(page);
            if(cate != null){
                customerCaseVO.setType_code(cate);
            }
            map = customerCaseService.getCustomerCaseList(customerCaseVO);

            System.out.println("---------------");
            System.out.println(map.get("page"));
            System.out.println(map.get("list"));
            System.out.println("---------------");

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(map, HttpStatus.OK);
    }



    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<?> addCustomerCase(CustomerCaseVO customerCase) throws Exception {

        String fileName = fileService.tumbnailUpload(customerCase.getTumbnail(), "TUMBNAIL");
        customerCase.setTumbnail_file(fileName + "." + FilenameUtils.getExtension(customerCase.getTumbnail().getOriginalFilename()));
        customerCaseService.insertCustomerCase(customerCase);

        return new ResponseEntity<>(customerCaseService.getCustomerCaseCate(), HttpStatus.OK);
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public ResponseEntity<?> editCustomerCase(CustomerCaseVO customerCase) throws Exception {

        try{

            if(customerCase.getTumbnail() != null){
                String fileName = fileService.tumbnailUpload(customerCase.getTumbnail(), "TUMBNAIL");
                customerCase.setTumbnail_file(fileName + "." + FilenameUtils.getExtension(customerCase.getTumbnail().getOriginalFilename()));
            }

            customerCaseService.editCustomerCase(customerCase);


        }catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/{idx}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomerCase( @PathVariable("idx") int idx) throws Exception {

        try{
            customerCaseService.deleteCustomerCase(idx);

        }catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }



    /**
     * 이미지 Viewer
     * @throws Exception
     */
    @RequestMapping(value="/{filePath}/{fileName}", method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> ImageLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable("filePath") String filePath, @PathVariable("fileName") String fileName) throws Exception {
        
        System.out.println(fileUploadUtil.getFileUploadPath().resolve(filePath).toString());
        System.out.println(fileUploadUtil.getFileUploadPath().resolve(filePath).toString()+"/" + fileName);

        File img = new File(fileUploadUtil.getFileUploadPath().resolve(filePath).toString()+"/" + fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));

    }

}
