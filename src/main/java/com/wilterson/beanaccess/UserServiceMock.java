package com.wilterson.beanaccess;

import org.springframework.stereotype.Service;

@Service
public class UserServiceMock implements UserService {

    public User getCurrentUser() {
        return new User("Wilterson", "Franco");
    }
}
