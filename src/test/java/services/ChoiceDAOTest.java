package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sondage.Starter;
import sondage.entity.model.*;
import sondage.entity.services.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
@DataJpaTest
public class ChoiceDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    IChoiceDAO choiceDAO;



    @Test
    public void testSave_GoodChoice_NoExceptionThrow(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Une description.");
        surveyItem.setTags(new HashSet<>());
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gg.com");
        respondent.addTag("M1");
        respondent.addTag("ILD");
        respondent.setExpired(false);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);
        survey.addRespondent(respondent);

        surveyDAO.save(survey);

        Choice choice = new Choice();
        choice.setScore(1);
        choice.setRespondent(respondent);
        choice.setItem(surveyItem);

        assertDoesNotThrow(() -> {
            choiceDAO.save(choice);
        });
    }

    @Test
    public void testSave_withoutScore_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Une description.");
        surveyItem.setTags(new HashSet<>());
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gg.com");
        respondent.addTag("M1");
        respondent.addTag("ILD");
        respondent.setExpired(false);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);
        survey.addRespondent(respondent);

        surveyDAO.save(survey);

        Choice choice = new Choice();
        choice.setRespondent(respondent);
        choice.setItem(surveyItem);


        assertThrows(ConstraintViolationException.class, () -> {
            System.err.println(choice);
            choiceDAO.save(choice);
        });
    }

    @Test
    public void testSave_withoutRespondent_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Une description.");
        surveyItem.setTags(new HashSet<>());
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);

        surveyDAO.save(survey);

        Choice choice = new Choice();
        choice.setScore(1);
        choice.setItem(surveyItem);

        assertThrows(DataIntegrityViolationException.class, () -> {
            choiceDAO.save(choice);
        });
    }

    @Test
    public void testSave_WithoutItem_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gg.com");
        respondent.addTag("M1");
        respondent.addTag("ILD");
        respondent.setExpired(false);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addRespondent(respondent);

        surveyDAO.save(survey);

        Choice choice = new Choice();
        choice.setScore(1);
        choice.setRespondent(respondent);

        assertThrows(DataIntegrityViolationException.class, () -> {
            choiceDAO.save(choice);
        });
    }
}
