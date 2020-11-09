/*package com.eney.portal.mapper;


import DatasourceConfig;
import ShortUrlBasicVO;
import ShortUrlJoinVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlDao {

    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;


    public void insertBasicInfo(ShortUrlBasicVO shortUrlBasicVO) {
        sqlSession.insert("shortUrl.insertBasicInfo",shortUrlBasicVO);
        System.out.println(shortUrlBasicVO.getUrl_idx());
    }

    public void insertJoinInfo(ShortUrlJoinVO shortUrlJoinVO) {
        sqlSession.update("shortUrl.insertJoinInfo",shortUrlJoinVO);
    }

    public String findUrlByShortUrl(String shortUrl) {
        return sqlSession.selectOne("shortUrl.findUrlByShortUrl",shortUrl);
    }

    public void insertAnalyticsInfo(String shortUrl) {
        sqlSession.insert("shortUrl.insertAnalyticsInfo",shortUrl);

    }

//    public void addShortUrl(ShortUrlVO shortUrlVO) {
//        sqlSession.update("shortUrl.insertShortUrl",shortUrlVO);
//    }
//
//    public String findUrlByShortUrl(String shortUrl){
//        return sqlSession.selectOne("shortUrl.findUrlByShortUrl",shortUrl);
//    }
//
//    public void updateCountBoard(String shortUrl) {
//        sqlSession.update("shortUrl.updateCountBoard",shortUrl);
//    }
}
*/