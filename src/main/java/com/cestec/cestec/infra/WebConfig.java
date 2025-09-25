package com.cestec.cestec.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/icon/**")
                .addResourceLocations("classpath:/static/icon/");
        
        registry.addResourceHandler("/imoveisImages/**")
                .addResourceLocations("file:./uploadImage/imoveisImages/");
    }
}