package domain;

import java.io.*;
import java.util.Objects;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String pregunta; 
	private float betMinimum;
	private Forecast result = null;
	@XmlIDREF
	private Event event;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Vector<Forecast> forecasts = new Vector<>();


	public Question(){
		super();
	}
	
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.pregunta = query;
		this.betMinimum=betMinimum;
		this.event = event;
	}
	
	public Question(String query, float betMinimum,  Event event) {
		super();
		this.pregunta = query;
		this.betMinimum=betMinimum;

		this.event = event;
	}

    public Vector<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(Vector<Forecast> forecast) {
        this.forecasts = forecast;
    }

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return pregunta;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.pregunta = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public Forecast getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(Forecast result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

    public boolean doesForecastExist(String description){
        System.out.println(this.forecasts.size());
        for(Forecast i: this.forecasts){
            if(i.getDescription().compareTo(description) == 0)
                return true;
        }
        return false;
    }

    public Forecast addForecast(String description, float winrate){
        Forecast fr = new Forecast(description, winrate, this);
        this.forecasts.add(fr);
        return fr;
    }

	public String toString(){
		return questionNumber+";"+pregunta+";"+Float.toString(betMinimum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(betMinimum, event, forecasts, pregunta, questionNumber, result);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Float.floatToIntBits(betMinimum) == Float.floatToIntBits(other.betMinimum)
				&& Objects.equals(event, other.event) && Objects.equals(forecasts, other.forecasts)
				&& Objects.equals(pregunta, other.pregunta) && Objects.equals(questionNumber, other.questionNumber)
				&& Objects.equals(result, other.result);
	}

}
