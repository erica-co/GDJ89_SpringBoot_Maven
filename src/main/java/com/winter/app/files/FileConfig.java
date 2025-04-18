package com.winter.app.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//XML
@Configuration
public class FileConfig implements WebMvcConfigurer {
	@Value("${app.files.base}")
	private String path;
	
	@Value("${app.files.url}")
	private String url;
	
	//<resources mapping="/resources/**" location="/resources/" />
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /files/notice/파일명
		// D:\\upload\\notice\\
		registry.addResourceHandler(url)
				.addResourceLocations("file:\\"+path);
		
	}

}
