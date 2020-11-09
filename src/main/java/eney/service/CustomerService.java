package eney.service;

import eney.domain.*;
import eney.mapper.CustomerDao;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Resource
    CustomerDao customerDao;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Map<String, Object> getCustomerList(CustomerVO customerVO) {

        customerVO.setPageSize(9);
        customerVO.setTotalCount(customerDao.getCustomerCount(customerVO));

        Map<String, Object> map = new HashMap<>();

        List<CustomerVO> customerList = customerDao.getCustomerList(customerVO);

        map.put("list", customerList);
        map.put("page", customerVO);

        return map;
    }

    public Map<String, Object> getGroupByCustomerList(CustomerVO customerVO) {

        Map<String, Object> map = new HashMap<>();

        List<CustomerVO> unusedGroupList = customerDao.getGroupUnusedCustomerList(customerVO);
        List<CustomerVO> usedGroupList = customerDao.getGroupByCustomerList(customerVO);

        map.put("usedGroup", usedGroupList);
        map.put("unusedGroup", unusedGroupList);

        return map;
    }

    public Map<String, Object> getGroupByCustomerListCount(CustomerVO customerVO) {

        Map<String, Object> map = new HashMap<>();

        int usedGroupList = customerDao.getGroupByCustomerListCount(customerVO);

        map.put("count", usedGroupList);

        return map;
    }

    public void updateCustomerGroupMember(CustomerGroupVO customerGroupVO) {
        if (customerGroupVO.getUse_customer_idx_list().size() > 0) {
            customerDao.updateCustomerGroupMember(customerGroupVO);
        }
        if (customerGroupVO.getUnused_customer_idx_list().size() > 0) {
            customerDao.updateCustomerGroupMemberRemove(customerGroupVO);
        }
    }

    public CustomerVO getCustomerInfo(CustomerVO customerVO) {
        return customerDao.getCustomerInfo(customerVO);
    }

    public void updateCustomerInfo(CustomerVO customerVO) {
        customerDao.updateCustomerInfo(customerVO);
    }

    public void addCustomer(CustomerVO customerVO) {

        customerDao.addCustomer(customerVO);
    }

    public int customerdupleCheck(CustomerVO customerVO) {

        return customerDao.customerdupleCheck(customerVO);
    }

    public Map<String, Object> getCustomerEventList(CustomerEventVO customerEventVO, String userId) {


        CustomerVO customerVO = new CustomerVO();
        customerVO.setUser_id(userId);
        customerVO.setIdx(customerEventVO.getCustomer_idx());

        if (customerDao.getCustomer(customerVO).size() > 0) {
            Map<String, Object> map = new HashMap<>();

            customerEventVO.setPageSize(9999);
            customerEventVO.setTotalCount(customerDao.getCustomerEventCount(customerEventVO));

            List<CustomerEventVO> customerEventList = customerDao.getCustomerEventList(customerEventVO);

            map.put("list", customerEventList);
            map.put("page", customerEventVO);

            return map;

        } else {
            throw new NullPointerException();
        }
    }

    public Map<String, Object> getCustomerGroupList(CustomerGroupVO customerGroupVO) {

        customerGroupVO.setPageSize(9);
        customerGroupVO.setTotalCount(customerDao.getCustomerGroupCount(customerGroupVO));

        Map<String, Object> map = new HashMap<>();

        List<CustomerGroupVO> customerGrouprList = customerDao.getCustomerGroupList(customerGroupVO);

        map.put("list", customerGrouprList);
        map.put("page", customerGroupVO);

        return map;
    }

    public Map<String, Object> getAllCustomerGroupList(CustomerGroupVO customerGroupVO) {

        Map<String, Object> map = new HashMap<>();

        List<CustomerGroupVO> customerGrouprList = customerDao.getAllCustomerGroupList(customerGroupVO);

        map.put("list", customerGrouprList);

        return map;
    }

    public void addCustomerGroup(CustomerGroupVO customerGroupVO) {

        customerDao.addCustomerGroup(customerGroupVO);
    }

    public int nameDupleCheckCustomerGroup(CustomerGroupVO customerGroupVO) {

        return customerDao.nameDupleCheckCustomerGroup(customerGroupVO);
    }

    public void deleteCustomerGroup(CustomerGroupVO customerGroupVO) {

        customerDao.deleteCustomerGroup(customerGroupVO);
    }

    public void updateCustomerGroup(CustomerGroupVO customerGroupVO) {

        customerDao.updateCustomerGroup(customerGroupVO);
    }

    public Exception bulkUploadCustomer(MultipartFile file, String userId) {



        try{

            List<CustomerVO> customerList = new ArrayList<>();

            if(file!=null && file != null && file.getSize() > 0){
                InputStream excelFile =  new BufferedInputStream(file.getInputStream());

                HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

                int rowindex=0;
                int columnindex=0;
                //시트 수 (첫번째에만 존재하므로 0을 준다)
                //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
                HSSFSheet sheet=workbook.getSheetAt(0);
                //행의 수
                int rows=sheet.getPhysicalNumberOfRows();
                for(rowindex=1;rowindex<rows;rowindex++){
                    System.out.println("===");
                    System.out.println(rowindex);
                    System.out.println(rows);
                    System.out.println("===");
                    //행을읽는다
                    HSSFRow row=sheet.getRow(rowindex);
                    if(row !=null){
                        //셀의 수
                        int cells = row.getPhysicalNumberOfCells();
                        CustomerVO customerVO = new CustomerVO();
                        customerVO.setUser_id(userId);
                        for(columnindex=0; columnindex<=4; columnindex++){
                            System.out.println(columnindex);
                            //셀값을 읽는다
                            HSSFCell cell=row.getCell(columnindex);
                            String value="";
                            //셀이 빈값일경우를 위한 널체크
//							if(columnindex == 0 && cell == null){
//								throw new Exception("050번호는 필수입니다.");
//							}

                            switch (columnindex){
                                case 0:
                                    String name;
                                    try{
                                        name = cell.getStringCellValue();
                                    }catch(NullPointerException e){
                                        name = "";
                                    }
                                    customerVO.setName(name);
                                    break;
                                case 1:
                                    String phone;
                                    try{
                                        phone = cell.getStringCellValue();
                                    }catch(NullPointerException e){
                                        phone = "";
                                    }
                                    customerVO.setPhone_number(phone);
                                    break;
                                case 2:
                                    String gender;
                                    try{
                                        gender = cell.getStringCellValue();
                                    }catch(NullPointerException e){
                                        gender = "";
                                    }
                                    customerVO.setGender(gender);
                                    break;
                                case 3:
                                    String teamName;
                                    try{
                                        teamName = cell.getStringCellValue();
                                    }catch(NullPointerException e){
                                        teamName = "";
                                    }
                                    customerVO.setTeam_name(teamName);
                                    break;
                                case 4:
                                    String email;
                                    try{
                                        email = cell.getStringCellValue();
                                    }catch(NullPointerException e){
                                        email = "";
                                    }
                                    customerVO.setEmail(email);
                                    break;
                            }
                            if(columnindex == 4){
                                customerList.add(customerVO);
                                System.out.println(customerVO);
                                customerVO = new CustomerVO();
                            }
                        }

                    }else{
                        System.out.println("로우 널임");
                    }
                }

                if(customerList.size() != 0 ){
                    for(int i=0;i <=customerList.size()-1; i++){
                        customerDao.bulkUploadCustomer(customerList.get(i));
                    }
                }
            }

            return null;

        }catch(Exception e){
            e.printStackTrace();
            return e;
        }


    }


}
