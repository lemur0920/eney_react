package eney.api.v1.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;

@Repository
public class ApiUserDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;

}
