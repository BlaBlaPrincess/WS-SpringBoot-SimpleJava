package com.blablaprincess.springboot_simplejava.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigSpringIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /swagger-ui/")
    void getUi() throws Exception {
        // Act
        mvc.perform(MockMvcRequestBuilders.get("/swagger-ui/"))
                // Assert
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /swagger-resources")
    void getSwaggerResources() throws Exception {
        // Act
        mvc.perform(MockMvcRequestBuilders.get("/swagger-resources"))
                // Assert
                .andExpect(status().isOk());
    }

}