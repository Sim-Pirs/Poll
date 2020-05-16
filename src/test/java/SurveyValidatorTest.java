import org.junit.jupiter.api.Test;
import sondage.entity.model.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import javax.validation.constraints.*;

public class SurveyValidatorTest {
	
	/*Partie 1 test des regex*/
	
		public void nameRegex(String name,boolean validates) throws NoSuchFieldException, SecurityException{
		    Field field = Survey.class.getDeclaredField("name");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);    
		    assertEquals(name.matches(annotations[0].regexp()), validates);
		}
		
		public void descriptionRegex(String description, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Survey.class.getDeclaredField("description");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
		    assertEquals(description.matches(annotations[0].regexp()), validates);
		}
		
		/*Tests Name*/
		@Test
		public void testSurveyBadName()  throws NoSuchFieldException, SecurityException {
			Survey survey = new Survey();
			survey.setName(";Projet???Master"); //ici l'erreur est l'espace en début de titre
			nameRegex(survey.getName(), false);
		}
		
		@Test
		public void testSurveyGoodName()  throws NoSuchFieldException, SecurityException {
			Survey survey = new Survey();
			survey.setName("Projet Master 1");
			nameRegex(survey.getName(), true);
		}
		/*******************************************************************************************************/
		
		/*Test Description*/
		@Test
		public void testSurveyBadDescription() throws NoSuchFieldException, SecurityException{
			Survey survey = new Survey();
			survey.setDescription(";;;;;;;;;;;;");
			descriptionRegex(survey.getDescription(), false);
		}
		
		@Test
		public void testSurveyGoodDescription() throws NoSuchFieldException, SecurityException{
			Survey survey = new Survey();
			survey.setDescription("Ceci est une description.");
			descriptionRegex(survey.getDescription(), true);
		}
	/*******************************************************************************************************/
	/*Partie 2 sur le SurveyValidator*/

}
