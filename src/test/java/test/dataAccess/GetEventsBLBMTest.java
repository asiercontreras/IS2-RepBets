package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.persistence.PersistenceException;

import org.junit.*;
import org.mockito.*;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import domain.*;
import dataAccess.DataAccess;

public class GetEventsBLBMTest {
	
	
	@InjectMocks
	BLFacadeImplementation sut;
	
	@Mock
	DataAccess mockitoDA;
	
	int month;
	int year;
	
	Event evTest1;
	Event evTest2;
	
	@Before
	public void setUp() {
		// Creamos mock del DataAccess
		mockitoDA = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(mockitoDA);
		
		Calendar today = Calendar.getInstance();
		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year++;
		}
	}
	
	@Test //Test sin eventos en esa fecha
	public void test1() {
		Vector<Event> events = new Vector<>();
		@SuppressWarnings("deprecation")
		Date date = new Date(120, 5, 1);
		
		Mockito.doReturn(events).when(mockitoDA).getEvents(date);
		
		try {
			Vector<Event> returnedEvents = sut.getEvents(date);
			assertEquals(returnedEvents.isEmpty(), true);
		} catch (IllegalArgumentException e) { //Cambiar exception
			fail("Exception con createQuery() o setParameter()");
		} catch(IllegalStateException | PersistenceException e) {
			fail("Exception con getResultList()");
		} 
	}
	
	@Test //Test con varios eventos en esa fecha
	public void test2() {
			
		Vector<Event> events = new Vector<>();
		evTest1 = new Event("Pregunta Evento 1", UtilDate.newDate(2000, 1, 17));
		evTest2 = new Event("Pregunta Evento 2", UtilDate.newDate(2000, 1, 17));
		
		@SuppressWarnings("deprecation")
		Date date = new Date(2000, 1, 17);
		
		events.add(evTest1);
		events.add(evTest2);
		
		Mockito.doReturn(events).when(mockitoDA).getEvents(date);
		try {
			Vector<Event> returnedEvents = sut.getEvents(date);
			Event returnedEvent1 = returnedEvents.get(0);
			Event returnedEvent2 = returnedEvents.get(1);
			assertEquals(evTest1, returnedEvent1);
			assertEquals(evTest1, returnedEvent2);
		} catch (IllegalArgumentException e) { //Cambiar exception
			fail("Exception con createQuery() o setParameter()");
		} catch(IllegalStateException | PersistenceException e) {
			fail("Exception con getResultList()");
		}
	}
}
