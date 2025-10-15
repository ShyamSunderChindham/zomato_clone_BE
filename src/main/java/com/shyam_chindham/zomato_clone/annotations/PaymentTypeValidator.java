package com.shyam_chindham.zomato_clone.annotations;

import com.shyam_chindham.zomato_clone.entities.enums.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentTypeValidator implements ConstraintValidator<PaymentTypeValidation,String> {
    @Override
    public boolean isValid(String paymentType, ConstraintValidatorContext constraintValidatorContext) {
        if(paymentType == null) return false;
        List<String> paymentTypes = Arrays.stream(PaymentMethod.values())
                .map(Enum::name)  // Convert each enum to its name
                .collect(Collectors.toList());
        return paymentTypes.contains(paymentType);
    }
}
