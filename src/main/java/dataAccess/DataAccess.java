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
public class DataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	// Crear logger
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			// Crear el nombre del que va a tener el archivo
			String nombreArchivo = this.getClass().getSimpleName() + "LOGGER.log";

			// Crear un fichero para saber si se ha creado o no
			File archivo = new File(nombreArchivo);

			// Comprobar si ya existe el archivo para no crear otro
			if (!archivo.exists()) {
				// Crear el fichero
				Handler fileHandler = new FileHandler(nombreArchivo);

				// El formato que vayamos a querer darle al logger
				fileHandler.setFormatter(new SimpleFormatter());

				fileHandler.setLevel(Level.INFO);

				logger.addHandler(fileHandler);
			}

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();

			this.createForecast("Atletico", 1.2f, q1);
			this.createForecast("Athletic", 2.2f, q1);
			this.createForecast("Toquero", 1.2f, q2);
			this.createForecast("Fernano 'el niño' Torres", 2.2f, q2);

			// El mensaje que queremos poner cuando el programa se ejecute correctamente
			logger.log(Level.INFO, ">>>>>>> BD inicializada\n");
		} catch (SecurityException e) {
			// El mensaje que queremos poner cuando el programa salte un error
			logger.log(Level.INFO, ">>>>>>> ERROR en el logger\n");
		} catch (Exception E) {
			logger.log(Level.INFO, ">>>>>>> ERROR al inicializar la BD\n");
		}
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
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * Creates a user
	 * 
	 * @param name      users name
	 * @param surnames  users surnames
	 * @param dni       users dni
	 * @param birthdate users birthdate
	 * @param passwd    users password
	 * @param isAdmin   true if the user is an admin
	 * @return the user
	 * @throws UserAlreadyExist if a user with the same dni exists
	 */
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

	/**
	 * creates an event
	 * 
	 * @param eventNumber the id of the event
	 * @param description description of the event
	 * @param eventDate   the date of the event
	 * @return the event
	 * @throws ObjectAlreadyExistException if the event already exists
	 */
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

	/**
	 * creates a forecast for a question
	 * 
	 * @param description the answer to the question
	 * @param winrate     the money multiplier if the forecast is correct
	 * @param question    the question for which the forecast is
	 * @return the forecast
	 * @throws ForecastAlreadyExist if the forecast already exists for that question
	 */
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

	/**
	 * creates a bet, if the user already has a bet for that forecast, the bet is
	 * updated
	 * 
	 * @param bet the bet value
	 * @param usr the user making the bet
	 * @param fr  the forecast for which the bet is
	 * @return the bet
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

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * searches a user in the database
	 * 
	 * @param dni users dni
	 * @return the user
	 * @throws UserNotFound if the user does not exist
	 */
	public User getUser(String dni) throws UserNotFound {
		System.out.println(">> DataAccess: getUser");

		User usr = db.find(User.class, dni);
		if (usr == null)
			throw new UserNotFound("User not found.");

		return usr;
	}

	/**
	 * adds money to a user
	 * 
	 * @param usr   the user to which the money is added
	 * @param money the money to add
	 * @return the final value of the users wallet
	 */
	public float addMoney(User usr, float money) {
		System.out.println(">> DataAccess: addMoney: " + money);

		User u = db.find(User.class, usr.getDni());

		db.getTransaction().begin();
		u.setWallet(u.getWallet() + money);
		db.getTransaction().commit();

		return u.getWallet();
	}

	public Card addCard(User usr, String cardNum, int cvv, String last3Digits) throws ObjectAlreadyExistException {
		System.out.println(">>DataAccess: addCard: " + cardNum + "for user: " + usr.getDni());

		User u = db.find(User.class, usr.getDni());
		Card card = db.find(Card.class, cardNum);
		if (card != null)
			throw new ObjectAlreadyExistException("the card already exists");

		db.getTransaction().begin();
		card = u.addCard(cardNum, cvv, last3Digits);
		db.persist(u);
		db.getTransaction().commit();

		return card;
	}

	/**
	 * this method closes an event
	 * 
	 * @param ev the event to close
	 */
	public void closeEvent(Event ev) {
		System.out.println(">> DataAccess: closing event: " + ev.getDescription());
		Event event = db.find(Event.class, ev.getEventNumber());
		db.getTransaction().begin();
		event.setClosed(true);
		db.getTransaction().commit();
	}

	/**
	 * sets the winner forecast to a question
	 * 
	 * @param question the question whose result is set
	 * @param forecast the forecast that won the question
	 */
	public void setResult(Question question, Forecast forecast) {
		System.out.println(">> DataAccess: Asignado " + forecast.toString() + " a " + question.toString());
		Question q = db.find(Question.class, question.getQuestionNumber());
		Forecast fr = db.find(Forecast.class, forecast.getFrNum());

		db.getTransaction().begin();
		q.setResult(fr);
		for (Bet b : fr.getBets()) {
			User usr = b.getUsr();
			usr.setWallet(usr.getWallet() + b.getBet() * forecast.getWinrate());
		}
		db.getTransaction().commit();
	}

	/**
	 * searches the forecasts of a question in the database
	 * 
	 * @param question the question for which the forecasts are
	 * @return a vector of forecasts
	 */
	public Vector<Forecast> getQuestionForecasts(Question question) {
		Question q = db.find(Question.class, question.getQuestionNumber());
		return q.getForecasts();
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

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public Vector<User> getAllUsers() {
		System.out.println(">> DataAccess: getAllUsers");
		Vector<User> res = new Vector<User>();
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u", User.class);
		List<User> users = query.getResultList();
		for (User u : users) {
			res.add(u);
		}
		return res;
	}

	public User guardarCambios(String name, String surnames, Date birthdate, String dni, String passwd, byte[] img) {
		System.out.println(">> DataAccess: guardarCambios => name=" + name + "surname" + surnames + "birthdate"
				+ birthdate + "dni" + dni);
		User u = db.find(User.class, dni);
		db.getTransaction().begin();
		u.setName(name);
		u.setSurnames(surnames);
		u.setBirthdate(birthdate);
		u.setPasswd(passwd);
		u.setProfile_img(img);
		db.getTransaction().commit();
		return u;

	}
}
