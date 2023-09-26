package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.*;

@Entity
public class Forecast implements Serializable {
    @Id  
    @GeneratedValue
    private Integer frNum;
    private String description;
    private float winrate;
    private Question question;
    private float minimumBet;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Vector<Bet> bets = new Vector<>();

	public Forecast(){
        super();
    }

    public Forecast(Integer frNum, String description, float winratio, Question question){
        super();
        this.frNum = frNum;
        this.description = description;
        this.winrate = winratio;
        this.question = question;
        this.minimumBet = question.getBetMinimum();
    }

    public Forecast(String description, float winrate, Question question){
        super();
        this.description = description;
        this.winrate = winrate;
        this.question = question;
        this.minimumBet = question.getBetMinimum();
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getWinrate() {
		return winrate;
	}

	public void setWinrate(float winratio) {
		this.winrate = winratio;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

    public Integer getFrNum() {
        return frNum;
    }

    public void setFrNum(Integer frNum) {
        this.frNum = frNum;
    }

    public float getMinimumBet() {
        return minimumBet;
    }

    public void setMinimumBet(float minimumBet) {
        this.minimumBet = minimumBet;
    }

    public Vector<Bet> getBets() {
        return bets;
    }

    public void setBets(Vector<Bet> bets) {
        this.bets = bets;
    }

    public void addBet(Bet b){
        this.bets.add(b);
    }

    public Bet findBet(Forecast fr){
        for(Bet b: this.bets){
            if(b.getFr().equals(fr)) return b;
        }
        return null;
    }

    public String toString(){
        return this.description + "; minimum bet: " + this.minimumBet + "; winrate: " + this.winrate;
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null) return false;
        if(o.getClass() != this.getClass()) return false;

        Forecast f = (Forecast) o;
        if(this.getDescription().equals(f.getDescription()) && this.getQuestion().equals(f.getQuestion())) return true;
        return false;
    }
}

