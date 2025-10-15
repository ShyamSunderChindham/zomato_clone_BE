package com.shyam_chindham.zomato_clone.annotations;

import com.shyam_chindham.zomato_clone.entities.enums.MenuItemType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemTypeValidator implements ConstraintValidator<MenuItemTypeValidation, String> {
    @Override
    public boolean isValid(String menuItemType, ConstraintValidatorContext constraintValidatorContext) {
        if(menuItemType == null) return false;
        List<String> menuItemTypes = Arrays.stream(MenuItemType.values())
                .map(Enum::name)  // Convert each enum to its name
                .collect(Collectors.toList());
        return menuItemTypes.contains(menuItemType);
    }
}
