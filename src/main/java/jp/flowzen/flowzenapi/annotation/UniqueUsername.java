package jp.flowzen.flowzenapi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.flowzen.flowzenapi.validator.UniqueUsernameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * UniqueUsername
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {
  String message() default "ユーザーネームは既に使用されています";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default{};
}