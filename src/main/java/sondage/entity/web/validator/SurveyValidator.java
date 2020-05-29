package sondage.entity.web.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Survey;

import java.util.Date;

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
                "survey.name.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate",
                "survey.endDate.empty");

        //if(survey.getEndDate().compareTo(new Date()) <= 0)
        //    errors.rejectValue("endDate", "survey.endDate.expiredDate");

        if(survey.getItems() == null) return;

        for(int i = 0; i < survey.getItems().size(); ++i) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "items[" + i + "].nbPersMin",
                    "surveyItem.nbPersMin.empty");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "items[" + i + "].nbPersMax",
                    "surveyItem.nbPersMax.empty");

            if(survey.getItems().get(i).getNbPersMax() < survey.getItems().get(i).getNbPersMin()){
                errors.rejectValue("items[" + i + "].nbPersMax", "surveyItem.nbPersMax.tooSmall");
            }
        }
    }
}