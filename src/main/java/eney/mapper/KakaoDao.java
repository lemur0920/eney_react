package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.KakaoAlimtalkTemplateVO;
import eney.domain.KakaoSenderProfileVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class KakaoDao {
    @Autowired
    @Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
    private SqlSession sqlSession;


    public void insertKakaoTemplate(Map<String, Object> map) {
        sqlSession.insert("sj_kakao.insertKakaoTemplate", map);
    }

    public void insertSenderProfile(Map<String, Object> profile) {
        sqlSession.insert("sj_kakao.insertSenderProfile", profile);
    }

    public void deleteSenderProfile(Map<String, Object> param) {
        sqlSession.delete("sj_kakao.deleteSenderProfile", param);
    }

    public List<KakaoSenderProfileVO> getSenderProfileList(KakaoSenderProfileVO kakaoSenderProfileVO) {
        return sqlSession.selectList("sj_kakao.getSenderProfileList", kakaoSenderProfileVO);
    }

    public int getSenderProfileListCount(KakaoSenderProfileVO kakaoSenderProfileVO) {
        return sqlSession.selectOne("sj_kakao.getSenderProfileListCount", kakaoSenderProfileVO);
    }
    public List<KakaoAlimtalkTemplateVO> getAlimtalkTemplateList(KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO) {
        return sqlSession.selectList("sj_kakao.getAlimtalkTemplateList", kakaoAlimtalkTemplateVO);
    }

    public int getAlimtalkTemplateListCount(KakaoAlimtalkTemplateVO kakaoAlimtalkTemplateVO) {
        return sqlSession.selectOne("sj_kakao.getAlimtalkTemplateListCount", kakaoAlimtalkTemplateVO);
    }

}
