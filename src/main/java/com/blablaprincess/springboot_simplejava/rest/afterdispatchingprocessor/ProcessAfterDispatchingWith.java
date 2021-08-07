package com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessAfterDispatchingWith {
    Class<? extends AfterDispatchingProcessor>[] value();
}
