package jp.flowzen.flowzenapi.validator;

import org.springframework.stereotype.Component;

import jp.flowzen.flowzenapi.annotation.UniqueEmail;
import jp.flowzen.flowzenapi.service.user.UserServiceHelper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * UniqueEmailValidator
 */
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private UserServiceHelper userServiceHelper;

  public UniqueEmailValidator(UserServiceHelper userServiceHelper) {
    this.userServiceHelper = userServiceHelper;
  }

  @Override
  public void initialize(UniqueEmail constraintAnnotation) {
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null && !userServiceHelper.emailExists(email);
  }
}