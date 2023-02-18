package com.wilterson.demo.service;

import feign.Logger;
import feign.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostClientLoggerConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Level.FULL;
    }
}
