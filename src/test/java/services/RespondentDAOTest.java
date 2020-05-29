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
    private static Survey survey1;
    private static Survey survey2;

    @BeforeAll
    public static void init(){
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("coucoucou");

        survey1 = new Survey();
        survey1.setName("Sondage");
        survey1.setDescription("Une description.");
        survey1.setEndDate(new Date());
        survey1.setPollster(pollster);

        survey2 = new Survey();
        survey2.setName("Sondage");
        survey2.setDescription("Une description.");
        survey2.setEndDate(new Date());
        survey2.setPollster(pollster);
    }

    @BeforeEach
    public void add(){
        pollsterDAO.save(pollster);
        surveyDAO.save(survey1);
        surveyDAO.save(survey2);
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
        respondent.setSurvey(survey1);
        respondent.setExpired(false);

        assertDoesNotThrow(() -> {
            respondentDAO.save(respondent);
        });
    }

    @Test
    public void testSave_WithoutEmail_ThrowException(){
        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setTags(tags);
        respondent.setSurvey(survey1);

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
        r1.setSurvey(survey1);
        r1.setExpired(false);
        Respondent r2 = new Respondent();
        r2.setEmail("Romain@gmail.com");
        r2.setTags(tags);
        r2.setSurvey(survey2);
        r2.setExpired(false);

        respondentDAO.save(r1);

        assertDoesNotThrow(() -> {
            respondentDAO.save(r2);
        });
    }
}
