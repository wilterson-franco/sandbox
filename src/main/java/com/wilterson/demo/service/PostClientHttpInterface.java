package com.wilterson.demo.service;

import com.wilterson.demo.model.Post;
import com.wilterson.demo.hystrix.PostClientFallback;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/", configuration = {PostClientConfiguration.class,
        PostClientInterceptor.class, PostClientLoggerConfiguration.class}, fallback = PostClientFallback.class)
public interface PostClientHttpInterface {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable long postId);
}
