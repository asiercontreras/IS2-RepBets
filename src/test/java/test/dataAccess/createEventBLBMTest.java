package test.dataAccess;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import dataAccess.DataAccess;
import domain.Event;
import domain.Forecast;
import domain.User;
import exceptions.EventFinished;
import exceptions.NotEnoughMoneyException;
import exceptions.ObjectAlreadyExistException;

import businessLogic.*;
import configuration.UtilDate;

public class createEventBLBMTest {

	char[] pass = { '1', '1' };

	@InjectMocks
	BLFacadeImplementation sut;

	@Mock
	DataAccess mockitoDA;

	private Event ev;
	int month;
	int year;
	Event ev1;
	String descripcion;

	@Before
	public void setUp() throws ObjectAlreadyExistException {
		mockitoDA = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(mockitoDA);

		Calendar today = Calendar.getInstance();

		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}

	}

	// Test cuando creas creas un evento y no esta creado
	@Test
	public void test1() throws ObjectAlreadyExistException, EventFinished {

		descripcion = "Hay evento creado?";

		ev = new Event(descripcion, UtilDate.newDate(year, month + 1, 12));

		// Llama al método createEvent y verifica si funciona correctamente.
		Mockito.doReturn(ev).when(mockitoDA).createEvent(descripcion, UtilDate.newDate(year, month + 1, 12));
		try {
			Event ev1 = sut.createEvent(descripcion, UtilDate.newDate(year, month + 1, 12));
			assertEquals(ev, ev1);
		} catch (ObjectAlreadyExistException e) {
			fail("Algo ha ido mal en la ejecución.");
		}
	}

	// Test cuando creas un evento pero esta creado
	@Test
	public void test2() throws ObjectAlreadyExistException, EventFinished {

		descripcion = "Hay evento creado1?";
		
		ev = new Event(descripcion, UtilDate.newDate(year, month + 1, 12));
		
		//No hace falta mockear nada porque se supone que la excepcion saltara antes
		assertThrows(ObjectAlreadyExistException.class,() -> {sut.createEvent(descripcion, UtilDate.newDate(year, month+1, 12));});

	}
	
	// Test cuando creas un evento que ya ha finalizado
	@Test
	public void test3() throws ObjectAlreadyExistException, EventFinished {

		descripcion = "Hay evento creado1?";
		
		ev = new Event(descripcion, UtilDate.newDate(year, month + 1, 12));
		
		//No hace falta mockear nada porque se supone que la excepcion saltara antes
		assertThrows(EventFinished.class,() -> {sut.createEvent(descripcion, UtilDate.newDate(year, month-1, 12));});

	}
}
