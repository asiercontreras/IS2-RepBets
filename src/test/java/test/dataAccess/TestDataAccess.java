package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import domain.Bet;
import exceptions.ObjectAlreadyExistException;
import exceptions.QuestionAlreadyExist;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		public User createBetUser(User usuario) throws ObjectAlreadyExistException{
			System.out.println(">> DataAccessTest: createUser");
			//System.out.println(">> DataAccess: CreateUser=> name = "+name+" dni = "+dni);
	        if(db.find(User.class, usuario.getDni()) != null) throw new ObjectAlreadyExistException("The user already exists");

	        db.getTransaction().begin();
	        User usr = usuario;
	        db.persist(usr);
	        db.getTransaction().commit();
	        
	        return usr;
	        
			


		}
		public Forecast createBetForecast(Forecast forec) throws ObjectAlreadyExistException {
			System.out.println(">> DataAccessTest: createForecast");
			Question q = db.find(Question.class, forec.getQuestion().getQuestionNumber());

	        if(q.doesForecastExist(forec.getDescription())) throw new ObjectAlreadyExistException("The forecast already exist for that question");

	        db.getTransaction().begin();
	        Forecast fr = q.addForecast(forec.getDescription(), forec.getWinrate());
	        db.persist(q);
	        db.getTransaction().commit();
	        
	        return fr;
		}
		public boolean removeUserDado(User usr) {
			
			if(db.find(User.class, usr.getDni()) != null) {
				
				db.getTransaction().begin();
				db.remove(usr);
			
		        db.getTransaction().commit();
		        return true;
			} else
			return false;
		}
		
		public boolean removeBet(Bet b)  {
				if(db.find(Bet.class, b.getId()) != null) {
				db.getTransaction().begin();
				db.remove(b);
			
		        db.getTransaction().commit();
		        return true;
			} else
			return false;
		}
		public boolean removeForecast(Forecast f) {
			System.out.println(">> DataAccessTest: removeForecast");
			Forecast e = db.find(Forecast.class, f.getFrNum());
			if (e!=null) {
				db.getTransaction().begin();
				db.remove(e);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public boolean existEvent(Event ev) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return true;
			} else 
			return false;
			
		} 
}

