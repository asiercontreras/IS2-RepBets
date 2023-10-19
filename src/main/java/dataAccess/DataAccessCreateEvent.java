package dataAccess;

import java.util.logging.*;
import java.io.File;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.*;

public class DataAccessCreateEvent {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	// Crear logger
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public DataAccessCreateEvent(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessCreateEvent() {
		this(false);
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
