
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sondage.Starter;
import sondage.entity.model.*;
import sondage.entity.services.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class PollsterDAOTest {

    @Autowired
    IPollsterDAO pollsterDAO;


    @Test
    public void testSave_WithoutFirstName_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("coucoucou");

        assertThrows(DataIntegrityViolationException.class, () ->{
            pollsterDAO.save(pollster);
        });
    }

    @Test
    public void testSave_WithoutLastName_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("coucoucou");

        assertThrows(DataIntegrityViolationException.class, () ->{
            pollsterDAO.save(pollster);
        });
    }

    @Test
    public void testSave_WithoutEmail_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setPassword("coucoucou");

        assertThrows(DataIntegrityViolationException.class, () ->{
            pollsterDAO.save(pollster);
        });
    }

    @Test
    public void testSave_WithoutPassword_ThrowException(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");

        assertThrows(DataIntegrityViolationException.class, () ->{
            pollsterDAO.save(pollster);
        });
    }

    @Test
    public void testSave_WithoutSurveys_NoExceptionsThrow(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain897@gmail.com");
        pollster.setPassword("coucoucou");

        assertDoesNotThrow(() -> {
            pollsterDAO.save(pollster);
        });
    }

    @Test
    public void testSave_WithoutSurveys_WellAdded(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain76@gmail.com");
        pollster.setPassword("coucoucou");

        pollsterDAO.save(pollster);

        Pollster p = pollsterDAO.findById(pollster.getId());

        assertEquals(pollster.getId(), p.getId());
    }

    /*
    @Test
    public void testSave_GoodPollster(){
        Pollster pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("coucou");

        HashSet<Survey> surveys = new HashSet<>();
        Survey survey = new Survey();
        survey.setName("Sondage");
        survey.setDescription("Une description.");
        survey.setEndDate(new Date());
        survey.setPollster(pollster);
        surveys.add(survey);

        pollster.setSurveys(surveys);

        pollsterDAO.save(pollster);

        Pollster p = pollsterDAO.findById(pollster.getId());
        p.getSurveys().size();

        assertEquals(pollster.getId(), p.getId());
    }
     */
}
