package com.winter.app.files;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
	
	
	//HDD에 파일을 저장하고 저장된 파일명을 return
	
	public String fileSave(String path, MultipartFile multipartFile)throws Exception{
		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		//1. 어떤이름으로 저장??
		String fileName = UUID.randomUUID().toString();
		
		//2. fileName에 확장자
		fileName = fileName.concat("_").concat(multipartFile.getOriginalFilename());
		
		//3. HDD에 저장
		file = new File(file, fileName);
		//a) FileCopyUtils.copy()
		//FileCopyUtils.copy(multipartFile.getBytes(), file);
		//b) MultipartFile trasferTo()
		multipartFile.transferTo(file);
		
		return fileName;
	}

}
