package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.CustomerCaseVO;
import eney.domain.ServiceApplyVO;
import eney.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerCaseDao {

    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    public ServiceApplyVO subscribeCheck(ServiceApplyVO serviceApplyVO) {
        return sqlSession.selectOne("customer-case.subscribeCheck",serviceApplyVO);
    }

    public List<CustomerCaseVO> getCustomerCaseCate() {
        return sqlSession.selectList("customer-case.getCustomerCaseCate");
    }


    public int getCustomerCaseCount(CustomerCaseVO customerCaseVO) {
        return sqlSession.selectOne("customer-case.getCustomerCaseCount", customerCaseVO);
    }

    public CustomerCaseVO getCustomerCase(int idx) {
        return sqlSession.selectOne("customer-case.getCustomerCase",idx);
    }

    public List<CustomerCaseVO> getCustomerCaseList(CustomerCaseVO customerCaseVO) {
        return sqlSession.selectList("customer-case.getCustomerCaseList",customerCaseVO);
    }

    public int insertCustomerCase(CustomerCaseVO customerCaseVO) {
        return sqlSession.insert("customer-case.insertCustomerCase",customerCaseVO);
    }

    public int editCustomerCase(CustomerCaseVO customerCaseVO) {
        return sqlSession.update("customer-case.editCustomerCase",customerCaseVO);
    }

    public int deleteCustomerCase(int idx) {
        return sqlSession.delete("customer-case.deleteCustomerCase",idx);
    }
}
