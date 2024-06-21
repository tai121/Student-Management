package com.taipham.studentmanagement.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint,String>{

 
    
    @Override
    public void initialize(PasswordConstraint constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern patternHoa = Pattern.compile("[A-Z]");
        Matcher matcherHoa = patternHoa.matcher(value);
        boolean coHoa = matcherHoa.find();

        Pattern patternThuong = Pattern.compile("[a-z]");
        Matcher matcherThuong = patternThuong.matcher(value);
        boolean coThuong = matcherThuong.find();

        Pattern patternSo = Pattern.compile("[0-9]");
        Matcher matcherSo = patternSo.matcher(value);
        boolean coSo = matcherSo.find();

        Pattern patternDacBiet = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcherDacBiet = patternDacBiet.matcher(value);
        boolean coDacBiet = matcherDacBiet.find();

        return coHoa && coThuong && coSo && coDacBiet;
    }
}
