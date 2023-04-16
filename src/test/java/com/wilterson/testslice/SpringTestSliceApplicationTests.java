package com.wilterson.testslice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTestSliceApplicationTests {

    @Autowired
    MyController myController;

    @Test
    void contextLoads() {
        assertThat(myController).isNotNull();
    }
}
