package com.wilterson.demo.service;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostClientInterceptor {

    /**
     * Custom interceptor to add additional request headers
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("user", "John");
            requestTemplate.header("password", "password123");
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }

    /**
     * Alternatively, we can use the BasicAuthRequestInterceptor class that the Spring Cloud OpenFeign provides
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("username", "password");
    }
}
