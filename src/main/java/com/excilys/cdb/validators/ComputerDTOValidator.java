package com.excilys.cdb.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.excilys.cdb.dto.ComputerDTO;

public class ComputerDTOValidator{

	public static List<String> validate(ComputerDTO computerDTO) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ComputerDTO>> violations = validator.validate(computerDTO);
		List<String> errors = new ArrayList<>();
		if (!violations.isEmpty()) {
			for (ConstraintViolation<ComputerDTO> constraintViolation : violations) {
				errors.add("Value '" + constraintViolation.getInvalidValue() + "' is invalid for the field '"
						+ constraintViolation.getPropertyPath() + "' : " + constraintViolation.getMessage());
			}
		}
		return errors;
	}

}
