package com.wilterson.tutorials.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringCacheTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheTutorialApplication.class, args);
    }
}
