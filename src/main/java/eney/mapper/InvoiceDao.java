package eney.mapper;

import java.util.List;

import eney.DatasourceConfig;
import eney.domain.UserInvoiceVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.UserInvoiceVO;

@Repository
public class InvoiceDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDao.class);
	
	/**
	 * 인보이스 정보 등록
	 * @param userInvoiceVO
	 */
	public int insertInvoice(UserInvoiceVO userInvoiceVO) {
		return sqlSession.insert("invoice.insertInvoice", userInvoiceVO);
	}
	public void updateInvoiceGenerate(Integer invoice_idx){
		sqlSession.update("invoice.updateInvoiceGenerate",invoice_idx);
	}
	public void insertInvoiceContent(UserInvoiceVO contentVO) {
		sqlSession.insert("invoice.insertInvoiceContent",contentVO);
	}
	public List<UserInvoiceVO> selectInvoiceList(UserInvoiceVO userInvoiceVO){
		return sqlSession.selectList("invoice.selectInvoiceList",userInvoiceVO);
	}

}
