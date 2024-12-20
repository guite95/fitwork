package com.fitwork.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fitwork.back.interceptor.VisitorInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final VisitorInterceptor visitorInterceptor;
	public WebConfig(VisitorInterceptor visitorInterceptor) {
		this.visitorInterceptor = visitorInterceptor;
	}
  
//	@Override
//	public void addCorsMappings(@NonNull CorsRegistry registry) {
//		registry.addMapping("/**")
////        .allowedOrigins("http://192.168.210.83:5173")
//        .allowedOrigins("http://localhost:5173")
//        .allowedMethods("GET", "POST", "PUT", "DELETE")
//        .allowedHeaders("*")
//        .allowCredentials(true)
//        .maxAge(3600);
//	}
	
//	@Override
//	public void addCorsMappings(@NonNull CorsRegistry registry) {
//		registry.addMapping("/**")
////        .allowedOrigins("http://192.168.210.83:5173")
//        .allowedOrigins("http://localhost:5173")
//        .allowedMethods("GET", "POST", "PUT", "DELETE")
//        .allowedHeaders("*")
//        .allowCredentials(true)
//        .maxAge(3600);
//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("file/board/**").addResourceLocations("file:///C://SSAFY/final-prj/board/img/");
		registry.addResourceHandler("file/club/**").addResourceLocations("file:///C://SSAFY/final-prj/clubs/img/");
		registry.addResourceHandler("file/class/**").addResourceLocations("file:///C://SSAFY/final-prj/classes/img/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(visitorInterceptor)
				.addPathPatterns("/**");
//				.excludePathPatterns("/static/**", "/error"); // 특정 경로 제외가능
	}
	
}