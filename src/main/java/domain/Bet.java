package domain;

import java.util.Objects;

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

    @Override
	public int hashCode() {
		return Objects.hash(bet, fr, id, usr);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bet other = (Bet) obj;
		return Float.floatToIntBits(bet) == Float.floatToIntBits(other.bet) && Objects.equals(fr, other.fr)
				&& Objects.equals(id, other.id) && Objects.equals(usr, other.usr);
	}
	/*
	public boolean equals(Object o){
        if(o == this) return true;
        if(o == null) return false;
        if(o.getClass() != this.getClass()) return false;

        Bet f = (Bet) o;
        if(this.fr.equals(f.getFr())) return true;
        return false;
    }*/
    
}

