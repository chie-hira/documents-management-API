package com.files.management.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrivacyTypeValidator implements ConstraintValidator<ValidPrivacyType, String> {

  @Override
  public void initialize(ValidPrivacyType constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true; // @NotBlankでチェックされるため、ここでは空を許容する
    }
    return value.equals("公開") || value.equals("非公開");
  }
}
