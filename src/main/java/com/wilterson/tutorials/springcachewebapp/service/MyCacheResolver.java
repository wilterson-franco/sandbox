package com.wilterson.tutorials.springcachewebapp.service;

import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

@Slf4j
public class MyCacheResolver implements CacheResolver {

    private final CacheManager cacheManager;

    public MyCacheResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Collection<Cache> caches = new ArrayList<>();
//        if(context.getTarget().getClass() == BookRepository.class){
            if (context.getMethod().getName().equals("getBookByIsbn")) {
                caches.add(cacheManager.getCache("books"));
            }
//        }
        return caches;
    }
}
