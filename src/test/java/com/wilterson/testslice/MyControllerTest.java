package com.wilterson.testslice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(MyController.class)
public class MyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Test
    @DisplayName("Given an existent Post ID, when MyController is called, then returns the Post associated to the given ID")
    void givenExistentPost_WhenMyControllerCalled_ThenReturnPost() throws Exception {

        // given
        Long id = 1L;

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/post/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        // then
    }
}
