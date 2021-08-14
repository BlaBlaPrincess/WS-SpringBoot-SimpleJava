package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.aspect.enabled", havingValue = "true")
public class SignTelegramProfilerAspect {

    private final TelegramBotNotifierService telegramBotNotifierService;
    private final SignTelegramProfilerAspectMessageBuilder builder;

    @Around("@annotation(com.blablaprincess.springboot_simplejava.business.notifiers.telegram.SignTelegramProfiler)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch clock = new StopWatch();
        Throwable throwable = null;
        try {
            clock.start();
            try {
                Object[] args = joinPoint.getArgs();
                return joinPoint.proceed(args);
            } catch (Throwable ex) {
                throwable = ex;
                throw throwable;
            }
        } finally {
            clock.stop();
            String message;
            Signature signature = joinPoint.getSignature();
            double performance = clock.getTotalTimeSeconds();
            if (throwable == null) {
                message = builder.build(signature, performance);
            } else {
                message = builder.build(signature, performance, throwable);
            }
            telegramBotNotifierService.sendNotification(message);
        }
    }

}
