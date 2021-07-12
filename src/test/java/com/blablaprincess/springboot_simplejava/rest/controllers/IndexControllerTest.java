package com.blablaprincess.springboot_simplejava.rest.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndexController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @DisplayName("GET /")
    void get() {
        // Act + Assert
        mvc.perform(MockMvcRequestBuilders.get("/"))
           .andExpect(status().isPermanentRedirect());
    }

}