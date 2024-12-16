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
	
	// Method wajib: untuk BuyerViewWishlistPage, fetch semua item yang di wishlist oleh user
	// Parameter String wishlistId diremove karena tidak diperlukan, hanya perlu userId untuk fetch semua user's wishlist
	public ArrayList<Wishlist> viewWishlist(String userId) {
		ArrayList<Wishlist> wishlists = new ArrayList<>();
		
		String query = "SELECT * FROM wishlists WHERE userId = ?";	
		PreparedStatement prepQuery = db.prepareStatement(query);
		
		try {
			prepQuery.setString(1, userId);
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
		String query = "INSERT INTO wishlists (wishlistId, itemId, userId)" + "VALUES (?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		String wishlistNewId = generateNewWishlistId();
		try {
			psQuery.setString(1, wishlistNewId);
			psQuery.setString(2, itemId);
			psQuery.setString(3, userId);
			psQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method tambahan untuk mendapat id terakhir dari wishlist, yang nantinya digunakan untuk mengenerate id wishlist baru.
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
		
		return "W000";
		
	}
	
	// Method tambahan untuk mengenerate id wishlist baru.
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
	
	// Method wajib: Method untuk meremove wishlist dari database wishlists.
	public void removeWishlist(String wishlistId) {
		String query = "DELETE FROM wishlists WHERE wishlistId = ?";
	    PreparedStatement ps = db.prepareStatement(query);
	    try {
	        ps.setString(1, wishlistId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// Method tambahan untuk fetch wishlistId berdasarkan itemId dan userId
	public String getWishlistIdByItemIdAndUserId(String itemId, String userId) {
	    String query = "SELECT wishlistId FROM wishlists WHERE itemId = ? AND userId = ?";
	    PreparedStatement ps = db.prepareStatement(query);
	    try {
	        ps.setString(1, itemId);
	        ps.setString(2, userId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("wishlistId");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	// Method tambahan untuk cek apakah item sudah di wishlist oleh user
	public boolean isItemInWishlist(String itemId, String userId) {
	    String query = "SELECT COUNT(*) FROM wishlists WHERE itemId = ? AND userId = ?";
	    PreparedStatement ps = db.prepareStatement(query);
	    try {
	        ps.setString(1, itemId);
	        ps.setString(2, userId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}


}
