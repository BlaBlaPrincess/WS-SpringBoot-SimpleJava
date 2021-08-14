package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import org.aspectj.lang.Signature;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SignTelegramProfilerAspectMessageBuilderImpl implements SignTelegramProfilerAspectMessageBuilder {

    private String build(Signature signature, @Nullable Throwable throwsException, double performance) {
        String message = "App has handled the method:\n" +
                signature.toString() + "\n\n" +
                "▫️ Successful: ";
        if (throwsException == null) {
            message += "yes \uD83D\uDFE2\n";
        } else {
            message += "no \uD83D\uDD34\n" +
                    "▫️ Cause:" + throwsException + "\n";
        }
        message += "▫️ By: " + performance + " sec";
        return message;
    }

    @Override
    public String build(Signature signature, double performance) {
        return build(signature, null, performance);
    }

    @Override
    public String build(Signature signature, double performance, Throwable throwsException) {
        return build(signature, throwsException, performance);
    }

}
