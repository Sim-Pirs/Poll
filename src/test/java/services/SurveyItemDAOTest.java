package services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sondage.Starter;
import sondage.entity.model.Pollster;
import sondage.entity.model.Respondent;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.services.IPollsterDAO;
import sondage.entity.services.ISurveyDAO;
import sondage.entity.services.ISurveyItemDAO;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class SurveyItemDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    ISurveyItemDAO surveyItemDAO;

    private static Pollster pollster;

    @BeforeAll
    public static void init() {
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain45@gmail.com");
        pollster.setPassword("coucoucou");

        Respondent respondent = new Respondent();
        respondent.setEmail("roain@gmail.com");
        respondent.setTags(new HashSet<>());
    }

    @BeforeEach
    public void add() {
        pollsterDAO.save(pollster);
    }


    @Test
    public void testSave_AddGoodSurveyItem_NoExceptionThrow() {
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

        surveyDAO.save(survey);

        survey.addItem(surveyItem);

        assertDoesNotThrow(() -> {
           surveyItemDAO.save(surveyItem);
        });
    }

    @Test
    public void testSave_WithNoDescription_NoExceptionThrow() {
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        surveyDAO.save(survey);

        survey.addItem(surveyItem);

        assertDoesNotThrow(() -> {
            surveyItemDAO.save(surveyItem);
        });
    }

    @Test
    public void testSave_WithNoNbPersMin_ThrowException() {
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        surveyDAO.save(survey);

        survey.addItem(surveyItem);

        assertThrows(DataIntegrityViolationException.class, () -> {
            surveyItemDAO.save(surveyItem);
        });
    }

    @Test
    public void testSave_WithNoNbPersMax_ThrowException() {
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setTags(new HashSet<>());

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        surveyDAO.save(survey);

        survey.addItem(surveyItem);

        assertThrows(DataIntegrityViolationException.class, () -> {
            surveyItemDAO.save(surveyItem);
        });
    }

    @Test
    public void testSave_WithNoTag_NoExceptionThrow() {
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        surveyDAO.save(survey);

        survey.addItem(surveyItem);

        assertDoesNotThrow(() -> {
            surveyItemDAO.save(surveyItem);
        });
    }

    @Test
    public void testSave_WithNoSurvey_ThrowException() {
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