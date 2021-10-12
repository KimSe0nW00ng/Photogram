package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{ // web 설정 파일
	
	@Value("${file.path}") //yml에 있는 file path값
	private String uploadFolder;
	
	// file:///  C:/workspace/springbootwork/upload/
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);

			registry
			.addResourceHandler("/upload/**")// jsp페이지에서 /upload/**패턴이 나오면 발동
			.addResourceLocations("file:///"+uploadFolder) //uploadFolder = yml의 file의 값
			.setCachePeriod(60*10*6)  //1시간 동안 이미지 캐싱
			.resourceChain(true) // 발동함
			.addResolver(new PathResourceResolver()); 
	}

}
