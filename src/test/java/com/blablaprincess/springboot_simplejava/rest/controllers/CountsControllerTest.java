package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;

    private final String[] responseForGetIntegerAlgorithms
            = new String[]{"alg_1", "alg_2", "alg_3"};
    private final ArrayCountingAlgorithmsPresenterDto responseForGetIntegerAlgorithmsCounts
            = new ArrayCountingAlgorithmsPresenterDto(new HashMap<>());

    @BeforeEach
    void setup() {
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithms())
               .thenReturn(responseForGetIntegerAlgorithms);
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(new Integer[] {1, 0, 0}))
               .thenReturn(responseForGetIntegerAlgorithmsCounts);
        Mockito.when(integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(new Integer[] {1, 0}))
               .thenReturn(responseForGetIntegerAlgorithmsCounts);
    }

    @SneakyThrows
    private ResultMatcher response(Object expected) {
        return content().string(mapper.writeValueAsString(expected));
    }

    private ResultMatcher noResponse() {
        return content().string("");
    }

    private interface VerificationScenario {
        void verify();
    }

    private final VerificationScenario getAlgorithmsCallsOnce = () -> {
        verify(integersCountingAlgorithmsPresenterService, times(1)).getAlgorithms();
    };

    private final VerificationScenario getAlgorithmsCountsCallsOnce = () -> {
        verify(integersCountingAlgorithmsPresenterService, times(1)).getAlgorithmsCounts(any());
    };

    private final VerificationScenario verifyNoInteractions = () -> {
        verifyNoInteractions(integersCountingAlgorithmsPresenterService);
    };

    @DisplayName("GET")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getCases")
    @SneakyThrows
    void get(String url, ResultMatcher status, ResultMatcher resultMatcher, VerificationScenario verificationScenario) {
        // Act + Assert
        mvc.perform(MockMvcRequestBuilders.get(url))
           .andExpect(status)
           .andExpect(resultMatcher);

        verificationScenario.verify();
    }

    private Stream<Arguments> getCases() {
        return Stream.of(
                arguments("/counts/int",          status().isOk(),           response(responseForGetIntegerAlgorithms),       getAlgorithmsCallsOnce),
                arguments("/counts/int/100",      status().isOk(),           response(responseForGetIntegerAlgorithmsCounts), getAlgorithmsCountsCallsOnce),
                arguments("/counts/int/-10",      status().isOk(),           response(responseForGetIntegerAlgorithmsCounts), getAlgorithmsCountsCallsOnce),
                arguments("/counts/int/any",      status().isBadRequest(), noResponse(),                                      verifyNoInteractions),

                arguments("/counts/integer",      status().isPermanentRedirect(), redirectedUrl("/counts/int"),     verifyNoInteractions),
                arguments("/counts/integer/any",  status().isPermanentRedirect(), redirectedUrl("/counts/int/any"), verifyNoInteractions),
                arguments("/counts/integer/a/a",  status().isPermanentRedirect(), redirectedUrl("/counts/int/a/a"), verifyNoInteractions)
                        );
    }

}