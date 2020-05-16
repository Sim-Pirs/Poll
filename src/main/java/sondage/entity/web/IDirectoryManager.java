package sondage.entity.web;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sondage.entity.model.Pollster;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.model.User;

import java.util.Collection;
import java.util.Date;

public interface IDirectoryManager {

    // créer un utilisateur anonyme
    User newUser();

    // identifier un utilisateur
    boolean login(User user, String email, String password);

    // oublier l'utilisateur
    void logout(User user);


    /* ****************** POLLSTER ******************** */
    Pollster findPollsterByEmail(String email);

    void savePollster(Pollster pollster);
    /* ***************** FIN POLLSTER ***************** */

    /* ******************* SURVEY ********************* */
    Survey saveSurvey(Survey survey);

    Survey findSurveyById(long id);

    Collection<Survey> findSurveyByPollsterId(long id);

    void removeSurveyById(long id);
    /* ***************** FIN SURVEY ******************* */

    /* ****************** SURVEY ITEM ***************** */
    SurveyItem findSurveyItemById(long id);

    void deleteSurveyItemById(long id);
    /* *************** FIN SURVEY ITEM **************** */
}