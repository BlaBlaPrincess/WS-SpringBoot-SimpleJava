package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;

import static org.mockito.AdditionalMatchers.aryEq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;

    private final String[] responseForGetAlgorithms
            = new String[]{"alg_1", "alg_2", "alg_3"};
    private final ArrayCountingAlgorithmsPresenterDto responseForGetAlgorithmsCounts
            = new ArrayCountingAlgorithmsPresenterDto(new HashMap<>());

    @Test
    @SneakyThrows
    void getAlgorithms() {
        // Arrange
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithms())
               .thenReturn(responseForGetAlgorithms);

        String urlTemplate = "/counts/int";
        ResultMatcher status = status().isOk();

        // Act + Assert
        mvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON))
           .andExpect(status)
           .andExpect(content().string(
                   mapper.writeValueAsString(responseForGetAlgorithms)
                                      ));
    }

    @Test
    @SneakyThrows
    void getAlgorithmsCounts() {
        // Arrange
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(aryEq(new Integer[]{1, 2, 3})))
               .thenReturn(responseForGetAlgorithmsCounts);

        String urlTemplate = "/counts/int/123";
        ResultMatcher status = status().isOk();

        // Act + Assert
        mvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON))
           .andExpect(status)
           .andExpect(content().string(
                   mapper.writeValueAsString(responseForGetAlgorithmsCounts)
                                      ));
    }

}