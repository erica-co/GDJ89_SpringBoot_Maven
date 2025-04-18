package com.winter.app.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("fileDownView")
public class FileDownView extends AbstractView {
	@Value("${app.files.base}")
	private String path;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 FileVO fileVO = (FileVO)model.get("fileVO");
		 
		 String kind = (String)model.get("kind");
		 
		 File file = new File(path.concat(kind), fileVO.getFileName());
		 
		 //응답 헤더
		 //1. 총 파일의 크기
		 response.setContentLengthLong(file.length());
		 
		 //2. 다운로드의 이름을 인코딩
		 String name = URLEncoder.encode(fileVO.getOldName(), "UTF-8");
		 
		 //3. header
		 response.setHeader("Content-Disposition", "attchment;fileName=\"".concat(name).concat("\""));
		 response.setHeader("Content-Transfer-Encoding", "binary");
		 
		 //HDD에서 파일을 읽어 오기
		 FileInputStream fr = new FileInputStream(file);
		 
		 //Client로 전송 연결 준비
		 OutputStream os = response.getOutputStream();
		 
		 //전송
		 FileCopyUtils.copy(fr, os);
		 
		 //자원 해제
		 
		 os.close();
		 fr.close();
		 
		 
		 
		 
		 
		 
		
	}
}
