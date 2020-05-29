package web;

import org.junit.jupiter.api.Test;
import sondage.entity.model.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import javax.validation.constraints.*;

public class PollsterValidatorTest {
	
	/*Tests des regex*/
	
		/*Méthodes de test*/
		public void firstNameRegex(String firstName,boolean validates) throws NoSuchFieldException, SecurityException{
		    Field field = Pollster.class.getDeclaredField("firstName");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);    
		    assertEquals(firstName.matches(annotations[0].regexp()), validates);
		}
		
		public void lastNameRegex(String lastName, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Pollster.class.getDeclaredField("lastName");
		    Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
		    assertEquals(lastName.matches(annotations[0].regexp()), validates);
		}
		
		public void emailRegex(String email, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Pollster.class.getDeclaredField("email");
			Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
			assertEquals(email.matches(annotations[0].regexp()), validates);
		}
		
		public void passwordRegex(String password, boolean validates) throws NoSuchFieldException, SecurityException{
			Field field = Pollster.class.getDeclaredField("password");
			Pattern[] annotations = field.getAnnotationsByType(Pattern.class);
			assertEquals(password.matches(annotations[0].regexp()), validates);
		}
		
		
		/*Tests FirstName*/
		@Test
		public void testPollsterBadFirstName() throws NoSuchFieldException, SecurityException {
			Pollster pollster = new Pollster();
			pollster.setFirstName("51m0n");
			firstNameRegex(pollster.getFirstName(), false);
		}
		
		@Test
		public void testPollsterGoodFirstName() throws NoSuchFieldException, SecurityException {
			Pollster pollster = new Pollster();
			pollster.setFirstName("Simon");
			firstNameRegex(pollster.getFirstName(), true);
		}
		/*******************************************************************************************************/
		/*Tests LastName*/
		@Test
		public void testPollsterBadLastName() throws NoSuchFieldException, SecurityException {
			Pollster pollster = new Pollster();
			pollster.setLastName("P13R35");
			lastNameRegex(pollster.getLastName(), false);
		}
		
		@Test
		public void testPollsterGoodLastName() throws NoSuchFieldException, SecurityException {
			Pollster pollster = new Pollster();
			pollster.setLastName("PIERES");
			lastNameRegex(pollster.getLastName(), true);
		}
		/*******************************************************************************************************/
		/*Tests Email*/
		@Test
		public void testPollsterBadEmail() throws NoSuchFieldException, SecurityException{
			Pollster pollster = new Pollster();
			pollster.setEmail("simon.com");
			emailRegex(pollster.getEmail(), false);
		}
		
		public void testPollsterGoodEmail() throws NoSuchFieldException, SecurityException{
			Pollster pollster = new Pollster();
			pollster.setEmail("simon@gmail.com");
			emailRegex(pollster.getEmail(), true);
		}
		
		/*******************************************************************************************************/
		/*Tests Password*/
	/*	@Test
		public void testPollsterBadPasswordLess8Characters() throws NoSuchFieldException, SecurityException{
			Pollster pollster = new Pollster();
			pollster.setPassword("1234");
			passwordRegex(pollster.getPassword(), false);
		}
		
		@Test
		public void testPollsterBadPasswordMore15Characters() throws NoSuchFieldException, SecurityException{
			Pollster pollster = new Pollster();
			pollster.setPassword("AZERTYUIOP18903748"); //à changer en fonction du nombre max de caractères autorisés.
			passwordRegex(pollster.getPassword(), false);
		}
		
		public void testPollsterGoodPassword() throws NoSuchFieldException, SecurityException{
			Pollster pollster = new Pollster();
			pollster.setPassword("m4st3rILDgog+");
			passwordRegex(pollster.getPassword(), true);
		}*/
}
