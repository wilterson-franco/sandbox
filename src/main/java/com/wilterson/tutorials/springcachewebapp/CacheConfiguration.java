package com.wilterson.tutorials.springcachewebapp;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.wilterson.tutorials.springcachewebapp.service.MyCacheResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private Caffeine<Object, Object> caffeineBuilder() {
        return Caffeine
                .newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager myCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineBuilder());
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("books");
        caffeineCacheManager.setCacheNames(cacheNames);
        return caffeineCacheManager;
    }

    @Bean
    public CacheResolver myCacheResolver(CacheManager cacheManager) {
        return new MyCacheResolver(cacheManager);
    }
}
