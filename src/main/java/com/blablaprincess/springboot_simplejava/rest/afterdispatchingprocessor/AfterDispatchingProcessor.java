package com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AfterDispatchingProcessor {
    void process(String responseBody, HttpServletRequest request, HttpServletResponse response);
}
