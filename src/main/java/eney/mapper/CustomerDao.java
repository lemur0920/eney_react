package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao {

    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;

    public int getCustomerCount(CustomerVO customerVO) {
        return sqlSession.selectOne("customer.getCustomerCount", customerVO);
    }

    public List<CustomerVO> getCustomerList(CustomerVO customerVO) {
        return sqlSession.selectList("customer.getCustomerList",customerVO);
    }

    public int addCustomer(CustomerVO customerVO) {
        return sqlSession.insert("customer.addCustomer",customerVO);
    }

    public int customerdupleCheck(CustomerVO customerVO) {
        return sqlSession.selectOne("customer.customerdupleCheck",customerVO);
    }

    public void updateCustomerInfo(CustomerVO customerVO) {
        sqlSession.update("customer.updateCustomerInfo",customerVO);
    }

    public int updateCustomerGroupMember(CustomerGroupVO customerGroupVO) {
        return sqlSession.update("customer.updateCustomerGroupMember", customerGroupVO);
    }

    public int updateCustomerGroupMemberRemove(CustomerGroupVO customerGroupVO) {
        return sqlSession.update("customer.updateCustomerGroupMemberRemove", customerGroupVO);
    }

    public List<CustomerVO> getGroupUnusedCustomerList(CustomerVO customerVO) {
        return sqlSession.selectList("customer.getGroupUnusedCustomerList",customerVO);
    }

    public List<CustomerVO> getGroupByCustomerList(CustomerVO customerVO) {
        return sqlSession.selectList("customer.getGroupByCustomerList",customerVO);
    }

    public int getGroupByCustomerListCount(CustomerVO customerVO) {
        return sqlSession.selectOne("customer.getGroupByCustomerListCount",customerVO);
    }

    public List<CustomerVO> getCustomer(CustomerVO customerVO) {
        return sqlSession.selectList("customer.getCustomer", customerVO);
    }

    public CustomerVO getCustomerInfo(CustomerVO customerVO) {
        return sqlSession.selectOne("customer.getCustomerInfo", customerVO);
    }

    public int getCustomerEventCount(CustomerEventVO customerEventVO) {
        return sqlSession.selectOne("customer.getCustomerEventCount", customerEventVO);
    }

    public List<CustomerEventVO> getCustomerEventList(CustomerEventVO customerEventVO) {
        return sqlSession.selectList("customer.getCustomerEventList",customerEventVO);
    }

    public int updateCustomerEventWithGA(List<GAKeyWordVO> gaKeyWordVOList) {
        return sqlSession.update("customer.updateCustomerEventWithGA",gaKeyWordVOList);
    }

    public int getCustomerGroupCount(CustomerGroupVO customerGroupVO) {
        return sqlSession.selectOne("customer.getCustomerGroupCount", customerGroupVO);
    }

    public List<CustomerGroupVO> getCustomerGroupList(CustomerGroupVO customerGroupVO) {
        return sqlSession.selectList("customer.getCustomerGroupList",customerGroupVO);
    }
    public List<CustomerGroupVO> getAllCustomerGroupList(CustomerGroupVO customerGroupVO) {
        return sqlSession.selectList("customer.getAllCustomerGroupList",customerGroupVO);
    }

    public List<CustomerGroupVO> getGroup(CustomerGroupVO customerGroupVO) {
        return sqlSession.selectList("customer.getGroup", customerGroupVO);
    }

    public int nameDupleCheckCustomerGroup(CustomerGroupVO customerGroupVO) {
        return sqlSession.selectOne("customer.nameDupleCheckCustomerGroup",customerGroupVO);
    }

    public int addCustomerGroup(CustomerGroupVO customerGroupVO) {
        return sqlSession.insert("customer.addCustomerGroup",customerGroupVO);
    }

    public int deleteCustomerGroup(CustomerGroupVO customerGroupVO) {
        return sqlSession.delete("customer.deleteCustomerGroup",customerGroupVO);
    }

    public int updateCustomerGroup(CustomerGroupVO customerGroupVO) {
        return sqlSession.update("customer.updateCustomerGroup", customerGroupVO);
    }

    public void bulkUploadCustomer(CustomerVO customerVO) {
        sqlSession.update("customer.bulkUploadCustomer", customerVO);
    }

}
