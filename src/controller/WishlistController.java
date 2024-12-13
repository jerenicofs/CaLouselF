package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Wishlist;
import util.Session;

public class WishlistController {
	private DatabaseConnection db = DatabaseConnection.getInstance();

	public ArrayList<Wishlist> getAllWishlist(){
		ArrayList<Wishlist> wishlists = new ArrayList<>();
		
		String query = "SELECT * FROM wishlists WHERE userId = ?";
		
		PreparedStatement prepQuery = db.prepareStatement(query);
		
		try {
			prepQuery.setString(1, Session.getUser().getUserId());
			db.rs = prepQuery.executeQuery();
			while (db.rs != null && db.rs.next()) {
	            wishlists.add(new Wishlist(
	                db.rs.getString("wishlistId"),
	                db.rs.getString("itemId"),
	                db.rs.getString("userId")
	            ));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishlists;
	}
	
	// Method wajib: untuk insert ke db wishlist
	public void addWishlist(String itemId, String userId) {
		String query = "INSERT INTO wishlistId (wishlistId, itemId, userId)" + "VALUES (?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		String wishlistNewId = generateNewWishlistId();
		try {
			psQuery.setString(1, wishlistNewId);
			psQuery.setString(2, itemId);
			psQuery.setString(3, userId);
			psQuery.executeUpdate();
			//Coba buat pake Item item nanti
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getLastId() {
		String query = "SELECT wishlistId FROM wishlists ORDER BY wishlistId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("wishlistId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "W001";
		
		
	}
	
	public String generateNewWishlistId() {
		String lastId = getLastId();
		String numberId = lastId.substring(1);
		try {
			int num = Integer.parseInt(numberId);
			num++;
			return String.format("W%03d", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "W001";
	}

}
