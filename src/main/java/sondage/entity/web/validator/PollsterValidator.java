package sondage.entity.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Pollster;

import org.apache.commons.validator.routines.EmailValidator;
import sondage.entity.web.Manager;


@Service
public class PollsterValidator implements Validator {

    @Autowired
    Manager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return Pollster.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pollster pollster = (Pollster) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "pollster.firstName.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "pollster.lastName.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "pollster.email.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "pollster.password.empty");

        if(manager.findPollsterByEmail(pollster.getEmail()) != null){
            errors.rejectValue("email", "pollster.email.alreadyUsed");
        }

        if(!EmailValidator.getInstance().isValid(pollster.getEmail())){
            errors.rejectValue("email", "pollster.email.invalid");
        }

        boolean containUpper = false;
        boolean containLower = false;
        boolean containDigit = false;
        boolean containSpecial = false;

        if (pollster.getPassword().length() < 8 || pollster.getPassword().length() > 254) {
            errors.rejectValue("password", "pollster.password.badSize");
        }

        for (int i = 0; i < pollster.getPassword().length(); i++){
            char c = pollster.getPassword().charAt(i);

            if (!Character.isDigit(c) && !Character.isLetter(c) && c != ' ') {
                containSpecial = true;
            } else if (Character.isDigit(c)) {
                containDigit = true;
            } else if (Character.isUpperCase(c)) {
                containUpper = true;
            } else if (Character.isLowerCase(c)) {
                containLower = true;
            }
        }

        if(!containDigit || !containLower || !containUpper || !containSpecial){
            errors.rejectValue("password", "pollster.password.lackCarac");
        }

    }

}