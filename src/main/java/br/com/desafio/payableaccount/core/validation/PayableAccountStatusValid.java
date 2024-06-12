package br.com.desafio.payableaccount.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PayableAccountStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PayableAccountStatusValid {

    String message() default "Invalid status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
