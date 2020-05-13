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

        System.err.println(errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
                "surveyItem.description.empty", "Une description est requise.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nb_pers_min",
                "surveyItem.nbPersMin.empty", "Un nombre minimum est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nb_pers_max",
                "susurveyItemrvey.nbPersMax.empty", "Un nombre maximum est requis.");
        
        if(!surveyItem.getDescription().matches("")) {
        	errors.reject("surveyItem.description", "Caractères spéciaux non autorisés");
        }
        
        if(surveyItem.getNbPersMin()>0) {
        	errors.reject("surveyItem.nbPersMin", "Ce nombre ne peut pas être négatif");
        }
        
        if(surveyItem.getNbPersMax()>0) {
        	errors.reject("surveyItem.nbPersMax", "Ce nombre ne peut pas être négatif");
        }
        
        /*TODO*/
        /*voir si on vérifie le nombre de tags et si il peut être null*/
    }
}