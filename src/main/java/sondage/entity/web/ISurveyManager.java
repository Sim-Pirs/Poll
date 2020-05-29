package sondage.entity.web;

import sondage.entity.model.*;
import java.util.Collection;
import java.util.List;

public interface ISurveyManager {

    // identifier un utilisateur
    boolean login(User user, String email, String password);

    // oublier l'utilisateur
    void logout(User user);

    void sendAccessSurveyMail(String emailTo, String token, String surveyName);

    void sendRecapMail(String emailTo, Collection<Choice> choices);

    void sendFinalAffectation(Respondent respondent);

    void makeAffectation(Survey survey);

    /* ****************** POLLSTER ******************** */
    Pollster findPollsterByEmail(String email);

    void savePollster(Pollster pollster);
    /* ***************** FIN POLLSTER ***************** */

    /* ******************* SURVEY ********************* */
    Survey saveSurvey(Survey survey);

    Survey findSurveyById(long id);

    Collection<Survey> findSurveyByPollsterId(long id);

    void deleteSurveyById(long id);
    /* ***************** FIN SURVEY ******************* */

    /* ****************** SURVEY ITEM ***************** */
    SurveyItem findSurveyItemById(long id);

    void deleteSurveyItemById(long id);
    /* *************** FIN SURVEY ITEM **************** */

    /* ****************** RESPONDENT ****************** */
    Respondent findRespondentById(long id);

    Respondent findRespondentByEmailAndSurveyId(String email, long id);

    Collection<Respondent> findAllRespondentsBySurveyId(long surveyId);

    Respondent findRespondentByToken(String token);

    void updateRespondentAccessById(long id, String token, boolean isExpired);

    void saveRespondent(Respondent respondent);

    void deleteRespondentsBySurveyId(long surveyId);

    void deleteRespondentById(long id);
    /* **************** FIN RESPONDENT **************** */

    /* ******************** CHOICE ******************** */
    Choice findChoiceByRespondentIdAndItemId(long idResp, long idItem);

    List<Choice> findAllChoiceByItemParentId(long id);

    Choice saveChoice(Choice choice);

    void deleteChoiceByRespondentIdAndItemId(long idResp, long idItem);
    /* ****************** FIN CHOICE ****************** */

}