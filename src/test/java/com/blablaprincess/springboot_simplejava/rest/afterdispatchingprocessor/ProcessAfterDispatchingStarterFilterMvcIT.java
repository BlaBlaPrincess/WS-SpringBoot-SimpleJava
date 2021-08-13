package com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Stream;

import static com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.ProcessAfterDispatchingStarterFilterMvcIT.TestConfig.*;
import static com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.ProcessAfterDispatchingStarterFilterMvcIT.TestConfig.ExceptionsHandler.EXCEPTION_STATUS_CODE;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {
        ProcessAfterDispatchingStarterFilterMvcIT.TestConfig.TestController.class,
        ProcessAfterDispatchingStarterFilterMvcIT.TestConfig.AnnotatedTestController.class,
        ProcessAfterDispatchingStarterFilterMvcIT.TestConfig.AnnotatedTwiceTestController.class
})
class ProcessAfterDispatchingStarterFilterMvcIT {

    @TestConfiguration
    public static class TestConfig {

        final static String BASE_DO_STUFF_URI = "/process_after_dispatching_starter_filter_test__";

        @Autowired
        private Worker worker;

        @Autowired
        ProcessAfterDispatchingStarterFilter processAfterDispatchingStarterFilter;

        @Bean
        public Worker worker() {
            return mock(Worker.class);
        }

        @Bean
        public AfterDispatchingProcessor afterDispatchingProcessor() {
            return mock(AfterDispatchingProcessor.class);
        }

        @Bean
        public MockMvc mockMvc() {
            return MockMvcBuilders.standaloneSetup(new TestController(), new AnnotatedTestController(), new AnnotatedTwiceTestController())
                    .setControllerAdvice(new ExceptionsHandler())
                    .addFilters(processAfterDispatchingStarterFilter)
                    .build();
        }

        public interface Worker {
            String doWork() throws RuntimeException;
        }

        @RestController
        public class TestController {

            public final static String DO_STAFF_URI = "/controller/do_stuff";
            public final static String DO_STAFF_AND_PROCESS_URI = "/controller/do_stuff_and_process";
            public final static String DO_STAFF_AND_PROCESS_TWICE_URI = "/controller/do_stuff_and_process_twice";

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_URI)
            public String doStuff() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_URI)
            @ProcessAfterDispatchingWith(AfterDispatchingProcessor.class)
            public String doStuffAndProcess() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_TWICE_URI)
            @ProcessAfterDispatchingWith({AfterDispatchingProcessor.class, AfterDispatchingProcessor.class})
            public String doStuffAndProcessTwice() throws RuntimeException {
                return worker.doWork();
            }
        }

        @RestController
        @ProcessAfterDispatchingWith(AfterDispatchingProcessor.class)
        public class AnnotatedTestController {

            public final static String DO_STAFF_URI = "/annotated_controller/do_stuff";
            public final static String DO_STAFF_AND_PROCESS_URI = "/annotated_controller/do_stuff_and_process";
            public final static String DO_STAFF_AND_PROCESS_TWICE_URI = "/annotated_controller/do_stuff_and_process_twice";

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_URI)
            public String doStuff() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_URI)
            @ProcessAfterDispatchingWith(AfterDispatchingProcessor.class)
            public String doStuffAndProcess() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_TWICE_URI)
            @ProcessAfterDispatchingWith({AfterDispatchingProcessor.class, AfterDispatchingProcessor.class})
            public String doStuffAndProcessTwice() throws RuntimeException {
                return worker.doWork();
            }
        }

        @RestController
        @ProcessAfterDispatchingWith({AfterDispatchingProcessor.class, AfterDispatchingProcessor.class})
        public class AnnotatedTwiceTestController {

            public final static String DO_STAFF_URI = "/annotated_twice_controller/do_stuff";
            public final static String DO_STAFF_AND_PROCESS_URI = "/annotated_twice_controller/do_stuff_and_process";
            public final static String DO_STAFF_AND_PROCESS_TWICE_URI = "/annotated_twice_controller/do_stuff_and_process_twice";

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_URI)
            public String doStuff() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_URI)
            @ProcessAfterDispatchingWith(AfterDispatchingProcessor.class)
            public String doStuffAndProcess() throws RuntimeException {
                return worker.doWork();
            }

            @GetMapping(BASE_DO_STUFF_URI + DO_STAFF_AND_PROCESS_TWICE_URI)
            @ProcessAfterDispatchingWith({AfterDispatchingProcessor.class, AfterDispatchingProcessor.class})
            public String doStuffAndProcessTwice() throws RuntimeException {
                return worker.doWork();
            }
        }

        @RestControllerAdvice
        public static class ExceptionsHandler {

            public final static int EXCEPTION_STATUS_CODE = 998;

            @ExceptionHandler(RuntimeException.class)
            public ResponseEntity<String> handleServletException(Exception exception) {
                return new ResponseEntity<>(exception.getMessage(), null, EXCEPTION_STATUS_CODE);
            }
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestConfig.Worker worker;

    @MockBean
    private AfterDispatchingProcessor afterDispatchingProcessor;

    @DisplayName("test when controller does not throw")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestCases")
    void testWhenControllerDoesNotThrow(String uri, int wantedNumberOfAfterDispatchingProcessorInvocations) throws Exception {
        // Arrange
        String response = "Some response message";
        Mockito.when(worker.doWork()).thenReturn(response);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(BASE_DO_STUFF_URI + uri))
           // Assert
           .andExpect(status().isOk());

        verify(worker).doWork();
        verify(afterDispatchingProcessor, times(wantedNumberOfAfterDispatchingProcessorInvocations)).process(any(), any(), any());
        verify(afterDispatchingProcessor, times(wantedNumberOfAfterDispatchingProcessorInvocations)).process(eq(response), any(), any());
    }

    @DisplayName("test when controller do throw")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestCases")
    void testWhenControllerDoThrow(String uri, int wantedNumberOfAfterDispatchingProcessorInvocations) throws Exception {
        // Arrange
        String message = "Some exception message";
        doThrow(new RuntimeException(message)).when(worker).doWork();

        // Act
        mvc.perform(MockMvcRequestBuilders.get(BASE_DO_STUFF_URI + uri))
           // Assert
           .andExpect(status().is(EXCEPTION_STATUS_CODE));

        verify(worker).doWork();
        verify(afterDispatchingProcessor, times(wantedNumberOfAfterDispatchingProcessorInvocations)).process(any(), any(), any());
        verify(afterDispatchingProcessor, times(wantedNumberOfAfterDispatchingProcessorInvocations)).process(eq(message), any(), any());
    }

    private static Stream<Arguments> getTestCases() {
        return Stream.of(
                arguments(TestConfig.TestController.DO_STAFF_URI, 0),
                arguments(TestConfig.TestController.DO_STAFF_AND_PROCESS_URI, 1),
                arguments(TestConfig.TestController.DO_STAFF_AND_PROCESS_TWICE_URI, 2),
                arguments(TestConfig.AnnotatedTestController.DO_STAFF_URI, 1),
                arguments(TestConfig.AnnotatedTestController.DO_STAFF_AND_PROCESS_URI, 1),
                arguments(TestConfig.AnnotatedTestController.DO_STAFF_AND_PROCESS_TWICE_URI, 2),
                arguments(TestConfig.AnnotatedTwiceTestController.DO_STAFF_URI, 2),
                arguments(TestConfig.AnnotatedTwiceTestController.DO_STAFF_AND_PROCESS_URI, 1),
                arguments(TestConfig.AnnotatedTwiceTestController.DO_STAFF_AND_PROCESS_TWICE_URI, 2)
        );
    }

}