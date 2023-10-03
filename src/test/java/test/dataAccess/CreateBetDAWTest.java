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
		// Configura aquí tu entorno de prueba, como inicializar una base de datos de
		// prueba o crear objetos necesarios.
		// Puedes utilizar bibliotecas como H2 para crear una base de datos de prueba en
		// memoria.
		// Asegúrate de crear una instancia de DataAccess para realizar pruebas en el
		// método createBet.
		// configure the state of the system (create object in the dabatase)
		// sut:system under test
		sut = new DataAccess();
		// additional operations needed to execute the test
		testDA = new TestDataAccess();

//		testDA.open();
//		ev = testDA.addEventWithQuestion(eventText, oneDate, queryText, betMinimum);
//		testDA.close();
	}

//	@Test
//	public void test1() {
//
//		fail("Not yet implemented");
//	}

	@Test
	public void testCreateBetWithSufficientFunds() throws NotEnoughMoneyException, ObjectAlreadyExistException {
		// Configura el escenario de prueba con un usuario y una previsión que tenga
		// suficiente dinero en la billetera.
		User user = new User("asier", "contreras", "23", new Date(2000, 5, 1), "11", false);
		user.setWallet(200f);

		Integer frNum = 1;
		String description = "";
		float winrate = 1.2f;
		Question question;
		float minimumBet = 1.0f;
		Question q11;
		Event ev11= null;
		q11 = new Question(1, "¿Quién ganará el partido?", 1f, ev11);
		Forecast forecast = new Forecast(frNum, description, winrate, q11);
		float expectedWalletValue = 10.0f;
		
		//String name, String surnames, String dni, Date birthdate, String passwd, boolean isAdmin
		//sut.createUser("asier", "contreras", "11", new Date(2000, 5, 1), "11", false);
		//sut.createForecast( description, winrate, q11);
		// Llama al método createBet y verifica si funciona correctamente.
		try {
			testDA.open();
			User usr1 = testDA.createBetUser(user);
			Forecast fr = testDA.createBetForecast(forecast);

			testDA.close();
			float newValue = sut.createBet(10.0f, usr1, fr);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01); // Ajusta el valor esperado según tu lógica.
		} catch (NotEnoughMoneyException e) {
			fail("El método createBet debería permitir una apuesta válida con suficiente dinero.");
		} finally {
			  //Remove the created objects in the database (cascade removing)   
			testDA.open();
	         boolean b=testDA.removeUser(usr1);	         
	        		 testDA.close();
	         System.out.println("Finally "+b);          
	        }
	}

	@Test // (expected = NotEnoughMoneyException.class)
	public void testCreateBetWithInsufficientFunds() throws NotEnoughMoneyException {
		User user = new User("asier", "contreras", "11", new Date(2000, 5, 1), "11", false);
		user.setWallet(5.0f);

		Integer frNum = 1;
		String description = "";
		float winrate = 1.2f;
		Question question;
		float minimumBet = 1.0f;
		Question q11;
		Event ev11 = null;

		// Integer queryNumber, String query, float betMinimum, Event event

		q11 = new Question(1, "¿Quién ganará el partido?", 1f, ev11);
		Forecast forecast = new Forecast(frNum, description, winrate, q11);
		float expectedWalletValue = 10.0f;

		// Llama al método createBet y espera que lance una excepción
		// NotEnoughMoneyException.
		sut.createBet(1000.0f, user, forecast);
	}

}
