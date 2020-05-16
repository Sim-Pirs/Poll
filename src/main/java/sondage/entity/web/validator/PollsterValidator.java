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
                "pollster.firstName.empty", "Un prénom est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "pollster.lastName.empty", "Un nom de famille est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "pollster.email.empty", "Un email est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "pollster.password.empty", "un mot de passe est requis.");

        if(manager.findPollsterByEmail(pollster.getEmail()) != null){
            errors.reject("pollster.email.alreadyUsed", "Adresse mail déja utilisé.");
        }

        if(!EmailValidator.getInstance().isValid(pollster.getEmail())){
            errors.reject("pollster.email.invalidFormat", "Adresse mail invalide.");
        }

    }

}