package com.example.demo.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.MUser;
import com.example.demo.service.UserService;

public class UnusedValidator implements ConstraintValidator<Unused, String> {

	@Autowired
    private UserService userService;
 
    public void initialize(Unused constraintAnnotation) {
    }
 
    public boolean isValid(String value, ConstraintValidatorContext context) {
 
        MUser email = userService.findByEmail(value).orElse(null); // ここのvalueは入力値
        if(email == null){
        	return true;
        } 

        return false;
    }
}
