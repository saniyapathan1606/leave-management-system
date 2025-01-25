package team6.java.ca.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import team6.java.ca.interceptors.AdminInterceptor;
import team6.java.ca.interceptors.SecurityInterceptor;


@Component
public class WebAppConfig implements WebMvcConfigurer {
	@Autowired
	SecurityInterceptor securityInterceptor;
	
	@Autowired
	AdminInterceptor adminInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor).addPathPatterns("/compensation/*");
		registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/*");
	}
}


// /compensation/*", "/employee", "/manager", "/admin