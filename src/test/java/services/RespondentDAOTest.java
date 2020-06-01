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
@DataJpaTest
public class RespondentDAOTest {

    @Autowired
    IRespondentDAO respondentDAO;

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;



    @Test
    public void testSave_GoodRespondent_NoExceptionsThrow(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain1@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setEmail("Romain1@gmail.com");
        respondent.setTags(tags);
        respondent.setExpired(false);

        Survey survey1 = new Survey();
        survey1.setName("Sondage");
        survey1.setDescription("Une description.");
        survey1.setEndDate(new Date());
        survey1.setPollster(pollster);
        survey1.addRespondent(respondent);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey1);
        });
    }

    @Test
    public void testSave_WithoutEmail_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain2@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent respondent = new Respondent();
        respondent.setTags(tags);

        Survey survey1 = new Survey();
        survey1.setName("Sondage");
        survey1.setDescription("Une description.");
        survey1.setEndDate(new Date());
        survey1.setPollster(pollster);
        survey1.addRespondent(respondent);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey1);
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
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain3@gmail.com");
        pollster.setPassword("coucoucou");
        pollsterDAO.save(pollster);

        HashSet<String> tags = new HashSet<>();
        tags.add("M1");
        tags.add("ILD");

        Respondent r1 = new Respondent();
        r1.setEmail("Romain2@gmail.com");
        r1.setTags(tags);
        r1.setExpired(false);

        Respondent r2 = new Respondent();
        r2.setEmail("Romain3@gmail.com");
        r2.setTags(tags);
        r2.setExpired(false);

        Survey survey1 = new Survey();
        survey1.setName("Sondage");
        survey1.setDescription("Une description.");
        survey1.setEndDate(new Date());
        survey1.setPollster(pollster);
        survey1.addRespondent(r1);

        Survey survey2 = new Survey();
        survey2.setName("Sondage");
        survey2.setDescription("Une description.");
        survey2.setEndDate(new Date());
        survey2.setPollster(pollster);
        survey2.addRespondent(r2);

        surveyDAO.save(survey1);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey2);
        });
    }
}
