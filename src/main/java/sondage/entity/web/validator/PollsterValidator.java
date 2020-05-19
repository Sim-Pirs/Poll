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

    }

}