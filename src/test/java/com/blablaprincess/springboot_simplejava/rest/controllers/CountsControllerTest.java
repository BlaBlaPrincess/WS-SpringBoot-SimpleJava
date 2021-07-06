package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean(reset = MockReset.NONE)
    private ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;

    private final String[] responseForGetIntegerAlgorithms
            = new String[]{"alg_1", "alg_2", "alg_3"};
    private final ArrayCountingAlgorithmsPresenterDto responseForGetIntegerAlgorithmsCounts
            = new ArrayCountingAlgorithmsPresenterDto(new HashMap<>());

    @BeforeAll
    void setup() {
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithms())
               .thenReturn(responseForGetIntegerAlgorithms);
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(any()))
               .thenReturn(responseForGetIntegerAlgorithmsCounts);
    }

    @SneakyThrows
    private ResultMatcher response(Object expected) {
        return content().string(mapper.writeValueAsString(expected));
    }

    private ResultMatcher noResponse() {
        return content().string("");
    }

    @DisplayName("GET")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getCases")
    @SneakyThrows
    void get(String url, ResultMatcher status, ResultMatcher resultMatcher) {
        // Act + Assert
        mvc.perform(MockMvcRequestBuilders.get(url)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status)
           .andExpect(resultMatcher);
    }

    private Stream<Arguments> getCases() {
        return Stream.of(
                arguments("/counts/int",          status().isOk(),           response(responseForGetIntegerAlgorithms)),
                arguments("/counts/int/100",      status().isOk(),           response(responseForGetIntegerAlgorithmsCounts)),
                arguments("/counts/int/-10",      status().isOk(),           response(responseForGetIntegerAlgorithmsCounts)),
                arguments("/counts/int/any",      status().isBadRequest(), noResponse()),

                arguments("/counts/integer",      status().isPermanentRedirect(), redirectedUrl("/counts/int")),
                arguments("/counts/integer/any",  status().isPermanentRedirect(), redirectedUrl("/counts/int/any")),
                arguments("/counts/integer/a/a",  status().isPermanentRedirect(), redirectedUrl("/counts/int/a/a"))
                        );
    }

}