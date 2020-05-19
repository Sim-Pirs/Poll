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
import sondage.entity.model.*;
import sondage.entity.services.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class ChoiceDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    IRespondentDAO respondentDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    ISurveyItemDAO surveyItemDAO;

    @Autowired
    IChoiceDAO choiceDAO;

    private static Pollster pollster;
    private static Respondent respondent;
    private static Survey survey;
    private static SurveyItem surveyItem;

    @BeforeAll
    public static void init(){
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain12@gmail.com");
        pollster.setPassword("coucoucou");

        surveyItem = new SurveyItem();
        surveyItem.setDescription("Une description.");
        surveyItem.setTags(new HashSet<>());
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);

        survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.addItem(surveyItem);

        respondent = new Respondent();
        respondent.setEmail("Romain@gg.com");
        respondent.addTag("M1");
        respondent.addTag("ILD");
        respondent.setSurvey(survey);
    }

    @BeforeEach
    public void add(){
        pollsterDAO.save(pollster);
        surveyDAO.save(survey);
        respondentDAO.save(respondent);
    }

    @AfterEach
    public void clear(){
        choiceDAO.deleteAll();
    }


    @Test
    public void testSave_GoodChoice_NoExceptionThrow(){
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
        Choice choice = new Choice();
        choice.setScore(1);
        choice.setItem(surveyItem);

        assertThrows(DataIntegrityViolationException.class, () -> {
            choiceDAO.save(choice);
        });
    }

    @Test
    public void testSave_WithoutItem_ThrowException(){
        Choice choice = new Choice();
        choice.setScore(1);
        choice.setRespondent(respondent);

        assertThrows(DataIntegrityViolationException.class, () -> {
            choiceDAO.save(choice);
        });
    }
}
