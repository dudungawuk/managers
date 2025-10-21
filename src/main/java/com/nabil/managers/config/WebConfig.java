package com.nabil.managers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.root-dir}")
    private String rootDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Exposes the file system path as a web resource.
        // The 'file:///' part is crucial for file system paths.
        registry.addResourceHandler("/user-images/**")
                .addResourceLocations("file:" + rootDir);
    }
}