package sondage.entity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sondage.entity.model.*;
import sondage.entity.services.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service()
public class Manager implements IDirectoryManager {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    ISurveyItemDAO surveyItemDAO;

    @Autowired
    IRespondentDAO respondentDAO;

    @Autowired
    IChoiceDAO choiceDAO;

    @Autowired
    public EmailServiceImpl emailSender;

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
    public void sendAccessSurveyMail(String emailTo, String token, String surveyName) {
        emailSender.sendAccessMail(emailTo, token, surveyName);
    }

    @Override
    public void sendRecapMail(String emailTo, Collection<Choice> choices) {
        emailSender.sendRecapMail(emailTo, choices);
    }

    @Override
    public Pollster findPollsterByEmail(String email) {
        return pollsterDAO.findByEmail(email);
    }

    @Override
    public void savePollster(Pollster pollster) {
        pollsterDAO.save(pollster);
    }

    @Override
    public Survey saveSurvey(Survey survey) {
        return surveyDAO.save(survey);
    }

    @Override
    public Survey findSurveyById(long id) {
        return surveyDAO.findById(id);
    }

    @Override
    public Collection<Survey> findSurveyByPollsterId(long id) {
        return surveyDAO.findByPollster_Id(id);
    }

    @Override
    public void deleteSurveyById(long id) {
        surveyDAO.deleteById(id);
    }

    @Override
    public SurveyItem findSurveyItemById(long id) {
        return surveyItemDAO.findById(id);
    }

    @Override
    public void deleteSurveyItemById(long id) {
        surveyItemDAO.deleteById(id);
    }

    @Override
    public Respondent findRespondentById(long id) {
        return respondentDAO.findById(id);
    }

    @Override
    public Respondent findRespondentByEmailAndSurveyId(String email, long id) {
        return respondentDAO.findByEmailAndSurvey_Id(email, id);
    }

    @Override
    public Collection<Respondent> findAllRespondentsBySurveyId(long surveyId) {
        return respondentDAO.findAllBySurvey_Id(surveyId);
    }

    @Override
    public Respondent findRespondentByToken(String token) {
        return respondentDAO.findByToken(token);
    }

    @Override
    public void updateRespondentAccessById(long id, String token, boolean isExpired) {
        respondentDAO.updateAccessById(id, token, isExpired);
    }

    @Override
    public void saveRespondent(Respondent respondent) {
        respondentDAO.save(respondent);
    }

    @Override
    public void deleteRespondentsBySurveyId(long surveyId) {
        respondentDAO.deleteAllBySurvey_Id(surveyId);
    }

    @Override
    public Choice findChoiceByRespondentIdAndItemId(long idResp, long idItem) {
        return choiceDAO.findByRespondent_IdAndItem_Id(idResp, idItem);
    }

    @Override
    public Choice saveChoice(Choice choice) {
        return choiceDAO.save(choice);
    }

    @Override
    public void deleteChoiceByRespondentIdAndItemId(long idResp, long idItem) {
        choiceDAO.deleteByRespondent_IdAndItem_Id(idResp, idItem);
    }
}