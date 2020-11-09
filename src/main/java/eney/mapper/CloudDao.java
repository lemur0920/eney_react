package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CloudDao {
    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);


    public OpenStackInstanceVO getOpenStackInstanceInfoByApplyIdx(int idx) {
        return sqlSession.selectOne("cloud.getOpenStackInstanceInfoByApplyIdx", idx);
    }

    public OpenStackUserVO getOpenStackUser(String userId) {
        return sqlSession.selectOne("cloud.getOpenStackUser", userId);
    }

    public OpenStackFlavorVO getOpenStackFlavorId(String flavorName) {
        return sqlSession.selectOne("cloud.getOpenStackFlavorId", flavorName);
    }

    public OpenStackNetworkVO getOpenStackNetworkUUID(String networkName) {
        return sqlSession.selectOne("cloud.getOpenStackNetworkUUID", networkName);
    }

    public OpenStackImageVO getOpenStackImageId(String imageName) {
        return sqlSession.selectOne("cloud.getOpenStackImageId", imageName);
    }

    public int addInstanceInfo(OpenStackInstanceVO openStackInstanceVO) {
        return (Integer)sqlSession.insert("cloud.addInstanceInfo", openStackInstanceVO);
    }

    public int instanceInfoCnt(OpenStackInstanceVO openStackInstanceVO) {
        return (Integer)sqlSession.selectOne("cloud.instanceInfoCnt", openStackInstanceVO);
    }

    public int editInstanceInfo(OpenStackInstanceVO openStackInstanceVO) {
        return (Integer)sqlSession.update("cloud.editInstanceInfo", openStackInstanceVO);
    }

    public List<OpenStackInstanceVO> getInstanceInfoList(OpenStackInstanceVO openStackInstanceVO) {
        return sqlSession.selectList("cloud.getInstanceInfoList", openStackInstanceVO);
    }
}
