package businessLogic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.*;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;
	private User currentUsr = null;

	String init = "initialize";

	public BLFacadeImplementation() {

		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(init)) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals(init));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(init)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	// Metodo para guardarCambios
	@WebMethod
	public User guardarCambios(String name, String surnames, Date birthdate, String dni, char[] passwd, byte[] img)
			throws NoSuchAlgorithmException {
		User user = null;
		String hashedPass = null;

		if (passwd.length != 0) {
			try {
				hashedPass = hashPass(String.valueOf(passwd));
				passwd = null;
			} catch (NoSuchAlgorithmException e) {
				throw e;
			}
		} else
			hashedPass = this.getCurrentUser().getPasswd();

		dbManager.open(false);
		try {
			user = dbManager.guardarCambios(name, surnames, birthdate, dni, hashedPass, img);
		} catch (Exception e) {
			throw e;
		} finally {
			dbManager.close();
		}
		return user;
	}

	public User getCurrentUser() {
		return currentUsr;
	}

	public void setCurrentUser(User usr) {
		this.currentUsr = usr;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {
		// The minimum bed must be greater than 0
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0 || event.isClosed())
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		dbManager.open(false);

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();


		return qry;
	};

	@WebMethod
	public User createUser(String name, String surnames, String dni, Date birthdate, char[] passwd, boolean isAdmin)
			throws ObjectAlreadyExistException, NoSuchAlgorithmException {
		User usr = null;
		String pass = String.valueOf(passwd);

		String hash = hashPass(pass);
		System.out.println("hashed passwd: " + hash);
		pass = null;
		passwd = null;

		dbManager.open(false);

		try {
			usr = dbManager.createUser(name, surnames, dni, birthdate, hash, isAdmin);
		} catch (ObjectAlreadyExistException e) {
			throw e;
		} finally {
			dbManager.close();
		}

		return usr;
	}

	@WebMethod
	public Event createEvent(String description, Date eventDate) throws EventFinished, ObjectAlreadyExistException {
		Event evnt = null;

		if (new Date().compareTo(eventDate) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		dbManager.open(false);
		try {
			evnt = dbManager.createEvent(description, eventDate);
		} catch (ObjectAlreadyExistException e) {
			throw e;
		} finally {
			dbManager.close();
		}
		return evnt;
	}

	@WebMethod
	public Forecast createForecast(String description, float winrate, Question question)
			throws ObjectAlreadyExistException {
		Forecast fr = null;

		dbManager.open(false);
		try {
			fr = dbManager.createForecast(description, winrate, question);
		} catch (ObjectAlreadyExistException e) {
			throw e;
		} finally {
			dbManager.close();
		}
		return fr;
	}
	// Crear metodo que abre la base de datos dbOPen

	@WebMethod
	public float createBet(float bet, User usr, Forecast fr) throws NotEnoughMoneyException {
		float wallet;
		dbManager.open(false);
		try {
			wallet = dbManager.createBet(bet, usr, fr);
		} catch (NotEnoughMoneyException e) {
			throw e;
		} finally {
			dbManager.close();
		}
		return wallet;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	@WebMethod
	public User getUser(String dni) throws UserNotFound {
		dbManager.open(false);
		User usr = null;
		try {
			usr = dbManager.getUser(dni);
		} catch (UserNotFound e) {
			throw e;
		} finally {
			dbManager.close();
		}
		return usr;
	}

	@WebMethod
	public float addMoney(float money) {
		User usr = this.getCurrentUser();
		dbManager.open(false);
		float new_wallet = dbManager.addMoney(usr, money);
		dbManager.close();
		return new_wallet;
	}

	@WebMethod
	public Card addCard(long cardNum, int cvv) throws ObjectAlreadyExistException, NoSuchAlgorithmException {
		User usr = this.getCurrentUser();
		Card card = null;

		String num = String.valueOf(cardNum);
		String last3Digits = num.substring(num.length() - 3, num.length() - 0);
		String hashedNum = hashPass(num);
		System.out.println("hash: " + hashedNum);

		dbManager.open(false);
		try {
			card = dbManager.addCard(usr, hashedNum, cvv, last3Digits);
		} finally {
			dbManager.close();
		}

		return card;
	}

	@WebMethod
	public Vector<Forecast> getQuestionForecasts(Question question) {
		dbManager.open(false);
		Vector<Forecast> forecasts = dbManager.getQuestionForecasts(question);
		dbManager.close();
		return forecasts;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public void setResult(Question question, Forecast fr) {
		dbManager.open(false);
		dbManager.setResult(question, fr);
		dbManager.close();
	}

	@WebMethod
	public void closeEvent(Event ev) {
		dbManager.open(false);
		dbManager.closeEvent(ev);
		dbManager.close();
	}

	private String hashPass(String pass) throws NoSuchAlgorithmException {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			hash = new String(md.digest(pass.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		return hash;
	}

	@WebMethod
	public boolean isHash(char[] passwd, String hash) {
		String pass = String.valueOf(passwd);
		try {
			return hashPass(pass).equals(hash);
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}
}
