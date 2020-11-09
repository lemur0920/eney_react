package eney.web;

import eney.domain.*;
import eney.service.CustomerService;
import eney.service.UserService;
import eney.util.DateUtil;
import org.apache.ibatis.jdbc.Null;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/service/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private CustomerService customerService;
    @Resource
    private UserService userService;


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCustomerInfo(@RequestParam(value = "idx") Integer idx, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        CustomerVO info = new CustomerVO();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setIdx(idx);
            customerVO.setUser_id(user.getUserid());

            info = customerService.getCustomerInfo(customerVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addCustomer(@RequestBody CustomerVO customerVO, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        CustomerVO info = new CustomerVO();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            customerVO.setUser_id(user.getUserid());

            if (customerService.customerdupleCheck(customerVO) > 0) {
                return new ResponseEntity<>("동일한 고객이 있습니다", HttpStatus.BAD_REQUEST);
            }

            customerService.addCustomer(customerVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomerInfo(CustomerVO customerVO, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        customerVO.setUser_id(user.getUserid());


        try {

            customerService.updateCustomerInfo(customerVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/management", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCustomerList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "search_filed", required = false) String searchFiled,
                                             @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setPageNo(page);
            customerVO.setUser_id(user.getUserid());

            if (startDate != null && startDate != "") {
                customerVO.setStartDate(DateUtil.getStringToDate(startDate));
            }
            if (endDate != null && endDate != "") {
                customerVO.setEndDate(DateUtil.getStringToDate(endDate));
            }

            if (search != null && searchFiled != null) {
                customerVO.setSearchFiled(searchFiled);
                customerVO.setSearchValue(search);
            }

            map = customerService.getCustomerList(customerVO);

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


    @RequestMapping(value = "/management/group_member", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getGroupByCustomerList(@RequestParam(value = "group_idx") Integer group_idx, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setGroup_idx(group_idx);
            customerVO.setUser_id(user.getUserid());

            map = customerService.getGroupByCustomerList(customerVO);


        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @RequestMapping(value = "/management/group_member_count", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getGroupByCustomerListCount(@RequestParam(value = "group_idx") Integer group_idx, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setGroup_idx(group_idx);
            customerVO.setUser_id(user.getUserid());

            map = customerService.getGroupByCustomerListCount(customerVO);
        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(map.get("count"), HttpStatus.OK);
    }




    @RequestMapping(value = "/management/group", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomerGroupMember(CustomerGroupVO customerGroupVO, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        customerGroupVO.setUser_id(user.getUserid());

        System.out.println(customerGroupVO);

        try {

            customerService.updateCustomerGroupMember(customerGroupVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/management/event", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCustomerEventList(@RequestParam(value = "page", defaultValue = "1") Integer page, int customer_idx, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerEventVO customerEventVO = new CustomerEventVO();
            customerEventVO.setPageNo(page);
            customerEventVO.setCustomer_idx(customer_idx);

            map = customerService.getCustomerEventList(customerEventVO, user.getUserid());

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

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCustomerGroupList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "search_filed", required = false) String searchFiled,
                                                  @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerGroupVO customerGroupVO = new CustomerGroupVO();
            customerGroupVO.setPageNo(page);
            customerGroupVO.setUser_id(user.getUserid());

//            if(startDate != null && startDate != ""){
//                customerGroupVO.setStartDate(DateUtil.getStringToDate(startDate));
//            }
//            if(endDate != null && endDate != ""){
//                customerGroupVO.setEndDate(DateUtil.getStringToDate(endDate));
//            }
//
//            if(search != null && searchFiled != null){
//                customerGroupVO.setSearchFiled(searchFiled);
//                customerGroupVO.setSearchValue(search);
//            }

            map = customerService.getCustomerGroupList(customerGroupVO);

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

    @RequestMapping(value = "/Allgroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCustomerGroupList(Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerGroupVO customerGroupVO = new CustomerGroupVO();
            customerGroupVO.setUser_id(user.getUserid());


            map = customerService.getAllCustomerGroupList(customerGroupVO);

            System.out.println("----get all----");
            System.out.println(map.get("list"));
            System.out.println("---------------");

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseEntity<?> addCustomerGroup(CustomerGroupVO customerGroupVO, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        customerGroupVO.setUser_id(user.getUserid());

        try {
            if (customerService.nameDupleCheckCustomerGroup(customerGroupVO) > 0) {
                return new ResponseEntity<>("동일한 그룹이름이 있습니다", HttpStatus.BAD_REQUEST);
            }

            customerService.addCustomerGroup(customerGroupVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/group", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteCustomerGroup(@RequestParam(value = "idx") int idx, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        try {
            CustomerGroupVO customerGroupVO = new CustomerGroupVO();
            customerGroupVO.setIdx(idx);
            customerGroupVO.setUser_id(user.getUserid());

            customerService.deleteCustomerGroup(customerGroupVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomerGroup(CustomerGroupVO customerGroupVO, Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        customerGroupVO.setUser_id(user.getUserid());

        try {
            if (customerService.nameDupleCheckCustomerGroup(customerGroupVO) > 0) {
                return new ResponseEntity<>("동일한 그룹이름이 있습니다", HttpStatus.BAD_REQUEST);
            }

            customerService.updateCustomerGroup(customerGroupVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/sample_download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> customerUploadSampleDownload(Authentication authentication) {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        try {
            FileInputStream file = new FileInputStream("/upload/resources/고객등록샘플.xls");

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            os.close();


            String contentType = "application/vnd.ms-excel";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=text.xls").body(os.toByteArray());

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping(value="/excel_upload",method=RequestMethod.POST)
    public ResponseEntity<?> excelUpload (@RequestParam("file") MultipartFile file, Authentication authentication){

        try{
            UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
            UserVO userVO = userService.loadUserByUsername(user.getUserId());


            Exception result = customerService.bulkUploadCustomer(file, userVO.getUserid());
            if(result != null){
                return new ResponseEntity<>("데이터를 확인해주세요",HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("데이터를 확인해주세요.",HttpStatus.BAD_REQUEST);

        }

    }

}
