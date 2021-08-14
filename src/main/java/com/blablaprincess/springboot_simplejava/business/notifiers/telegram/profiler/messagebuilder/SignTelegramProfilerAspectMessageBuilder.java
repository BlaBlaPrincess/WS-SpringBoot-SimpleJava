package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.profiler.messagebuilder;

import org.aspectj.lang.Signature;

public interface SignTelegramProfilerAspectMessageBuilder {
    String build(Signature signature, double performance);
    String build(Signature signature, double performance, Throwable throwsException);
}
