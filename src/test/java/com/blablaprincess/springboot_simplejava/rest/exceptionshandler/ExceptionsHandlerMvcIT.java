package com.blablaprincess.springboot_simplejava.rest.exceptionshandler;

import com.blablaprincess.springboot_simplejava.business.common.exceptions.BusinessException;
import com.blablaprincess.springboot_simplejava.business.arraycounting.UnexpectedArrayCountingException;
import com.blablaprincess.springboot_simplejava.business.common.exceptions.EmptyArrayException;
import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import com.blablaprincess.springboot_simplejava.rest.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ExceptionsHandlerMvcIT.ExceptionTestConfig.ExceptionTestController.class)
class ExceptionsHandlerMvcIT {

    private static final String TARGET_PACKAGE = "com.blablaprincess.springboot_simplejava";
    private static final Random random = new Random();

    @TestConfiguration
    public static class ExceptionTestConfig {

        public interface Thrower {
            void doWork() throws RuntimeException;
        }

        @Bean
        public Thrower thrower() {
            return mock(Thrower.class);
        }

        @RestController
        public static class ExceptionTestController {
            final static String DO_STAFF_URI = "/exceptions_test__/do_stuff";

            @Autowired
            private Thrower thrower;

            @GetMapping(DO_STAFF_URI)
            @ResponseStatus(OK)
            public void doStuff() throws RuntimeException {
                thrower.doWork();
            }
        }
    }

    private static class TestParameter {
        private Throwable exception;
        private int code;
    }

    private static class TestParameterBuilder {
        private final TestParameter testParameter = new TestParameter();
        private Class<? extends Throwable> exceptionType;
        private String message = "";

        public TestParameterBuilder exceptionType(Class<? extends Throwable> exceptionType) {
            this.exceptionType = exceptionType;
            return this;
        }

        public TestParameterBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TestParameterBuilder setRandomMessage() {
            this.message = UUID.randomUUID().toString();
            return this;
        }

        public TestParameterBuilder code(int code) {
            testParameter.code = code;
            return this;
        }

        public TestParameterBuilder setRandomCode() {
            testParameter.code = random.nextInt();
            return this;
        }

        @SneakyThrows
        public TestParameter build() {
            testParameter.exception = this.exceptionType.getConstructor(String.class)
                                                        .newInstance(this.message);
            return testParameter;
        }
    }

    @MockBean
    private ExceptionTestConfig.Thrower thrower;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private static Stream<Arguments> getPackageExceptionCases() {
        return new Reflections(TARGET_PACKAGE, new SubTypesScanner(false))
                .getSubTypesOf(Throwable.class)
                .stream()
                .filter(item -> item.getPackage().getName().startsWith(TARGET_PACKAGE))
                .map(item -> new Object[]{item.getName().replace(TARGET_PACKAGE, ""), item})
                .map(Arguments::of);
    }

    private static Stream<TestParameter> getTestExceptions() {
        return Stream.of(
                new TestParameterBuilder().exceptionType(RestException.class)      .setRandomMessage().code(500),
                new TestParameterBuilder().exceptionType(BusinessException.class)  .setRandomMessage().code(500),
                new TestParameterBuilder().exceptionType(EmptyArrayException.class).setRandomMessage().code(500),
                new TestParameterBuilder().exceptionType(ValidationException.class).setRandomMessage().code(400),
                new TestParameterBuilder().exceptionType(UnexpectedArrayCountingException.class).setRandomMessage().code(500))
                     .map(TestParameterBuilder::build);
    }

    private static Stream<Arguments> getTestExceptionCases() {
        return getTestExceptions().map(item -> new Object[]{
                item.exception.getClass().getName().replace(TARGET_PACKAGE, ""), item})
                                  .map(Arguments::of);
    }

    @DisplayName("check if all exceptions processed")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getPackageExceptionCases")
    void checkIfAllExceptionsProcessed(final String description,
                                       final Class<? extends Throwable> packageExceptionType) throws RuntimeException {
        // Arrange
        List<TestParameter> testData = getTestExceptions().collect(Collectors.toList());

        // Act
        for (TestParameter testParam : testData) {
            if (packageExceptionType.isInstance(testParam.exception)) {
                return;
            }
        }

        // Assert
        throw new AssertionFailedError(String.format("Class %s does not have a handling test in ExceptionHandler",
                                                     packageExceptionType.getName()));
    }

    @DisplayName("handle exceptions")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestExceptionCases")
    void assertException(final String description, final TestParameter param) throws Exception {
        // Arrange
        doThrow(param.exception).when(thrower).doWork();

        // Act
        String jsonResult = mvc.perform(MockMvcRequestBuilders.get(ExceptionTestConfig.ExceptionTestController.DO_STAFF_URI))
                               // Assert
                               .andExpect(status().is(param.code))
                               .andReturn()
                               .getResponse()
                               .getContentAsString();

        ErrorDto result = mapper.readValue(jsonResult, ErrorDto.class);

        assertEquals(param.exception.getMessage(),                             result.getMessage());
        assertEquals(param.exception.getClass().getName(),                     result.getClassName());
        assertEquals(ExceptionTestConfig.ExceptionTestController.DO_STAFF_URI, result.getPath());

        verify(thrower, times(1)).doWork();
    }

}