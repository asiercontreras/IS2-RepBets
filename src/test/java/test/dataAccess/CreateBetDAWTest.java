package test.dataAccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.NotEnoughMoneyException;
import exceptions.ObjectAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

public class CreateBetDAWTest {

	static DataAccess sut;

	// additional operations needed to execute the test
	static TestDataAccess testDA;
	private Event ev;

	@Before
	public void initialize() {

		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

	}

	@Test
	public void testCreateBetWithSufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		// suficiente dinero en la billetera.
		Integer frNum = 1111;
		String description = "Atleti";
		float winrate = 1.2f;
		Question q11;
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev11 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 190.0f;

		// String name, String surnames, String dni, Date birthdate, String passwd,
		// boolean isAdmin
		User user1 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(200f);
		Forecast forecast1 = sut.createForecast(description, winrate, q11);
		// sut.createForecast("Atletico", 1.2f, q11);
		// sut.createForecast("Athletic", 2.2f, q11);
		// Llama al método createBet y verifica si funciona correctamente.
		try {
			/*
			 * testDA.open(); User usr1 = testDA.createBetUser(user); Forecast fr =
			 * testDA.createBetForecast(forecast);
			 * 
			 * testDA.close();
			 */
			float newValue = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user1);
			testDA.removeEvent(ev11);
			testDA.close();

			System.out.println("Finally " + b);
		}
	}

	@Test // (expected = NotEnoughMoneyException.class)
	public void testCreateBetWithInsufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		
		Integer frNum = 11111;
		String description = "Atleti";
		float winrate = 1.2f;
		Question q111;
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev111 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q111 = ev111.addQuestion("¿Quién ganará el partido?", 1f);
		// q11 = new Question(1, "¿Quién ganará el partido?", 1f, ev11);

		float expectedWalletValue =190.0f;

		// String name, String surnames, String dni, Date birthdate, String passwd,
		// boolean isAdmin
		User user2 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user2.setWallet(5.0f);
		Forecast forecast2 = sut.createForecast(description, winrate, q111);
		try {
			
			float newValue = sut.createBet(20.0f, user2, forecast2);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user2);
			testDA.removeEvent(ev111);
			testDA.close();

			System.out.println("Finally " + b);
		}
	}
	
	@Test
	public void testCreateSecondBetWithSufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		// suficiente dinero en la billetera.
		Integer frNum = 1111;
		String description = "Atleti";
		float winrate = 1.2f;
		Question q11;
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev11 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 190.0f;

		// String name, String surnames, String dni, Date birthdate, String passwd,
		// boolean isAdmin
		User user1 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(200f);
		Forecast forecast1 = sut.createForecast(description, winrate, q11);
		// sut.createForecast("Atletico", 1.2f, q11);
		// sut.createForecast("Athletic", 2.2f, q11);
		// Llama al método createBet y verifica si funciona correctamente.
		try {
			/*
			 * testDA.open(); User usr1 = testDA.createBetUser(user); Forecast fr =
			 * testDA.createBetForecast(forecast);
			 * 
			 * testDA.close();
			 */
			float newValue = sut.createBet(5.0f, user1, forecast1);

			float newValue2 = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user1);
			testDA.removeEvent(ev11);
			testDA.close();

			System.out.println("Finally " + b);
		}
	}


}
