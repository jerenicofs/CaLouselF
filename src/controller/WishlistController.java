package controller;

import java.sql.PreparedStatement;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wishlists;
	}

}
