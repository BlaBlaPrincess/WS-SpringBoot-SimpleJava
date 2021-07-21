package com.blablaprincess.springboot_simplejava.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SwaggerConfig.class)
class SwaggerConfigMvcIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @DisplayName("GET /swagger-ui.html")
    void get() {
        // Act + Assert
        mvc.perform(MockMvcRequestBuilders.get("/swagger-ui.html"))
           .andExpect(status().isOk());
    }

}