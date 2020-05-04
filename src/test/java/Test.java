import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sondage.Starter;
import sondage.model.*;
import sondage.services.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Starter.class)
public class Test {

    @Autowired
    IAnswerDAO answerDAO;

    @Autowired
    IChoiceDAO choiceDAO;

    @Autowired
    IPollsterDAO pollsterDAO;

    @Autowired
    IRespondentDAO respondentDAO;

    @Autowired
    ISurveyDAO surveyDAO;

    @Autowired
    ITagDAO tagDAO;

    static Answer answer1;
    static Answer answer2;
    static Answer answer3;
    static Choice choice;
    static Pollster pollster;
    static Respondent respondent;
    static Survey survey;
    static Tag tag1;
    static Tag tag2;
    static Tag tag3;

    @BeforeAll
    public static void init(){
        //on crée un sondeur
        pollster = new Pollster();
        pollster.setFirstName("Romain");
        pollster.setLastName("COLONNA");
        pollster.setEmail("romain@gmail.com");
        pollster.setPassword("pass");

        //on crée un sondage
        survey = new Survey();
        survey.setDescription("Une description.");

        //On crée des réponse a se sondage
        answer1 = new Answer();
        answer1.setDescription("Une description1.");
        answer2 = new Answer();
        answer2.setDescription("Une description2.");
        answer3 = new Answer();
        answer3.setDescription("Une description3.");

        //on ajoute les réponse au sondage
        survey.addPossibleAnswer(answer1);
        survey.addPossibleAnswer(answer2);
        survey.addPossibleAnswer(answer3);

        //on assigne le sondage au sondeur
        pollster.addSurvey(survey);



        //on crée un sondé
        respondent = new Respondent();
        respondent.setEmail("roro@gmail.com");

        //on crée des tags
        tag1 = new Tag();
        tag1.setName("ILD");
        tag1.setDescription("Description1.");
        tag2 = new Tag();
        tag2.setName("GIG");
        tag2.setDescription("Description2.");
        tag3 = new Tag();
        tag3.setName("FSI");
        tag3.setDescription("Description3.");

        //on assigne les tags aux sondé et aux sondages
        survey.addTag(tag1);
        survey.addTag(tag2);
        survey.addTag(tag3);
        respondent.addTag(tag2);

        //on simule la réponse de l'utilisateur
        choice = new Choice();
        choice.setSurvey(survey);
        choice.setRespondent(respondent);
        choice.addAnswer(answer1);
        choice.addAnswer(answer2);
        choice.addAnswer(answer3);
        choice
    }
}
