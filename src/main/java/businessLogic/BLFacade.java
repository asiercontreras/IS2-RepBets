package businessLogic;

import java.util.Vector;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import domain.*;
import exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	@WebMethod public User createUser(User u) throws ObjectAlreadyExistException, NoSuchAlgorithmException;
	
	@WebMethod public Event createEvent(String description, Date eventDate) throws EventFinished, ObjectAlreadyExistException;

    @WebMethod public User getUser(String dni) throws UserNotFound;

    @WebMethod public Forecast createForecast(String description, float winrate, Question question) throws ObjectAlreadyExistException;

    @WebMethod
    public Vector<Forecast> getQuestionForecasts(Question question);

    @WebMethod
    public float createBet(float bet,User usr, Forecast fr) throws NotEnoughMoneyException;

    @WebMethod
    public User getCurrentUser();

    @WebMethod
    public void setCurrentUser(User usr);

    @WebMethod
    public float addMoney(float money);

    @WebMethod
    public Card addCard(long cardNum, int cvv) throws ObjectAlreadyExistException, NoSuchAlgorithmException;
    
    @WebMethod public void setResult(Question question, Forecast forecast);
    
    @WebMethod public User guardarCambios(String name, String surnames, Date birthdate, String dni, char[] passwd, byte[] img) throws NoSuchAlgorithmException;

    @WebMethod
    public void closeEvent(Event ev);

    @WebMethod
    public boolean isHash(char[] passwd, String hash);
}
