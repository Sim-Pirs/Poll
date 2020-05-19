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
import sondage.entity.services.IPollsterDAO;
import sondage.entity.services.IRespondentDAO;
import sondage.entity.services.ISurveyDAO;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class RespondentDAOTest {

    @Autowired
    IRespondentDAO respondentDAO;

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    private static Pollster pollster;
    private static Survey survey;

    @BeforeAll
    public static void init(){
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("coucoucou");

        survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
    }

    @BeforeEach
    public void add(){
        pollsterDAO.save(pollster);
        surveyDAO.save(survey);
    }

    @AfterEach
    public void clear(){
        respondentDAO.deleteAll();
    }

    @Test
    public void testSave_GoodRespondent_NoExceptionsThrow(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gmail.com");
        respondent.setTags(tags);
        respondent.setSurvey(survey);

        assertDoesNotThrow(() -> {
            respondentDAO.save(respondent);
        });
    }

    @Test
    public void testSave_GoodRespondent_WellAdded(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gmail.com");
        respondent.setTags(tags);
        respondent.setSurvey(survey);

        respondentDAO.save(respondent);

        Respondent r = respondentDAO.findById(respondent.getId());

        assertEquals(respondent.getId(), r.getId());
    }

    @Test
    public void testSave_WithoutEmail_ThrowException(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setTags(tags);
        respondent.setSurvey(survey);

        assertThrows(DataIntegrityViolationException.class, () ->{
            respondentDAO.save(respondent);
        });
    }

    @Test
    public void testSave_WithoutSurvey_ThrowException(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain@gmail.com");
        respondent.setTags(tags);

        assertThrows(DataIntegrityViolationException.class, () ->{
            respondentDAO.save(respondent);
        });
    }

    @Test
    public void testSave_WithEmailAlreadyUsed_NoExceptionThrow(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent r1 = new Respondent();
        r1.setEmail("Romain@gmail.com");
        r1.setTags(tags);
        r1.setSurvey(survey);
        Respondent r2 = new Respondent();
        r2.setEmail("Romain@gmail.com");
        r2.setTags(tags);
        r2.setSurvey(survey);

        respondentDAO.save(r1);

        assertDoesNotThrow(() -> {
            respondentDAO.save(r2);
        });
    }
}
