package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.profiler;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.profiler.messagebuilder.SignTelegramProfilerAspectMessageBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SignTelegramProfilerAspectTest {

    private final TelegramBotNotifierService service =
            mock(TelegramBotNotifierService.class);
    private final SignTelegramProfilerAspectMessageBuilder builder =
            mock(SignTelegramProfilerAspectMessageBuilder.class);

    private final SignTelegramProfilerAspect aspect = new SignTelegramProfilerAspect(service, builder);

    private final ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
    private final Object[] args = new Object[]{1, 2, 3};

    @BeforeEach
    void setup() {
        when(proceedingJoinPoint.getArgs()).thenReturn(args);
        when(proceedingJoinPoint.getSignature()).thenReturn(mock(Signature.class));
    }

    @Test
    void testProceedingJoinPointDoesNotThrows() throws Throwable {
        // Arrange
        String message = "successful";
        when(builder.build(any(Signature.class), anyDouble())).thenReturn(message);

        // Act + Assert
        assertDoesNotThrow(() -> aspect.around(proceedingJoinPoint));

        verify(proceedingJoinPoint).proceed(eq(args));
        verify(service).sendNotification(eq(message));
    }

    @Test
    void testProceedingJoinPointThrows() throws Throwable {
        // Arrange
        String message = "not successful";
        Throwable throwable = new Throwable();
        when(proceedingJoinPoint.proceed(eq(args))).thenThrow(throwable);
        when(builder.build(any(Signature.class), anyDouble(), eq(throwable))).thenReturn(message);

        // Act + Assert
        assertThrows(Throwable.class, () -> aspect.around(proceedingJoinPoint));

        verify(proceedingJoinPoint).proceed(eq(args));
        verify(service).sendNotification(eq(message));
    }

}