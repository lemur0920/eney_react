package eney.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.domain.FileVO;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import eney.domain.FileVO;
import eney.mapper.FileDao;

@Deprecated
@Component
public class FileDownloadView extends AbstractView {

	@Resource
	FileDao fileDao;

	public void Download() {
		setContentType("application/download; utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		File file = (File) model.get("downloadFile");
		System.out.println(file.getAbsolutePath());
		FileVO fileVoInfo = (FileVO) model.get("downloadFileVoInfo");
		String fileName = (fileVoInfo != null) ? fileVoInfo.getName() : file.getName();
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		if(! file.isFile()){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.write("ERROR: not found file".getBytes());
			out.flush();
			
			return;
		}

		if (request.getHeader("User-Agent").indexOf("MSIE") > -1) {
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else {
			fileName = new String(fileName.getBytes("utf-8"));
		}

		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		

		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}
		
		out.flush();
	}
}