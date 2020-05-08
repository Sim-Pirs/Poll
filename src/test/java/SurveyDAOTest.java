
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SurveyDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    private static Pollster pollster;
    private static HashSet<SurveyItem> surveyItems;
    private static HashSet<Respondent> respondents;

    @BeforeAll
    public static void init(){
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain334@gmail.com");
        pollster.setPassword("coucou");

        SurveyItem surveyItem = new SurveyItem();
        surveyItem.setDescription("Description.");
        surveyItem.setNbPersMin(2);
        surveyItem.setNbPersMax(5);
        surveyItem.setTags(new HashSet<>(){});

        Respondent respondent = new Respondent();
        respondent.setEmail("roain@gmail.com");
        respondent.setTags(new HashSet<>());

        surveyItems = new HashSet<>();
        surveyItems.add(surveyItem);

        respondents = new HashSet<>();
        respondents.add(respondent);
    }

    @BeforeEach
    public void add(){
        pollsterDAO.save(pollster);
    }


    @Test
    public void testSave_PollsterNotInBase_ThrowException(){
        Pollster p = new Pollster();
        p.setFirstName("Romain");
        p.setLastName("Colonna");
        p.setEmail("romain@gmail.com");
        p.setPassword("coucou");

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(p);
        survey.setItems(surveyItems);

        assertThrows(InvalidDataAccessApiUsageException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutName_ThrowException(){
        Survey survey = new Survey();
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setItems(surveyItems);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutDescription_ThrowException(){
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setItems(surveyItems);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutEndDate_ThrowException(){
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setPollster(pollster);
        survey.setItems(surveyItems);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutPollster_ThrowException(){
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setItems(surveyItems);

        assertThrows(DataIntegrityViolationException.class, () ->{
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithoutItems_NoExceptionThrow(){
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);

        assertDoesNotThrow(() -> {
            surveyDAO.save(survey);
        });
    }

    @Test
    public void testSave_WithRespondentNotInBase_ThrowException(){
        Respondent r = new Respondent();
        r.setEmail("roain@gmail.com");
        r.setTags(new HashSet<>());
        HashSet<Respondent> rs = new HashSet<>();
        rs.add(r);

        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        survey.setRespondents(rs);

        assertThrows(InvalidDataAccessApiUsageException.class, () ->{
            surveyDAO.save(survey);
        });
    }
}
