package test.dataAccess;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.ObjectAlreadyExistException;

public class CreateEventDABTest {

	static DataAccess sut;
	static TestDataAccess testDA;
	private Event ev;
	int month;
	int year;
	Event ev1;
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

	// Crear un evento el cual ya esta creado anteriormente. Para ello se elevará la
	// excepcion ObjectAlreadyExistException
	@Test
	public void test1() throws ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Hay evento creado?";

		try {
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));
			
			ev1 = sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));
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

	// Crear un evento donde no tenga fecha -> Date=null
	@Test
	public void test2() throws ObjectAlreadyExistException{
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Hay evento creado?";
		
		try {
			// Llama al método createEvent y verifica si funciona correctamente.
			ev = sut.createEvent(descripcion, null);
			assertNotNull(ev.getEventDate());
		} catch (ObjectAlreadyExistException e) {
			fail("El metodo createEvent deberia de permitir un evento el cual no se ha creado todavia y una fecha.");
		}
		finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
	
	
	// Crear un evento nuevo que no existe
	@Test
	public void test3() throws ObjectAlreadyExistException, ParseException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Hay evento creado?";
		String descripcion1 = "Es nuevo evento?";
		
		try {
			
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));

			// Llama al método createEvent y verifica si funciona correctamente.
			ev1 = sut.createEvent(descripcion1, UtilDate.newDate(year, month+2, 12));

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
