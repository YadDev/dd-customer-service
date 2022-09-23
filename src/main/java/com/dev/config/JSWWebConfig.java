package com.dev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class JSWWebConfig implements WebMvcConfigurer {
	
	 @Value("${CORS.AllowedOriginPatterns}")
	 private String[] AllowedOriginPatterns;

	 @Value("${CORS.AllowedHeaders}")
	 private String[] AllowedHeaders;

	 @Value("${CORS.AllowedMethods}")
	 private String[] AllowedMethods;
	 
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").
	        allowedOriginPatterns(AllowedOriginPatterns).
	        allowedHeaders(AllowedHeaders).
	        allowedMethods(AllowedMethods);
	    }

}
