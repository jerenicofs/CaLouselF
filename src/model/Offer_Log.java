package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;

public class Offer_Log {
	private String offer_logId; // id offer_log
	private String userId; // id user yang offernya ditolak
	private String itemName; // nama item yang ditolak
	private String itemSize; // size item yang ditolak
	private String itemPrice; // price item yang ditolak
	private String itemCategory; // category item yang ditolak
	private String itemOfferPrice;// price yang ditawarkan untuk item
	private String reasonText; // alasan kenapa item ditolak (telah diisi admin)
	private static DatabaseConnection db = DatabaseConnection.getInstance();

	public Offer_Log(String offer_logId, String userId, String itemName, String itemSize, String itemPrice,
			String itemCategory, String itemOfferPrice, String reasonText) {
		super();
		this.offer_logId = offer_logId;
		this.userId = userId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemOfferPrice = itemOfferPrice;
		this.reasonText = reasonText;
	}

	public String getOffer_logId() {
		return offer_logId;
	}

	public void setOffer_logId(String offer_logId) {
		this.offer_logId = offer_logId;
	}

	public String getItemOfferPrice() {
		return itemOfferPrice;
	}

	public void setItemOfferPrice(String itemOfferPrice) {
		this.itemOfferPrice = itemOfferPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	// Method tambahan: Untuk fetch semua offerlog dari user (buyer) yang login.
	public static ArrayList<Offer_Log> GetAllOfferLog(String userId) {
		ArrayList<Offer_Log> offerLog = new ArrayList<>();
		String query = "SELECT * FROM offer_logs WHERE userId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, userId);
			db.rs = prepQuery.executeQuery();
			while (db.rs != null && db.rs.next()) {
				offerLog.add(new Offer_Log(db.rs.getString("offer_logId"), db.rs.getString("userId"),
						db.rs.getString("itemName"), db.rs.getString("itemSize"), db.rs.getString("itemPrice"),
						db.rs.getString("itemCategory"), db.rs.getString("itemOfferPrice"),
						db.rs.getString("reasonText")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return offerLog;
	}

	// Method tambahan untuk menambahkan offer log baru
	public static void AddOffer_Log(String offer_LogId, Offer o, String reasonText) {
		String query = "SELECT * FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		Item i = null;
		try {
			ps.setString(1, o.getItemId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				i = new Item(rs.getString("itemId"), rs.getString("itemName"), rs.getString("itemSize"),
						rs.getString("itemPrice"), rs.getString("itemCategory"), rs.getString("itemStatus"),
						rs.getString("itemWishlist"), rs.getString("itemOfferStatus"), rs.getString("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "INSERT INTO offer_logs (offer_logId, userId, itemName, itemSize, itemPrice, itemCategory, itemOfferPrice, reasonText)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		try {
			psQuery.setString(1, offer_LogId);
			psQuery.setString(2, o.getUserId());
			psQuery.setString(3, i.getItemName());
			psQuery.setString(4, i.getItemSize());
			psQuery.setString(5, i.getItemPrice());
			psQuery.setString(6, i.getItemCategory());
			psQuery.setString(7, o.getOfferedPrice());
			psQuery.setString(8, reasonText);
			psQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk mendapatkan id offerlog terakhir, yang nantinya akan
	// digunakan untuk mengenerate id baru
	public static String GetLastOfferLogId() {
		String query = "SELECT offer_logId FROM offer_logs ORDER BY offer_logId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("offer_logId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "OL000";
	}

}
