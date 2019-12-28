package com.ppmtool.validator;


import com.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals (clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(user.getPassword().length() < 6){
            errors.rejectValue("password","Length","Password must be 6 characters minimum.");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Password must match.");
        }
    }
}
