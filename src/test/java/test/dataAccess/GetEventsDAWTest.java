package test.dataAccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.*;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.transaction.TransactionRequiredException;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.NotEnoughMoneyException;
import exceptions.ObjectAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

public class GetEventsDAWTest {

	static DataAccess sut;
	static TestDataAccess testDA;
	
	int month;
	int year;
	
	Event evTest1;
	Event evTest2;

	@Before
	public void initialize() throws ObjectAlreadyExistException {
		
		Calendar today = Calendar.getInstance();
		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year++;
		}
	}
	
	
	@Test
	public void testGetEventsWithNoEvents() {
		sut = new DataAccess();
		testDA = new TestDataAccess();
		
		Date date = testDA.getRandomDateWithNoEvents();
		try {
			Vector<Event> events = sut.getEvents(date);
			assertEquals(events.isEmpty(), true);
		} catch (IllegalArgumentException e) { //Cambiar exception
			fail("Exception con createQuery() o setParameter()");
		} catch(IllegalStateException | PersistenceException e) {
			fail("Exception con getResultList()");
		} 
	}
	
	@Test
	public void testGetEventsWithOneEvent() {
		sut = new DataAccess();
		testDA = new TestDataAccess();
		
		try {
			evTest1 = sut.createEvent("Pregunta Evento 1", UtilDate.newDate(year, month, 17));
			
			@SuppressWarnings("deprecation")
			Date date = new Date(123, month, 17);
			Vector<Event> events = sut.getEvents(date);
			Event returnedEvent1 = events.get(0);
			assertEquals(evTest1, returnedEvent1);
		} catch (ObjectAlreadyExistException e) {
			fail("Error creando el evento.");
		} catch (IllegalArgumentException e) { //Cambiar exception
			fail("Exception con createQuery() o setParameter()");
		} catch(IllegalStateException | PersistenceException e) {
			fail("Exception con getResultList()");
		} finally {
			testDA.open();
			testDA.removeEvent(evTest1);
			testDA.close();
		}
	}
	
	@Test
	public void testGetEventsWithMultipleEvents() {
		sut = new DataAccess();
		testDA = new TestDataAccess();
		
		try {
			evTest1 = sut.createEvent("Pregunta Evento 1", UtilDate.newDate(year, month, 17));
			evTest2 = sut.createEvent("Pregunta Evento 2", UtilDate.newDate(year, month, 17));
			
			@SuppressWarnings("deprecation")
			Date date = new Date(123, month, 17);
			Vector<Event> events = sut.getEvents(date);
			Event returnedEvent1 = events.get(0);
			Event returnedEvent2 = events.get(1);
			assertEquals(evTest1, returnedEvent1);
			assertEquals(evTest1, returnedEvent2);
		} catch (ObjectAlreadyExistException e) {
			fail("Error creando el evento.");
		} catch (IllegalArgumentException e) { //Cambiar exception
			fail("Exception con createQuery() o setParameter()");
		} catch(IllegalStateException | PersistenceException e) {
			fail("Exception con getResultList()");
		} finally {
			testDA.open();
			testDA.removeEvent(evTest1);
			testDA.removeEvent(evTest2);
			testDA.close();
		}
	}
	
}
