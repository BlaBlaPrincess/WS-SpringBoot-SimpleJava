package com.blablaprincess.springboot_simplejava.business.methodcalls;

import com.blablaprincess.springboot_simplejava.business.afterhandlingprocessor.AfterHandlingProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MethodCallsHistoryAfterHandlingProcessor implements AfterHandlingProcessor {

    private final MethodCallsHistory methodCallsHistoryService;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void process(Object[] args, Signature signature, Object response, boolean successful) {

        String argsString = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        String responseBody = mapper.writeValueAsString(response);
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        methodCallsHistoryService.saveCall(MethodCallDescriptionEntity.builder()
                .method(signature.toShortString())
                .args(argsString)
                .successful(successful)
                .response(responseBody)
                .timestamp(timestamp)
                .build());
    }

    @Override
    public void processOnSuccess(Object[] args, Signature signature, Object response) {
        process(args, signature, response, true);
    }

    @Override
    public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        process(args, signature, throwable, false);
    }
}
