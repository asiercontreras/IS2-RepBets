package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.*;
import org.mockito.*;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import domain.*;
import exceptions.NotEnoughMoneyException;
import dataAccess.DataAccess;

public class CreateBetBLBMTest {

	int frNum;
	String description;
	float winrate;
	Question q11;
	int month;
	int year;
	Event ev11;
	char[] pass = { '1', '1' };
	
	@InjectMocks
	BLFacadeImplementation sut;

	@Mock
	DataAccess mockitoDA;

	@Before
	public void setUp() {
		// Creamos los mocks
		mockitoDA = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(mockitoDA);
		frNum = 1111;
		description = "Atleti";
		winrate = 1.2f;
		Calendar today = Calendar.getInstance();

		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
	}

	@Test //Realizamos una apuesta con dinero suficiente
	public void test1() throws NotEnoughMoneyException {

		ev11 = new Event("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 10.0f;
		User user1 = new User("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(20.0f);
		Forecast forecast1 = new Forecast(description, winrate, q11);
		Mockito.doReturn(10.0f).when(mockitoDA).createBet(10.0f, user1, forecast1);
		try {
			float newValue = sut.createBet(10.0f, user1, forecast1);
			// Verifica que el nuevo valor de la billetera sea el esperado.
			assertEquals(expectedWalletValue, newValue, 0.01);
		} catch (NotEnoughMoneyException e) {
			fail("Algo ha ido mal en la ejecución.");
		}

	}
	
	@Test //Realizamos una apuesta sin dinero suficiente
	public void test2() throws NotEnoughMoneyException {

		ev11 = new Event("Gana Atleti", UtilDate.newDate(year, month, 17));
		q11 = ev11.addQuestion("¿Quién ganará el partido?", 1f);
		float expectedWalletValue = 10.0f;
		User user1 = new User("asier", "contreras", "24", new Date(2000, 5, 1), "11", false);
		user1.setWallet(20.0f);
		Forecast forecast1 = new Forecast(description, winrate, q11);
		
		//No hace falta mockear nada porque se supone que la excepcion saltara antes
		
		assertThrows(NotEnoughMoneyException.class,() -> {sut.createBet(30.0f, user1, forecast1);});
	}
	

}
