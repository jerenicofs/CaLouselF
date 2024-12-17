package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import util.Session;

public class User {
	
	private String userId; // unique id untuk setiap user
	private String username; // username dari user
	private String password; // password dari user
	private String phoneNumber; // nomor telepon dari user 
	private String address; // alamat dari user
	private String role; // role dari user (buyer dan seller). kalau bukan keduanya, maka admin
	private static DatabaseConnection db = DatabaseConnection.getInstance();
	
	public User(String userId, String username, String password, String phoneNumber, String address, String role) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	// Method wajib: Untuk membuat session user yang login
	// Nb: Parameter diganti menjadi User karena validasi sudah ditangani di controller
	public static void Login(User loggedInUser) {
		Session.setUser(loggedInUser);
	}
	
	// Method wajib: untuk menambahkan user baru ke database 
	// nb: ditambahkan userId dan role sebagai parameter
	public static void Register(String userId, String username, String password, String phoneNumber, String address, String role) {
		String query = "INSERT INTO users (userId, username, password, phoneNumber, address, role)" + "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		
		try {
			psQuery.setString(1, userId);
			psQuery.setString(2, username);
			psQuery.setString(3, password);
			psQuery.setString(4, phoneNumber);
			psQuery.setString(5, address);
			psQuery.setString(6, role);
			psQuery.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	// Method wajib
	// Validasi sudah dilakukan di controller
	public static void CheckAccountValidation(String username, String password, String phoneNumber, String address) {
		
	}
	
	// Method tambahan untuk fetch semua users
	public static ArrayList<User> getAllUsers(){
		ArrayList<User> res = new ArrayList<>();
		
		String query = "SELECT * FROM users";
		PreparedStatement prepQuery = db.prepareStatement(query);
		
		try {
			db.rs = prepQuery.executeQuery();
			while(db.rs.next()) {
				res.add(new User(db.rs.getString("userId"), db.rs.getString("username"), db.rs.getString("password"),
						db.rs.getString("phoneNumber"), db.rs.getString("address"), db.rs.getString("role")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;

	}
}
