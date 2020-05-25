import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;
import sondage.Starter;
import sondage.entity.model.Pollster;
import sondage.entity.services.IPollsterDAO;
import sondage.entity.web.controller.IndexController;
import sondage.entity.web.controller.PollsterController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class PollsterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    private PollsterController pollsterController;

    private Pollster pollster;

    @BeforeEach
    public void init(){
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("Colonna");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("1Aaaaaaa.");

        pollsterDAO.save(pollster);
    }

    @AfterEach
    public void clean(){
        pollsterDAO.deleteAll();
    }

    @Test
    public void connect_WithUSerInBase_MustConnect(){
        ModelAndView mv = pollsterController.login(pollster.getEmail(), pollster.getPassword());

        System.err.println(mv);
        //assertEquals()
    }
}
