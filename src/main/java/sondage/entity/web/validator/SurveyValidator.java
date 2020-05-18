package sondage.entity.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Survey;

@Service
public class SurveyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Survey.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Survey survey = (Survey) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "survey.name.empty", "Un nom est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate",
                "survey.endDate.empty", "Une date est requise.");

        if(survey.getItems() == null) return;

        for(int i = 0; i < survey.getItems().size(); ++i) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "items[" + i + "].nbPersMin",
                    "surveyItem.nbPersMin.empty", "Un nombre minimum est requis.");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "items[" + i + "].nbPersMax",
                    "surveyItem.nbPersMax.empty", "Un nombre maximum est requis.");

            if(survey.getItems().get(i).getNbPersMax() < survey.getItems().get(i).getNbPersMin()){
                errors.rejectValue("items[" + i + "].nbPersMax", "surveyItem.nbPersMax.tooSmall", "Le nombre maximum de personne doit être supérieur ou égale au nombre minimum");
            }
        }
    }
}