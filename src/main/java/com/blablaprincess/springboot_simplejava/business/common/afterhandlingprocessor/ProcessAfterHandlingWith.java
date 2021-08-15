package com.blablaprincess.springboot_simplejava.business.common.afterhandlingprocessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessAfterHandlingWith {
    Class<? extends AfterHandlingProcessor>[] value();
}
