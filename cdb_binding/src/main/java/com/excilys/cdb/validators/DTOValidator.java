package com.excilys.cdb.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class DTOValidator {

  public static <T> List<String> validate(T objectDTO) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> violations = validator.validate(objectDTO);
    List<String> errors = new ArrayList<>();
    if (!violations.isEmpty()) {
      for (ConstraintViolation<T> constraintViolation : violations) {
        errors.add("Value '" + constraintViolation.getInvalidValue()
            + "' is invalid for the field '" + constraintViolation.getPropertyPath() + "' : "
            + constraintViolation.getMessage());
      }
    }
    return errors;
  }

}
