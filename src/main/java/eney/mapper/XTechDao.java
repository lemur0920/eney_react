package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.XTechApply;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class XTechDao {

    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;

    public void XtechApplySave(XTechApply xTechApply) {
        sqlSession.insert("xTech.XtechApplySave", xTechApply);
    }
}
