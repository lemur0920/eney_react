package eney.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.domain.UserInvoiceVO;
import eney.domain.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.service.AcsService;
import eney.service.AdminService;
import eney.service.UserService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("/pdf")
public class PdfController {

	@Resource
	AdminService adminService;

	@Resource
	UserService userService;

	@Resource
	AcsService acsService;


	/**
	*
	*인보이스 PDF
	*생성 및 출력*
	*
	* @param idx
	* @param userid
	* @param request
	* @param response
	* @throws Exception
	**/
	@ResponseBody
	@RequestMapping(value = "/downloadInvoice.do")
	  public void downloadInvoice(Integer idx,String userid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logoPath = request.getClass().getResource("/static/resources/img/logo_sub.png").getPath();
		String fontPath = request.getClass().getResource("/static/resources/css/font/NanumGothic.ttf").getPath();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		UserVO userVO = userService.loadUserByUsername(userid);
		List<UserInvoiceVO> invoiceList = userService.getInvoicePdf(idx);
		UserInvoiceVO invoicegroup = userService.getInvoicePdfGroupBy(idx);
	    Document document = new Document(PageSize.A4,25,25,40,25);
	    PdfWriter.getInstance(document, baos);
	    
		Image logo = Image.getInstance(logoPath);

	    document.open();
	        Font font = FontFactory.getFont(fontPath,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        font.setSize(9);
		   	
		    PdfPTable table = new PdfPTable(2);		    
		    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_BASELINE);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{3,1});
	        logo.setAlignment(Element.ALIGN_CENTER);
	        table.addCell(new Paragraph("주식회사 에네이\n\n(06978) 서울특별시 동작구 상도로 369 숭실대학교 창신관 212호 \n\n\n법인등록번호   : 110111-5542215\n\n사업자등록번호 : 119-87-01395\n\n\n\n",font));
	        table.addCell(logo);
        document.add(table);
	    
        PdfPTable customer = new PdfPTable(1);
        customer.setWidthPercentage(100);
        customer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        customer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        customer.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        customer.getDefaultCell().setFixedHeight(18);
        customer.setSpacingAfter(20);
    	customer.addCell(new Paragraph(20,"To : "+userVO.getName(),font));
    	customer.addCell(new Paragraph(20,"인보이스 번호 : "+invoicegroup.getInvoice_number(),font));
    	customer.addCell(new Paragraph(20,"생성 일자      : "+invoicegroup.getInvoice_issue_date(),font));
    	customer.addCell(new Paragraph(20,"사업자등록번호 : "+userVO.getCorporate_number(),font));
    	customer.addCell(new Paragraph(20,"주소 : "+invoicegroup.getCorporate_address(),font));
    	customer.addCell(new Paragraph(20,"담당자 이메일 : "+invoicegroup.getPublish_email(),font));
        
        document.add(customer);
        
        PdfPTable list = new PdfPTable(7);
        list.setWidthPercentage(100); 
        list.setWidths(new int[]{1,3,1,1,1,2,2});
        list.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        list.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.getDefaultCell().setFixedHeight(25);
        
