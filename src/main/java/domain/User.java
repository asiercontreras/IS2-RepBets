package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.*;

@Entity
public class User implements Serializable {
    private String name;
    private String surnames;
    @Id
    private String dni;
    private Date birthdate;
    private String passwd;
    private boolean isAdmin;
    private float wallet = 0f;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Vector<Bet> bets = new Vector<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Vector<Card> savedCards = new Vector<>();
    private byte[] profile_img = null;

	public User(String name, String surnames, String dni, Date birthdate, String passwd, boolean isAdmin){
        this.name = name;
        this.surnames = surnames;
        this.birthdate = birthdate;
        this.passwd = passwd;
        this.isAdmin = isAdmin;
        this.dni = dni;
    }

	public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return this.surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public Vector<Bet> getBets() {
        return bets;
    }

    public void setBets(Vector<Bet> bets) {
        this.bets = bets;
    }

    public byte[] getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(byte[] profile_img) {
        this.profile_img = profile_img;
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

	public Vector<Card> getSavedCards() {
		return savedCards;
	}

	public void setSavedCards(Vector<Card> savedCards) {
		this.savedCards = savedCards;
	}

    public Card addCard(String cardNum, int cvv, String last3Digits){
        Card card = new Card(this, cardNum, cvv, last3Digits);
        this.savedCards.add(card);
        return card;
    }

    public void addCard(Card card){
        this.savedCards.add(card);
    }
}

