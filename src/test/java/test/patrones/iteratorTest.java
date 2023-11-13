package test.patrones;

import domain.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import org.junit.Test;

import businessLogic.*;

public class iteratorTest {

	@Test
	public void test1() throws ParseException {

		BLFacade blf = new BLFacadeImplementation();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdf.parse("17/12/2023");
		ExtendedIterator<Event> list = blf.getEventsIterator(date);

		Event e;
		System.out.println("_____________________");
		System.out.println("RECORRIDO	HACIA	ATRÁS");
		list.goLast(); // Hacia atrás
		while (list.hasPrevious()) {
			e = list.previous();
			System.out.println(e.toString());
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("RECORRIDO	HACIA	ADELANTE");
		list.goFirst(); // Hacia adelante
		while (list.hasNext()) {
			e = list.next();
			System.out.println(e.toString());
		}

	}
}
