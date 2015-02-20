package com.excilys.cdb.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateValidator.class)
@Target(value = ElementType.FIELD)
//@Pattern(regexp = "^(19|20)[0-9][0-9](-)((0[1-9])|(1[0-2]))(-)((0[1-9])|([1-2][0-9])|(3[0-1]))(T|\\s)(([0-1][0-9])|(2[0-3])):([0-5][0-9])")
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
	String message() default "Date format incorrect";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
