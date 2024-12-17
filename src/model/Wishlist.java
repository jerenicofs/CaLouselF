package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;

public class Wishlist {
	private String wishlistId; // Unique id untuk setiap wishlist.
	private String itemId; // Foreign key: references items yang ditandai sebagai wishlist oleh user.
	private String userId; // Foreign key: references users yang menandai sebuah item sebagai wishlist
							// mereka.
	private static DatabaseConnection db = DatabaseConnection.getInstance();

	public Wishlist(String wishlistId, String itemId, String userId) {
		super();
		this.wishlistId = wishlistId;
		this.itemId = itemId;
		this.userId = userId;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// Method wajib: untuk BuyerViewWishlistPage, fetch semua item yang di wishlist
	// oleh user
	// Parameter String wishlistId diremove karena tidak diperlukan, hanya perlu
	// userId untuk fetch semua user's wishlist
	public static ArrayList<Wishlist> ViewWishlist(String userId) {
		ArrayList<Wishlist> wishlists = new ArrayList<>();

		String query = "SELECT * FROM wishlists WHERE userId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);

		try {
			prepQuery.setString(1, userId);
			db.rs = prepQuery.executeQuery();
			while (db.rs != null && db.rs.next()) {
				wishlists.add(new Wishlist(db.rs.getString("wishlistId"), db.rs.getString("itemId"),
						db.rs.getString("userId")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishlists;
	}

	// Method wajib: untuk insert ke db wishlist
	// Nb: ditambahkan parameter baru yaitu wishlistNewid yang sudah digenerate di
	// wishlist controller
	public static void AddWishlist(String wishlistNewId, String itemId, String userId) {
		String query = "INSERT INTO wishlists (wishlistId, itemId, userId)" + "VALUES (?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		try {
			psQuery.setString(1, wishlistNewId);
			psQuery.setString(2, itemId);
			psQuery.setString(3, userId);
			psQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method wajib: Method untuk meremove wishlist dari database wishlists.
	public static void RemoveWishlist(String wishlistId) {
		String query = "DELETE FROM wishlists WHERE wishlistId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, wishlistId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk delete wishlist berdasarkan itemId
	public static void DeleteWishlistByItemId(String itemId) {
		String wQuery = "DELETE FROM wishlists WHERE itemId = ?";
		PreparedStatement wps = db.prepareStatement(wQuery);
		try {
			wps.setString(1, itemId);
			wps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk mendapat id terakhir dari wishlist, yang nantinya
	// digunakan untuk mengenerate id wishlist baru.
	public static String GetLastId() {
		String query = "SELECT wishlistId FROM wishlists ORDER BY wishlistId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("wishlistId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "W000";
	}

	// Method tambahan untuk fetch wishlistId berdasarkan itemId dan userId
	public static String GetWishlistIdByItemIdAndUserId(String itemId, String userId) {
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
	public static boolean IsItemInWishlist(String itemId, String userId) {
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
