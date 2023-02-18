package com.wilterson.demo.service;

import feign.Response;
import feign.codec.ErrorDecoder;

public class PostClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 400:
                return new RuntimeException("Internal Server Error");
            case 404:
                return new RuntimeException("Not Found");
            default:
                return new RuntimeException("Generic error");
        }
    }
}
