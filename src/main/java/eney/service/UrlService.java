/*package com.eney.portal.service;


import ShortUrlBasicVO;
import ShortUrlJoinVO;
import com.eney.portal.mapper.ShortUrlDao;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
public class UrlService {

    @Autowired
    ShortUrlDao shortUrlDao;


    @Transactional
    public String addShortUrl(String requestUrl,String url, String userId, String[] channels,String title,String description) {

        ShortUrlBasicVO shortUrlBasicVO = new ShortUrlBasicVO(url,userId,title,description);
        shortUrlDao.insertBasicInfo(shortUrlBasicVO);

        for(String i  : channels){
            String shortUrl = Hashing.murmur3_32()
                    .hashString(url+i, StandardCharsets.UTF_8).toString();


            ShortUrlJoinVO shortUrlJoinVO =new ShortUrlJoinVO(shortUrlBasicVO.getUrl_idx(),shortUrl,i);
            System.out.println(shortUrlJoinVO.toString());
            shortUrlDao.insertJoinInfo(shortUrlJoinVO);

            //shortUrlVOS.add(new ShortUrlVO(i,url,short_Url,userId,title,description));
            //shortUrlDao.addShortUrl(new ShortUrlVO(i,url,short_Url,userId,title,description));
        }


        return "a";

    }

    @Transactional
    public String findUrlByShortUrl(String shortUrl) {
        String url = shortUrlDao.findUrlByShortUrl(shortUrl);
        if(!url.isEmpty()) shortUrlDao.insertAnalyticsInfo(shortUrl);

        return url;
    }
}
*/