package domain;

import javax.persistence.*;

@Entity
public class Bet {
    @Id @GeneratedValue
    private Integer id;
    private float bet;
    private User usr;
    private Forecast fr;

    public Bet(float bet, User usr, Forecast fr){
        this.bet = bet;
        this.usr = usr;
        this.fr = fr;
    }

    public Bet(Integer id, float bet, User usr, Forecast fr){
        this.id = id;
        this.bet = bet;
        this.usr = usr;
        this.fr = fr;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getBet() {
		return bet;
	}

	public void setBet(float bet) {
		this.bet = bet;
	}

	public User getUsr() {
		return usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

	public Forecast getFr() {
		return fr;
	}

	public void setFr(Forecast fr) {
		this.fr = fr;
	}

    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null) return false;
        if(o.getClass() != this.getClass()) return false;

        Bet f = (Bet) o;
        if(this.fr.equals(f.getFr())) return true;
        return false;
    }
}

