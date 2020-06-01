package services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sondage.Starter;
import sondage.entity.model.Pollster;
import sondage.entity.model.Respondent;
import sondage.entity.model.Survey;
import sondage.entity.model.SurveyItem;
import sondage.entity.services.IPollsterDAO;
import sondage.entity.services.ISurveyDAO;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
@DataJpaTest
public class SurveyDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;



    @Test
    public void testSave_PollsterNotInBase_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);


        assertThrows(InvalidDataAccessApiUsageException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutName_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain334@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Survey survey = new Survey();
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutDescription_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain3341@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutEndDate_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain3342@gmail.com");
        pollster.setPassword("coucoucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setPollster(pollster);
        survey.addItem(surveyItem);

        pollsterDAO.save(pollster);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutPollster_ThrowException(){
        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.addItem(surveyItem);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutItems_NoExceptionThrow(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain3343@gmail.com");
        pollster.setPassword("coucoucou");

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        pollsterDAO.save(pollster);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithRespondentNotInBase_NoThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain3344@gmail.com");
        pollster.setPassword("coucoucou");

        Respondent r = new Respondent();
        r.setEmail("roain@gmail.com");
        r.setTags(new HashSet<>());
        r.setExpired(false);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addRespondent(r);

        pollsterDAO.save(pollster);

        assertDoesNotThrow(() ->{
            surveyDAO.save(survey);
        });
    }
}
