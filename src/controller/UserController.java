package controller;

import java.util.ArrayList;
import java.util.Random;
import model.User;

public class UserController {
	
	// Method wajib: Untuk cek validasi login
	public int login(String username, String password) {

		if(username.isEmpty()) return -1;
		else if(password.isEmpty()) return -2;
		
		// Kalau yang login adalah admin
		if(username.equals("admin") && password.equals("admin")) return 2;
		
		boolean loginFlag = false;
		
		ArrayList<User> users = User.getAllUsers();
		
		User loggedInUser = null;
		for(User x : users) {
			if(x.getUsername().equals(username) && x.getPassword().equals(password)) {
				loginFlag = true;
				loggedInUser = x;
				break;
			}
		}
		
		if(!loginFlag) return -3;
		
		// Panggil method di Model untuk login
		User.Login(loggedInUser);
		return 1;
	}
	
	// Method untuk menambahkan user baru ke database 
	// nb: ditambahkan userId dan role sebagai parameter
	public void register(String userId, String username, String password, String phoneNumber, String address, String role) {
		User.Register(userId, username, password, phoneNumber, address, role);
	}
	
	// Method untuk check validasi register
	// nb: ditambahkan parameter role
	public double checkAccountValidation(String username, String password, String phoneNumber, String address, String role) {
		if(username.isEmpty()) return -1.1;
		else if(password.isEmpty()) return -2.1;
		else if(phoneNumber.isEmpty()) return -3.1;
		else if(address.isEmpty()) return -4;
		
		ArrayList<User> users = User.getAllUsers();
		
		// Username validation
		if(username.length() < 3) return -1.2;
		for (User x: users) {
			if(x.getUsername().equals(username)) return -1.3;
		}
		
		// Password validation
		if(password.length() < 8) return -2.2;
		String specialList = "!@#$%^&*";
		boolean checkSpecial = false;
		for(int i = 0; i < password.length(); i++) {
			char cek = password.charAt(i);
			if(specialList.indexOf(cek) != -1) {
				checkSpecial = true; break;
			}
		}
		if(!checkSpecial) return -2.3;
		
		// Phone number validation
		if (!phoneNumber.startsWith("+62")) return -3.2;
		String rest = phoneNumber.substring(3);
		if(rest.length() < 9) return -3.2;
		
		String userId = generateId();
		
		register(userId, username, password, phoneNumber, address, role);
		
		return 1;
	}
	
	// Method tambahan untuk generate randomId dan unique
	public String generateId() {
		ArrayList<User> users = User.getAllUsers();
		
		Random rand = new Random();
		
		String id;
		boolean isUnique = true;
		while(true) {
			int rand1 = rand.nextInt(9) + 1;
			int rand2 = rand.nextInt(10);
			int rand3 = rand.nextInt(10);
				
			id = String.format("U%d%d%d", rand1, rand2, rand3);
			
			for (User x : users) {
				if(x.getUserId().equals(id)) {
					isUnique = false; break;
				}
			}
			
			if(isUnique) break;
		}
		
		return id;
	}
	
}
