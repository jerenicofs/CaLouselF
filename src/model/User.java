package model;

public class User {
	
	private String userId; // unique id untuk setiap user
	private String username; // username dari user
	private String password; // password dari user
	private String phoneNumber; // nomor telepon dari user 
	private String address; // alamat dari user
	private String role; // role dari user (buyer dan seller). kalau bukan keduanya, maka admin
	
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
	
	
}
