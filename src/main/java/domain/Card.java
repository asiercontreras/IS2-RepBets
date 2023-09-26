package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {
    @Id
    private String cardNum;
    private int cvv;
    private User usr;
    private String last3Digits;

	public Card(User usr, String cardNum, int cvv, String last3Digits) {
        super();
        this.usr = usr;
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.last3Digits = last3Digits;
    }

    public String getLast3Digits() {
        return last3Digits;
    }

    public void setLast3Digits(String last3Digits) {
        this.last3Digits = last3Digits;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String toString(){
        return "*************"+ last3Digits;
    }
}
