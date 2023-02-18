package com.wilterson.demo.hystrix;

import com.wilterson.demo.model.Post;
import com.wilterson.demo.service.PostClientHttpInterface;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Feign supports <a href="https://www.baeldung.com/spring-cloud-netflix-hystrix">Hystrix</a>, so
 * if we have enabled it, we can implement the fallback pattern.</p>
 *
 * <p>With the fallback pattern, when a remote service call fails, rather than generating an
 * exception, the service consumer will execute an alternative code path to try to carry out
 * the action through another means.</p>
 *
 * <p>To achieve the goal, we need to enable Hystrix by adding feign.hystrix.enabled=true in the
 * properties file.</p>
 *
 * <p>This allows us to implement fallback methods that are called when the service fails</p>
 */
@Component
public class PostClientFallback implements PostClientHttpInterface {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(long id) {
        return null;
    }
}
