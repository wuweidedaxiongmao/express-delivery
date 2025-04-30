package com.example.springboot.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// 1. 允许所有域名跨域访问（生产环境建议指定具体域名）
		config.addAllowedOrigin("*");

		// 2. 允许所有请求头
		config.addAllowedHeader("*");

		// 3. 允许所有HTTP方法（GET/POST/PUT等）
		config.addAllowedMethod("*");

//		// 4. 允许跨域发送cookie
//		config.setAllowCredentials(true);

		// 5. 对所有接口应用跨域配置
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}