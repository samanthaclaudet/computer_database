package com.excilys.cdb.validators;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<Date, String> {

	public static final String DATE_REGEX = "^(19|20)[0-9][0-9](-)((0[1-9])|(1[0-2]))(-)((0[1-9])|([1-2][0-9])|(3[0-1]))(T|\\s)(([0-1][0-9])|(2[0-3])):([0-5][0-9])";
	private static final Pattern P = Pattern.compile(DATE_REGEX);

	@Override
	public void initialize(Date arg0) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.isEmpty()) {
			return true;
		}
		return P.matcher(value).matches();
	}

}
