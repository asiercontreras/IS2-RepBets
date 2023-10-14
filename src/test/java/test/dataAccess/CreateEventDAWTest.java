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
	private Event ev1;

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
	// Test que se crea un evento el cual ya estaba creado de antes
	public void test0() throws ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

		descripcion = "Gana equipo A";
		try {
			// Creamos dos eventos iguales, el segundo deberia de fallar
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month + 1, 12));
			ev1 = sut.createEvent(descripcion, UtilDate.newDate(year, month + 1, 12));

		} catch (ObjectAlreadyExistException e) {
			fail("El metodo createEvent NO deberia de permitir un evento el cual no se ha creado todavia.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}

	@Test
	// Test que se crea un evento nuevo
	public void test1() throws ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();
		descripcion = "Hay evento creado?";
		Event ev1= new Event(descripcion, UtilDate.newDate(year, month + 1, 12));
		try {
			ev = sut.createEvent(descripcion, UtilDate.newDate(year, month + 1, 12));
			//Cogemos el event number y se lo colocamos al evento creado por nosotros para hacer el equals
			ev1.setEventNumber(ev.getEventNumber());
			// Llama al método createEvent y verifica si funciona correctamente.F
			assertEquals(ev1,ev);
		} catch (ObjectAlreadyExistException e) {
			fail("El metodo createEvent deberia de permitir un evento el cual no se ha creado todavia.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			testDA.close();
			System.out.println("Finally " + b );
		}
	}

}
