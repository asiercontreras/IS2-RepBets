package domain;

import java.util.Vector;

public class UserAdapter {

	private User user;
	
	public UserAdapter(User user) {
		this.user=user;
	}

	public Vector<Bet> getBets() {
		// TODO Auto-generated method stub
		return user.getBets();
	}
}
