package sondage.entity.web.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Pollster;
import sondage.entity.model.SurveyItem;

@Service
public class SurveyItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pollster.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SurveyItem surveyItem = (SurveyItem) target;

        /*
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbPersMin",
                "surveyItem.nbPersMin.empty", "Un nombre minimum est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbPersMax",
                "surveyItem.nbPersMax.empty", "Un nombre maximum est requis.");


         */

        if(surveyItem.getNbPersMax() < surveyItem.getNbPersMin()){
            errors.reject("surveyItem.nbPersMax.tooSmall", "Le nombre maximum de personne doit être supérieur ou égale au nombre minimum");
        }
    }
}