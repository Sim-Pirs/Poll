package web;

import org.junit.jupiter.api.Test;
import sondage.entity.model.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import javax.validation.constraints.*;

public class SurveyItemValidatorTest {
	
	/*Partie 1 test des regex*/
	/*Méthodes test regex*/
		
		public void descriptionRegex(String description, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Survey.class.getDeclaredField("description");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
		    assertEquals(description.matches(annotations[0].regexp()), validates);
		}
	
		/* probleme : j'arrive pas à recup la regex pour le moment */
		/*public void tagsRegex(Collection<String> tags,boolean validates) throws NoSuchFieldException, SecurityException{
		    Field field = Respondent.class.getDeclaredField("tags");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
		    System.out.println("annotations la "+ annotations);
		    Object[]tagsTable = tags.toArray();
		    for(int i=0; i<tagsTable.length; i++) {
		    	System.out.println("i= "+ i + " " + tagsTable[i]);
		    	assertEquals(((String) tagsTable[i]).matches(annotations[0].regexp()), validates);
		    }
		}*/
		
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
