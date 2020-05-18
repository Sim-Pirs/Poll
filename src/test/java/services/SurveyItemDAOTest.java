package services;

import org.junit.jupiter.api.AfterEach;
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

import javax.validation.ConstraintViolationException;
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
    public void testSave_AddSurvey_NoExceptionThrow() {
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
        survey.addItem(surveyItem);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_AddSurvey_WellAdded() {
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
        survey.addItem(surveyItem);

        surveyDAO.save(survey);

        SurveyItem item = surveyItemDAO.findById(surveyItem.getId());

        assertEquals(surveyItem.getId(), item.getId());
    }

    @Test
    public void testSave_WithSurveyAlreadyAdded_WellAdded() {
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        surveyDAO.save(survey);


        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>());

        survey.addItem(surveyItem);

        surveyItemDAO.save(surveyItem);
        SurveyItem item = surveyItemDAO.findById(surveyItem.getId());

        assertEquals(surveyItem.getId(), item.getId());
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