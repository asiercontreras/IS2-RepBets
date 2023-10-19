package dataAccess;

import java.util.logging.*;
import java.io.File;
import java.security.Permission;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.*;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessCreateBet {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	// Crear logger
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public DataAccessCreateBet(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessCreateBet() {
		this(false);
	}

	
	
	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */



	public float createBet(float bet, User usr, Forecast fr) throws NotEnoughMoneyException {
		System.out.println(">> DataAcces: CreateBet => quantity: " + bet + ", question: " + fr.getQuestion());
		User u = db.find(User.class, usr.getDni());
		Forecast f = db.find(Forecast.class, fr.getFrNum());
		Bet b = u.findBet(fr);
		float newValue = this.getNewWalletValue(u, b, bet);
		this.realizarApuestaEnBD(b, u, newValue, bet, f);
		return newValue;
	}

	/**
	 * Devuelve el nuevo valor que tendra la cartera en caso de que se pudiera hacer
	 * la apuesta
	 * 
	 * @param u   el usuario
	 * @param b   la clase de apuesta
	 * @param bet el dinero (float) que va a apostar
	 * @return
	 * @throws NotEnoughMoneyException
	 */
	private float getNewWalletValue(User u, Bet b, float bet) throws NotEnoughMoneyException {
		float newValue;
		if (b != null)
			newValue = u.getWallet() + b.getBet() - bet;
		else
			newValue = u.getWallet() - bet;
		if (newValue < 0)
			throw new NotEnoughMoneyException("You do not have enough money");
		return newValue;

	}

	/**
	 * realiza la apuesta en la BD
	 * 
	 * @param b        la clase apuesta
	 * @param u        Usuario
	 * @param newValue el nuevo valor que quedara en la cartera
	 * @param bet      el dinero que ha a apostado
	 * @param f        el forecast al que ha apostado
	 */
	private void realizarApuestaEnBD(Bet b, User u, float newValue, float bet, Forecast f) {
		db.getTransaction().begin();
		if (b != null) {
			Bet dbBet = db.find(Bet.class, b.getId());
			u.setWallet(newValue);
			dbBet.setBet(bet);
		} else {
			b = new Bet(bet, u, f);
			u.setWallet(newValue);
			u.addBet(b);
			f.addBet(b);
			db.persist(b);
		}
		db.getTransaction().commit();
	}

	

	public User createUser(User u)
			throws ObjectAlreadyExistException {
		System.out.println(">> DataAccess: CreateUser=> name = " + u.getName() + " dni = " + u.getDni());

		if (db.find(User.class, u.getDni()) != null)
			throw new ObjectAlreadyExistException("The user already exists");

		db.getTransaction().begin();
		db.persist(u);
		db.getTransaction().commit();
		return u;
	}
	public Event createEvent(String description, Date eventDate) throws ObjectAlreadyExistException {
		System.out.println(">> DataAccess: CreateEvent => number = " + description + ", date = " + eventDate);

		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.description=?2",
				Event.class);
		query.setParameter(1, eventDate);
		query.setParameter(2, description);
		if (!query.getResultList().isEmpty())
			throw new ObjectAlreadyExistException("the event already exists");

		db.getTransaction().begin();
		Event evnt = new Event(description, eventDate);
		db.persist(evnt);
		db.getTransaction().commit();
		return evnt;
	}
	public Forecast createForecast(String description, float winrate, Question question)
			throws ObjectAlreadyExistException {
		System.out.println(">> DataAccess: CreateForecast=> description: " + description + ", winrate: " + winrate);

		Question q = db.find(Question.class, question.getQuestionNumber());

		if (q.doesForecastExist(description))
			throw new ObjectAlreadyExistException("The forecast already exist for that question");

		db.getTransaction().begin();
		Forecast fr = q.addForecast(description, winrate);
		db.persist(q);
		db.getTransaction().commit();

		return fr;
	}


	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}


	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}




}
