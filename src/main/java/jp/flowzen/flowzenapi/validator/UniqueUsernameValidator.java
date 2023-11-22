package jp.flowzen.flowzenapi.validator;

import org.springframework.stereotype.Component;

import jp.flowzen.flowzenapi.annotation.UniqueUsername;
import jp.flowzen.flowzenapi.service.user.UserServiceHelper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * UniqueUsernameValidator
 */
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private UserServiceHelper userServiceHelper;

  public UniqueUsernameValidator(UserServiceHelper userServiceHelper) {
    this.userServiceHelper = userServiceHelper;
  }

  @Override
  public void initialize(UniqueUsername constraintAnnotation) {
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return username != null && !userServiceHelper.usernameExists(username);
  }
}