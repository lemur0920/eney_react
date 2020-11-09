package eney.service;

import eney.domain.BoardContentVO;
import eney.domain.CustomerCaseVO;
import eney.domain.ServiceApplyVO;
import eney.mapper.AdminDao;
import eney.mapper.CustomerCaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerCaseService {

    @Resource
    CustomerCaseDao customerCaseDao;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ServiceApplyVO subscribeCheck(ServiceApplyVO serviceApplyVO) {
        return customerCaseDao.subscribeCheck(serviceApplyVO);
    }


    public List<CustomerCaseVO> getCustomerCaseCate() {
        return customerCaseDao.getCustomerCaseCate();
    }

    public CustomerCaseVO getCustomerCase(int idx) {
        return customerCaseDao.getCustomerCase(idx);
    }

    public Map<String, Object> getCustomerCaseList(CustomerCaseVO customerCaseVO) {

        customerCaseVO.setPageSize(9);
        customerCaseVO.setTotalCount(customerCaseDao.getCustomerCaseCount(customerCaseVO));

        Map<String, Object> map = new HashMap<>();

        List<CustomerCaseVO> customerCaseList = customerCaseDao.getCustomerCaseList(customerCaseVO);

        map.put("list", customerCaseList);
        map.put("page", customerCaseVO);

        return map;
    }

    public int insertCustomerCase(CustomerCaseVO customerCaseVO) {
        return customerCaseDao.insertCustomerCase(customerCaseVO);
    }

    public int editCustomerCase(CustomerCaseVO customerCaseVO) {
        return customerCaseDao.editCustomerCase(customerCaseVO);
    }

    public int deleteCustomerCase(int idx) {
        return customerCaseDao.deleteCustomerCase(idx);
    }

}
