package eney.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.domain.CallLogVO;
import eney.domain.UserVO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import eney.domain.CallLogVO;
import eney.domain.UserVO;

@Component
public class ExcelView extends AbstractXlsxView {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
    	Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "050번호 수신내역.xls";

        // get data model which is passed by the Spring container
		
		if("callLog".equals(model.get("data_cate"))){
			buildCallLogDownload(workbook, (List<CallLogVO>) model.get("data_list"));
			fileName = "050번호 수신내역";
		}
		if("user".equals(model.get("data_cate"))){
			buildUserListDownload(workbook, (List<UserVO>) model.get("data_list"));
			fileName = "회원목록";
		}
		fileName = URLEncoder.encode(fileName,"UTF-8").replace("+", " ");
		fileName = fileName.concat("_").concat(sdf.format(d)).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
	}

	private void buildCallLogDownload(Workbook workbook, List<CallLogVO> callLogList) {
		Sheet sheet = workbook.createSheet("050번호 수신내역");
        sheet.setDefaultColumnWidth(30);
         
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        // create header row
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("가맹점명");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("발신지명");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("발신번호");
        header.getCell(2).setCellStyle(style);
         
        header.createCell(3).setCellValue("착신번호");
        header.getCell(3).setCellStyle(style);
         
        header.createCell(4).setCellValue("050번호");
        header.getCell(4).setCellStyle(style);
         
        header.createCell(5).setCellValue("통화날짜");
        header.getCell(5).setCellStyle(style);
        
        header.createCell(6).setCellValue("통화발신시간");
        header.getCell(6).setCellStyle(style);
         
        header.createCell(7).setCellValue("통화수신시간");
        header.getCell(7).setCellStyle(style);
         
        header.createCell(8).setCellValue("통화종료시간");
        header.getCell(8).setCellStyle(style);
         
        header.createCell(9).setCellValue("통화시간");
        header.getCell(9).setCellStyle(style);
         
        header.createCell(10).setCellValue("통화결과");
        header.getCell(10).setCellStyle(style);
         
        // create data rows
        int rowCount = 1;
         
        for (CallLogVO aLog : callLogList) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(aLog.getAgent_name());
            aRow.createCell(1).setCellValue(aLog.getDong_name());
            aRow.createCell(2).setCellValue(aLog.getAni());
            aRow.createCell(3).setCellValue(aLog.getCalled_no());
            aRow.createCell(4).setCellValue(aLog.getDn());
            aRow.createCell(5).setCellValue(aLog.getCs_date());
            aRow.createCell(6).setCellValue(aLog.getCs_time());
            aRow.createCell(7).setCellValue(aLog.getSs_time());
            aRow.createCell(8).setCellValue(aLog.getSe_time());
            aRow.createCell(9).setCellValue(aLog.getSvc_duration_text());
            aRow.createCell(10).setCellValue(aLog.getCall_result_text());
        }
	}
	
	private void buildUserListDownload(Workbook workbook, List<UserVO> userList) {
		
        Sheet sheet = workbook.createSheet("회원목록");
        sheet.setDefaultColumnWidth(30);
         
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        // create header row
        Row header = sheet.createRow(0);
         
        header.createCell(0).setCellValue("NO");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("구분");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("아이디");
        header.getCell(2).setCellStyle(style);
         
        header.createCell(3).setCellValue("등록일자");
        header.getCell(3).setCellStyle(style);
         
        header.createCell(4).setCellValue("이름");
        header.getCell(4).setCellStyle(style);
        
        header.createCell(5).setCellValue("휴대폰");
        header.getCell(5).setCellStyle(style);
         
        header.createCell(6).setCellValue("이메일");
        header.getCell(6).setCellStyle(style);
         
        // create data rows
        int rowCount = 1;
         
        for (UserVO aLog : userList) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(aLog.getIdx());
            aRow.createCell(1).setCellValue(aLog.getMember_kind());
            aRow.createCell(2).setCellValue(aLog.getUserid());
            aRow.createCell(3).setCellValue(aLog.getReg_date());
            aRow.createCell(4).setCellValue(aLog.getName());
            aRow.createCell(5).setCellValue(aLog.getPhone_number());
            aRow.createCell(6).setCellValue(aLog.getEmail());
        }
	}
	
}