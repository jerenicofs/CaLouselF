package util;

import model.User;

public class Session {
	// Class tambahan untuk mendapatkan user yang sekarang sedang log in
	
	private static User user;
	
	public static User getUser() { return user;}
	
	public static void setUser(User user) { Session.user = user;}
	
	public static void clearSession() {user = null;}
}
