package com.wilterson.demo.service;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostClientConfiguration {

//    @Bean
//    public OkHttpClient client() {
//        return new OkHttpClient();
//    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new PostClientErrorDecoder();
    }
}
