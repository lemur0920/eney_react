package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.CustomerCaseVO;
import eney.domain.ServiceApplyVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceApplyDao {

    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    public int getCloudServiceApplyCnt(ServiceApplyVO serviceApplyVO) {
        return sqlSession.selectOne("service-apply.getCloudServiceApplyCnt", serviceApplyVO);
    }

    public ServiceApplyVO getCloudServiceApply(int idx) {
        return sqlSession.selectOne("service-apply.getCloudServiceApply",idx);
    }

    public List<ServiceApplyVO> getCloudServiceApplyList(ServiceApplyVO serviceApplyVO) {
        return sqlSession.selectList("service-apply.getCloudServiceApplyList",serviceApplyVO);
    }

    public int editCloudServiceApplyStatus(ServiceApplyVO serviceApplyVO) {
        return sqlSession.update("service-apply.editCloudServiceApplyStatus",serviceApplyVO);
    }
}
