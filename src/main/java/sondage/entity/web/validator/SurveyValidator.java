package sondage.entity.web.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sondage.entity.model.Pollster;
import sondage.entity.model.Survey;

@Service
public class SurveyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pollster.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Survey survey = (Survey) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "survey.name.empty", "Un nom est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate",
                "survey.endDate.empty", "Une date est requise.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
                "survey.description.empty", "Une description est requise.");


    }
}