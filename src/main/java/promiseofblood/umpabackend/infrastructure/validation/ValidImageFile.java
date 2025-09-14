package promiseofblood.umpabackend.infrastructure.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageFileValidator.class})
public @interface ValidImageFile {
  String message() default "유효하지 않은 이미지 파일입니다 (jpg/jpeg만 허용)";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
