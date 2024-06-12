package br.com.desafio.payableaccount.core.validation;

import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

public class PayableAccountStatusValidator implements ConstraintValidator<PayableAccountStatusValid, PayableAccountStatus> {

    @Override
    public void initialize(PayableAccountStatusValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PayableAccountStatus value, ConstraintValidatorContext context) {

        if (Objects.isNull(value)) {
            return false;
        }

        return Arrays.stream(PayableAccountStatus.values()).anyMatch(status -> status.name().equals(value.toString()));
    }
}

