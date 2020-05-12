package sondage.entity.web.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Pollster;

import org.apache.commons.validator.routines.EmailValidator;


@Service
public class PollsterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pollster.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pollster pollster = (Pollster) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "pollster.firstName", "Un prénom est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "pollster.lastName", "Un nom de famille est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "pollster.email", "Un email est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "pollster.password", "un mot de passe est requis.");

        if(!EmailValidator.getInstance().isValid(pollster.getEmail())){
            errors.reject("pollster.email", "Adresse mail invalide.");
        }
    }

}