        PdfPCell cell;
        cell = getNormalCell("수량","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
        cell = getNormalCell("상품명","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
        cell = getNormalCell("공급가액","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
        cell = getNormalCell("부가세","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
        cell = getNormalCell("합계","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
		cell = getNormalCell("신청일","korea",9,request);
		cell.setFixedHeight(28);
		cell.setBorderColorTop(new BaseColor(55,175,229));
		cell.setBorderWidthTop(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		list.addCell(cell);
        cell = getNormalCell("만료일","korea",9,request);
        cell.setFixedHeight(28);
        cell.setBorderColorTop(new BaseColor(55,175,229));
        cell.setBorderWidthTop(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(cell);
        
        DecimalFormat df = new DecimalFormat("#,##0");
    	int sum_all = 0;
    	int vat_all = 0;
    	int gross_all = 0;
    	
        for(UserInvoiceVO invoice : invoiceList){
        	GregorianCalendar today = new GregorianCalendar ( );
        	Integer year = today.get ( today.YEAR );
            Integer month = today.get ( today.MONTH ) + 1;
            Integer yoil = today.get ( today.DAY_OF_MONTH );
            Integer lastDay = today.getActualMaximum(Calendar.DATE);
            Integer firstDay = today.getActualMinimum(Calendar.DATE);

            String invoice_date = invoice.getInvoice_issue_date();
            Date invoice_issue_date = new SimpleDateFormat("yyyy-MM-dd").parse(invoice_date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(invoice_issue_date);

            Integer invoice_firstday = calendar.getActualMinimum(Calendar.DATE);
            Integer invoice_lastday = calendar.getActualMaximum(Calendar.DATE);

            Integer invoice_year = Integer.parseInt(invoice_date.split("-")[0].trim());
            Integer invoice_month = Integer.parseInt(invoice_date.split("-")[1].trim());
            Integer invoice_day = Integer.parseInt(invoice_date.split("-")[2].substring(0, 2).trim());


        	String reg_date = invoice.getReg_date();
        	Integer reg_year = Integer.parseInt(reg_date.split("-")[0].trim());
        	Integer reg_month = Integer.parseInt(reg_date.split("-")[1].trim());
        	Integer reg_day = Integer.parseInt(reg_date.split("-")[2].substring(0, 2).trim());


        	Calendar cal = Calendar.getInstance ( );//오늘 날짜를 기준으루..
        	cal.add ( cal.MONTH, -1 ); //1개월 전....
        	Integer use_year = cal.get ( cal.YEAR );
        	Integer use_month = cal.get ( cal.MONTH ) + 1 ;
        	Integer use_day = cal.get ( cal.DATE );
        	Integer use_lastDay = cal.getActualMaximum(Calendar.DATE);
        	Integer use_firstDay = cal.getActualMinimum(Calendar.DATE);

        	String sum = df.format(invoice.getSum());
        	String vat = df.format(invoice.getSum()/10);
        	String gross = df.format(invoice.getSum() + (invoice.getSum()/10));
        	String period = invoice_year+"-"+invoice_month+"-"+invoice_firstday+"~"+invoice_year+"-"+invoice_month+"-"+invoice_lastday;

        	if(reg_year.equals(year) && reg_month.equals(month) && reg_day < 15){
        		//가입일자가 이번달 15일 이전일 때 (ex : 3월 2일 가입 시 3월 2일 ~ 3월 31일 금액 일할계산)
				int dayPay = invoice.getSum() / use_lastDay;
				int prorate = dayPay * (use_lastDay -reg_day);

				sum = df.format(prorate);
				vat = df.format(prorate / 10);
				gross = df.format(prorate + (prorate/10));
				period = reg_year+"-"+reg_month+"-"+reg_day+"~"+reg_year+"-"+reg_month+"-"+lastDay;

				sum_all += prorate;
				vat_all += prorate / 10;
				gross_all += prorate + (prorate/10);

			}else if(reg_year.equals(use_year) && reg_month.equals(use_month) && reg_day >= 15){
        		//가입일자가 이번달 15일 이후일 때(ex : 3월 18일 가입 시 3월 18일 ~ 4월 30일 금액 계산)
        		int dayPay = invoice.getSum() / use_lastDay;
        		int prorate = dayPay * (use_lastDay - reg_day);
        		int total = prorate + invoice.getSum();

        		sum = df.format(total);
        		vat = df.format(total / 10);
        		gross = df.format(total + (total/10));

        		period = reg_year+"-"+reg_month+"-"+reg_day+"~"+year+"-"+month+"-"+lastDay;

			}else if(reg_year.equals(year) && reg_month.equals(month) && reg_day.equals(yoil)){
        		//가입일자와 인보이스 발행 일자가 같을 때
        		continue;
        	}else{
        		sum_all += invoice.getSum();
            	vat_all += invoice.getSum()/10;
            	gross_all += invoice.getSum() + (invoice.getSum()/10);
        	}
        	
        	list.addCell(new Paragraph(25, "1",font));

        	list.addCell(new Paragraph(25, invoice.getService_type()+"\n" +period, font));
    		cell = getNormalCell(sum,"korea",9,request);
    		cell.setPaddingRight(5);
    		cell.setFixedHeight(25);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		list.addCell(cell);
    		cell = getNormalCell(vat,"korea",9,request);
    		cell.setPaddingRight(5);
    		cell.setFixedHeight(25);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		list.addCell(cell);
    		cell = getNormalCell(gross,"korea",9,request);
    		cell.setPaddingRight(5);
    		cell.setFixedHeight(25);
    		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		list.addCell(cell);
			cell = getNormalCell(invoice.getReg_date().substring(0,10),"korea",9,request);
			cell.setFixedHeight(25);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			list.addCell(cell);
    		cell = getNormalCell(invoice.getEnd_date(),"korea",9,request);
    		cell.setFixedHeight(25);
    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    		list.addCell(cell);
        	
        }                
        document.add(list);
        
        PdfPTable allTable = new PdfPTable(2);
        allTable.setWidthPercentage((float) 44.5);
        allTable.setSpacingBefore(15);
        allTable.setWidths(new int[]{1,2});
        allTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        allTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        allTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        allTable.getDefaultCell().setFixedHeight(25);
        allTable.addCell(new Paragraph(25,"공급가액",font));
        cell = getNormalCell(df.format(sum_all),"korea",9,request);
        cell.setPaddingRight(5);
		cell.setFixedHeight(25);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		allTable.addCell(cell);
        allTable.addCell(new Paragraph(25,"부가세",font));
        cell = getNormalCell(df.format(vat_all),"korea",9,request);
        cell.setPaddingRight(5);
  		cell.setFixedHeight(25);
  		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
  		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
  		allTable.addCell(cell);
        allTable.addCell(new Paragraph(25,"합계",font));
        cell = getNormalCell(df.format(gross_all),"korea",9,request);
        cell.setPaddingRight(5);
  		cell.setFixedHeight(25);
  		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
  		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
  		allTable.addCell(cell);
        
        document.add(allTable);
        
        PdfPTable etcTable = new PdfPTable(1);
        etcTable.setWidthPercentage(100);
        etcTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        etcTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        etcTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        etcTable.getDefaultCell().setFixedHeight(25);
        
        etcTable.addCell(new Paragraph(25, "문의사항",font));
        etcTable.addCell(new Paragraph(25, "인보이스에 대한 문의가 있으시거나, 인보이스 내역이 변경 되셨다면  고객센터로 연락 주시면 답변 드리겠습니다.\nt : 0506 - 191 - 0024 / 070 - 7933 - 1616 \n f : 070 - 7815 - 2201 \n e : accounts@eney.co.kr",font));
        
        etcTable.addCell(new Paragraph(25, "결제 수단",font));
        
        etcTable.addCell(new Paragraph(25, "계좌이체 : 예금주:(주) 에네이 / 은행:KEB 하나은행 / 계좌번호 : 219-910008-66404",font));
        etcTable.addCell(new Paragraph(25, "CMS",font));
        
        document.add(etcTable);
        document.close();
        
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
            "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();

	  }
	
	public static PdfPCell getNormalCell(String string, String language, float size, HttpServletRequest request)
            throws DocumentException, IOException {
		
        if(string != null && "".equals(string)){
            return new PdfPCell();
        }
        Font f  = getFontForThisLanguage(language,request);
        if(size < 0) {
            f.setColor(BaseColor.RED);
            size = -size;
        }
        f.setSize(size);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        
        return cell;
    }

	public static Font getFontForThisLanguage(String language, HttpServletRequest request) {
		String fontPath = request.getClass().getResource("/static/resources/css/font/NanumGothic.ttf").getPath();
        if ("korea".equals(language)) {
            return FontFactory.getFont(fontPath,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }
        return FontFactory.getFont(fontPath, null, true);
    }
	
	public String getToday(){
		return Calendar.YEAR + "-" + (Calendar.MONTH +1) + "-" + Calendar.DATE;
	}
	
	public String getAmount(UserInvoiceVO invoice){
		
		String reg_date = invoice.getReg_date();
		String service = invoice.getService();
				
		return invoice.getReg_date();
	}
}