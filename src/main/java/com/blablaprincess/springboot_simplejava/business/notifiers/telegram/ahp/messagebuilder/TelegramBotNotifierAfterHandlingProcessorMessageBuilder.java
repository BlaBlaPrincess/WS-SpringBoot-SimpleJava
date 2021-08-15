package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp.messagebuilder;

import org.aspectj.lang.Signature;

public interface TelegramBotNotifierAfterHandlingProcessorMessageBuilder {
    String buildOnSuccess(Object[] args, Signature signature, Object response);
    String buildOnThrows(Object[] args, Signature signature, Throwable throwable);
}
