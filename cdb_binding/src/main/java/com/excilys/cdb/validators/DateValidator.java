package com.excilys.cdb.validators;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;

/**
 * Validates the date format
 * 
 * @author sclaudet
 *
 */
public class DateValidator implements ConstraintValidator<Date, String> {

  @Autowired
  private MessageSource message;

  @Override
  public void initialize(Date arg0) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value.isEmpty()) {
      return true;
    }

    Locale userLocale = LocaleContextHolder.getLocale();
    String regex = message.getMessage("label.regex", null, userLocale);
    Pattern pattern = Pattern.compile(regex);
    boolean bool = pattern.matcher(value).matches();
    return bool;
  }

}
