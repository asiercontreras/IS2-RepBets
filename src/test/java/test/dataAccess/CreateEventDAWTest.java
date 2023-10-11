package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.ObjectAlreadyExistException;

public class CreateEventDAWTest {

	static DataAccess sut;
	static TestDataAccess testDA;
	private Event ev;
	int month;
	int year;
	String descripcion;

	@Before
	public void initialize() throws ObjectAlreadyExistException {
		Calendar today = Calendar.getInstance();

		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}

	}

	@Test
	public void testCreateEventWithEventCreatedYet() throws ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Gana equipo A";
		try {
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));
			assertTrue(ev!=null);
			
			testDA.open();
			boolean exist = testDA.existEvent(ev);
			assertTrue(exist);
			testDA.close();
		}
		catch (ObjectAlreadyExistException e) {
			fail("El metodo createEvent deberia de permitir un evento el cual no se ha creado todavia.");
		}
		finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
	
	
	public void testCreateEventWithNotEventCreatedYet() throws ObjectAlreadyExistException{
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Hay evento creado?";
		String descripcion1 = "Es nuevo evento?";
		
		Event ev1 = null;
		
		try {
			
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));

			// Llama al método createEvent y verifica si funciona correctamente.
			ev1 = sut.createEvent(descripcion1, UtilDate.newDate(year, month+2, 12));
			assertNotNull(ev1);
		} catch (ObjectAlreadyExistException e) {
			fail("El metodo createEvent deberia de permitir un evento el cual no se ha creado todavia.");
		}
		finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			boolean b1 = testDA.removeEvent(ev1);
			testDA.close();
			System.out.println("Finally " + b + " y " + b1);
		}
	}

}
