package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp.messagebuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TelegramBotNotifierAfterDispatchingProcessorMessageBuilder {
    String build(String responseBody, HttpServletRequest request, HttpServletResponse response);
}
