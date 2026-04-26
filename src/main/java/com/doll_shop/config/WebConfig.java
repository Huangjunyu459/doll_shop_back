package com.doll_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.base-dir:./uploads/}")
    private String uploadBaseDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 让前端可以通过 http://localhost:8080/uploads/xxx.jpg 访问图片
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadBaseDir);
    }
}