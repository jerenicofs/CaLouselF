package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;

// Class tambahan untuk menyimpan offer yang dilakukan oleh buyer terhadap seller terhadap suatu item.
public class Offer {
	private String offerId; // Unique id untuk setiap offer yang dilakukan.
	private String itemId; // Foreign key: references items, menunjukkan item mana yang sedang dioffer oleh
							// buyer.
	private String userId; // Foreign key: references users, menunjukkan user mana yang melakukan offer.
	private String offeredPrice; // Harga yang ditawar oleh buyer.
	private String status; // default: Pending.
	private static DatabaseConnection db = DatabaseConnection.getInstance();

	public Offer(String offerId, String itemId, String userId, String offeredPrice, String status) {
		super();
		this.offerId = offerId;
		this.itemId = itemId;
		this.userId = userId;
		this.offeredPrice = offeredPrice;
		this.status = status;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
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

	public String getOfferedPrice() {
		return offeredPrice;
	}

	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Method tambahan untuk fetch semua offers yang ada
	public static ArrayList<Offer> GetAllOffers() {
		ArrayList<Offer> offers = new ArrayList<>();

		String query = "SELECT * from offers";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				offers.add(new Offer(db.rs.getString("offerId"), db.rs.getString("itemId"), db.rs.getString("userId"),
						db.rs.getString("offeredPrice"), db.rs.getString("status")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return offers;
	}

	// Method tambahan untuk mendapatkan highest offer terakhir dari sebuah item
	public static double GetHighestOffer(String itemId) {
		double highestOffer = 0.0;
		String query = "SELECT MAX(offeredPrice) AS highestOffer FROM offers WHERE itemId = ?";

		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, itemId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				highestOffer = rs.getDouble("highestOffer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return highestOffer;
	}
	

	// Method tambahan untuk delete offer berdasarkan itemId
	public static void DeleteOfferByItemId(String itemId) {
		String offQuery = "DELETE FROM offers WHERE itemId = ?";
		PreparedStatement ops = db.prepareStatement(offQuery);
		try {
			ops.setString(1, itemId);
			ops.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
