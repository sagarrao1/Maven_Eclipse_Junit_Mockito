package com.wiredCoffee.Extn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({
		IllegalArumentExceptionHandlerExtension.class,
		RewardByConversionParameterResolver.class		
})
public @interface TestWithErrorHandler {

}
