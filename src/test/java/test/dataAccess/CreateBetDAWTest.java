package test.dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.ObjectAlreadyExistException;

public class CreateBetDAWTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();
	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	public void test1() throws ObjectAlreadyExistException {

		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}

		Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
		Question q11;
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1);

		sut.createForecast("Atletico", 1.2f, q11);
		sut.createForecast("Athletic", 2.2f, q11);

		User usr = new User("asier", "contreras", "11", new Date(2000, 5, 1), "11", false);
		usr.setWallet(200f);
		
		

	}

}
