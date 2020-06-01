package services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sondage.Starter;
import sondage.entity.model.Pollster;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.services.IPollsterDAO;
import sondage.entity.services.ISurveyDAO;
import sondage.entity.services.ISurveyItemDAO;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
@DataJpaTest
public class SurveyItemDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    ISurveyItemDAO surveyItemDAO;



    @Test
    public void testSave_AddGoodSurveyItem_NoExceptionThrow() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain45@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setResultObtained(false);
        survey.setPollster(pollster);
        survey.setResultObtained(false);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithNoDescription_NoExceptionThrow() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romin45@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setResultObtained(false);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithNoNbPersMin_ThrowException() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romai5@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setResultObtained(false);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertThrows(ConstraintViolationException.class, () -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithNoNbPersMax_ThrowException() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("roin45@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setResultObtained(false);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertThrows(ConstraintViolationException.class, () -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithNoTag_NoExceptionThrow() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romai@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setResultObtained(false);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithNoSurvey_ThrowException() {
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("main45@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        assertThrows(DataIntegrityViolationException.class, () -> {
            surveyItemDAO.save(surveyItem);
        });
    }
}