package eney.mapper;

import java.math.BigDecimal;
import java.util.Map;

import eney.DatasourceConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.UserVO;

@Repository
public  class MainDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	public int insertTimeMeasure(Map<String,String> map){
		return sqlSession.insert("main.insertTimeMeasure",map);
	}
}


