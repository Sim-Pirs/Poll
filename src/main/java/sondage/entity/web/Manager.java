package sondage.entity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sondage.entity.model.Pollster;
import sondage.entity.model.Survey;
import sondage.entity.model.User;
import sondage.entity.services.IPollsterDAO;
import sondage.entity.services.ISurveyDAO;

import java.util.Collection;

@Service()
public class Manager implements IDirectoryManager {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    /* ******************************** SESSION ******************************** */
    @Override
    public User newUser() {
        return new User();
    }

    @Override
    public boolean login(User user, String email, String password) {
        Pollster pollster = pollsterDAO.findByEmailAndPassword(email, password);

        if (pollster == null) {
            user.setAsError(true);
            user.addErrorMessage("Email ou mot de passe incorecte.");
            return false;
        }

        user.setPollster(pollster);
        user.setConnected(true);
        return true;
    }

    @Override
    public void logout(User user) {
        user.setPollster(null);
        user.setConnected(false);
        user.setAsError(false);
        user.setErrorMessages(null);
    }
    /* ************************************************************************* */

    @Override
    public void savePollster(Pollster pollster) {
        pollsterDAO.save(pollster);
    }

    @Override
    public void saveSurvey(Survey survey) {
        surveyDAO.save(survey);
    }

    @Override
    public Collection<Survey> findAllSurvey() {
        return (Collection<Survey>) surveyDAO.findAll();
    }
}