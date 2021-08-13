package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
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
            String message = "App has handled the method:\n" +
                    joinPoint.getSignature().toString() + "\n\n" +
                    "▫️ Successful: ";
            if (throwable == null) {
                message += "yes \uD83D\uDFE2\n";
            } else {
                message += "no \uD83D\uDD34\n" +
                        "▫️ Cause:" + throwable + "\n";
            }
            message += "▫️ By: " + clock.getTotalTimeSeconds() + " sec";
            telegramBotNotifierService.sendNotification(message);
        }
    }

}
