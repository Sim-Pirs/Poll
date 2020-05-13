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
        
        if(!pollster.getFirstName().matches("[A-Z][a-z]+([-][A-Z]([a-z])+)?")) {
        	errors.reject("pollster.firstName", "Le format du Prénom n'est pas valable ! Format attendu : Jean ou Jean-Marie");
        }
        
        if(!pollster.getLastName().matches("[A-Z][a-z]+([-][A-Z]([a-z])+)?")) {
        	errors.reject("pollster.lastName", "Le format de Nom n'est pas valable ! Format attendu : Jean ou Jean-Marie");
        }
        
        if(!pollster.getPassword().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*-_=+])[a-zA-Z0-9!@#$%^&*-_=+](?=\\\\S+$).{8,15}")) {
        	errors.reject("pollster.email", "Le mot de passe n'es pas valable ! Format attendu : minCharacters=8, maxCharacters=15,/n must include at least a lower case letter, a capital letter, a number and a special character among !@#$%^&*-_=+");
        }
    }

}