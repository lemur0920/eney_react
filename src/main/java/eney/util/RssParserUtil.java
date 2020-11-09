package eney.util;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

@Component
public class RssParserUtil {
	// RSS 주소
	public List<Map<String,String>> rssParse(){
	String rssUrl = "http://rss.etnews.com/Section901.xml";
	
	List<Map<String,String>> newsList = new ArrayList<Map<String,String>>();
     
    try{
        URL url = null ;
         
        SAXBuilder rssParser = new SAXBuilder();
        rssParser.setIgnoringElementContentWhitespace(true) ;
         
         
        url = new URL(rssUrl) ;
         
        InputSource is = new InputSource(url.openStream()) ;
         
        Document doc = rssParser.build(is) ;
        Element root = doc.getRootElement() ;
        Element channel = root.getChild("channel") ;
        List<Element> item = channel.getChildren("item") ;
         
         
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
         
        for(int i=0; i < item.size(); i++){
            Element el = (Element)item.get(i) ;
             
            String title = el.getChildText("title") ;
            String link = el.getChildText("link") ;
            String pubDate = el.getChildText("pubDate") ;
            String author = el.getChildText("author") ;
         
 
            date = new Date(pubDate);
             
            // 날짜 형식 변경         
            String dateEscape = timeFormat.format(date) ;
 
            // 문자 디코딩
            String titleEscape = StringEscapeUtils.unescapeHtml4(title) ;
            String authorEscape = StringEscapeUtils.unescapeHtml4(author) ;
             
            HashMap<String,String> hm = new HashMap<String,String>();
            hm.put("title", titleEscape) ;
            hm.put("link", link) ;
            hm.put("date", dateEscape) ;
            hm.put("author", authorEscape) ;
             
            newsList.add(hm); 
        }
         
         
         
         
    }catch(Exception e){
        e.printStackTrace();
    }
	return newsList;
	}
}
