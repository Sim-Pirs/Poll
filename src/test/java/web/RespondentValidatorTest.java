package web;

import org.junit.jupiter.api.Test;
import sondage.entity.model.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.*;

public class RespondentValidatorTest {
	
	/*Partie 1 test des regex*/
		/*Méthodes test regex*/
	
		public void emailRegex(String email,boolean validates) throws NoSuchFieldException, SecurityException{
		    Field field = Respondent.class.getDeclaredField("email");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);    
		    assertEquals(email.matches(annotations[0].regexp()), validates);
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
		
		public void tokenRegex(String token,boolean validates) throws NoSuchFieldException, SecurityException{
		    Field field = Respondent.class.getDeclaredField("token");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);    
		    assertEquals(token.matches(annotations[0].regexp()), validates);
		}
		
		/*******************************************************************************************************/
		/*Tests email*/
		@Test
		public void testRespondentBadEmail() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			respondent.setEmail("nomis@truc@truc.com");
			emailRegex(respondent.getEmail(), false);
		}
		
		@Test
		public void testRespondentGoodEmail() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			respondent.setEmail("simon@hotmail.fr");
			emailRegex(respondent.getEmail(), true);
		}
		/*******************************************************************************************************/
		/*Tests tag*/
		/*@Test
		public void testRespondentBadTag() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			Collection<String> collection = new ArrayList<String>();
			collection.add("ILD-11;");
			collection.add("IAAA--");
			respondent.setTags(collection);
			tagsRegex(respondent.getTags(), false);
		}
		
		@Test
		public void testRespondentGoodTag() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			Collection<String> collection = new ArrayList<String>();
			collection.add("ILD-1;");
			collection.add("IAAA-5");
			respondent.setTags(collection);
			tagsRegex(respondent.getTags(), true);
		}*/
		/*******************************************************************************************************/
		/*Tests token*/
		@Test
		public void testRespondentBadToken() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			respondent.setToken("i05?");
			tokenRegex(respondent.getToken(), false);
		}
		
		@Test
		public void testRespondentGoodToken() throws NoSuchFieldException, SecurityException{
			Respondent respondent = new Respondent();
			respondent.setToken("ILD1");
			tokenRegex(respondent.getToken(), true);
		}

}
