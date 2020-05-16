package sondage.entity.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sondage.entity.model.Pollster;

import sondage.entity.model.User;
import sondage.entity.web.Manager;


@Service
public class UserValidator implements Validator {

    @Autowired
    Manager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return Pollster.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

    }

}