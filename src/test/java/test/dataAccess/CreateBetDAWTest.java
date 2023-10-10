package test.dataAccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.*;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.NotEnoughMoneyException;
import exceptions.ObjectAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

public class CreateBetDAWTest {

	static DataAccess sut;
	static TestDataAccess testDA;
	private Event ev;
	int frNum;
	String description;
	float winrate;
	Question q11;
	int month;
	int year;
	Event ev11;

	@Before
	public void initialize() throws ObjectAlreadyExistException {
		frNum = 1111;
		description = "Atleti";
		winrate = 1.2f;
		Question q11;
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
	public void testCreateBetWithSufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		ev11 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 190.0f;
		User user1 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(200f);
		Forecast forecast1 = sut.createForecast(description, winrate, q11);
		// Llama al método createBet y verifica si funciona correctamente.
		try {
			float newValue = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user1);
			testDA.removeForecast(forecast1);
			testDA.removeEvent(ev11);
			testDA.close();

			System.out.println("Finally " + b);
		}
	}

	@Test // (expected = NotEnoughMoneyException.class)
	public void testCreateBetWithInsufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		ev11 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 190.0f;
		User user1 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(5.0f);
		Forecast forecast1 = sut.createForecast(description, winrate, q11);
		// Llama al método createBet y verifica si funciona correctamente.
		try {
			float newValue = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user1);
			testDA.removeForecast(forecast1);
			testDA.removeEvent(ev11);
			testDA.close();

			System.out.println("Finally " + b);
		}

	}

	@Test
	public void testCreateSecondBetWithSufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {

		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		ev11 = sut.createEvent("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 190.0f;
		User user1 = sut.createUser("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(200f);
		Forecast forecast1 = sut.createForecast(description, winrate, q11);
		try {
			float newValue = sut.createBet(5.0f, user1, forecast1);
			float newValue2 = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue2, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeUserDado(user1);
			testDA.removeForecast(forecast1);
			testDA.removeEvent(ev11);
			testDA.close();

			System.out.println("Finally " + b);
		}
	}

}
