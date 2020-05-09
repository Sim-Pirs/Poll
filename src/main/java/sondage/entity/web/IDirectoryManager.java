package sondage.entity.web;

import sondage.entity.model.Pollster;
import sondage.entity.model.Survey;
import sondage.entity.model.User;

import java.util.Collection;

public interface IDirectoryManager {

    // créer un utilisateur anonyme
    User newUser();

    // identifier un utilisateur
    boolean login(User user, String email, String password);

    // oublier l'utilisateur
    void logout(User user);


    void savePollster(Pollster pollster);

    void saveSurvey(Survey survey);

    Collection<Survey> findAllSurvey();
}