package web;

import org.junit.jupiter.api.Test;
import sondage.entity.model.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import javax.validation.constraints.*;

public class SurveyItemValidatorTest {
	
	/*Test des regex*/
	/*Méthodes test regex*/
		
		public void descriptionRegex(String description, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Survey.class.getDeclaredField("description");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
		    assertEquals(description.matches(annotations[0].regexp()), validates);
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

}
