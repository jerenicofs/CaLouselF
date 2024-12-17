package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import util.Session;

public class Reason_log {
	private String reason_logId; // id reason_log
	private String userId; // id user yang barangnya ditolak
	private String itemName; // nama item yang ditolak
	private String itemSize; // size item yang ditolak
	private String itemPrice; // price item yang ditolak
	private String itemCategory; // category item yang ditolak
	private String reasonText; // alasan kenapa item ditolak (telah diisi admin)
	private static DatabaseConnection db = DatabaseConnection.getInstance();

	public Reason_log(String reasonId, String userId, String itemName, String itemSize, String itemPrice,
			String itemCategory, String reasonText) {
		super();
		this.reason_logId = reasonId;
		this.userId = userId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.reasonText = reasonText;
	}

	public String getReasonId() {
		return reason_logId;
	}

	public void setReasonId(String reasonId) {
		this.reason_logId = reasonId;
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

	// Method tambahan untuk fetch semua reason_log untuk suatu user
	public static ArrayList<Reason_log> GetAllReason_Log() {
		ArrayList<Reason_log> reasons = new ArrayList<>();
		String query = "SELECT * FROM reason_logs WHERE userId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, Session.getUser().getUserId());
			db.rs = prepQuery.executeQuery();

			while (db.rs != null && db.rs.next()) {
				reasons.add(new Reason_log(db.rs.getString("reason_logId"), db.rs.getString("userId"),
						db.rs.getString("itemName"), db.rs.getString("itemSize"), db.rs.getString("itemPrice"),
						db.rs.getString("itemCategory"), db.rs.getString("reasonText")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reasons;
	}

	// Method tambahan untuk dapet reason_logId terakhir
	public static String GetLastReasonLogId() {
		String query = "SELECT reason_logId FROM reason_logs ORDER BY reason_logId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("reason_logId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "R001";
	}

	// Method tambahan untuk buat reason_log baru
	public static void AddReason_Log(String reason_LogId, Item i, String reasonText) {
		String query = "INSERT INTO reason_logs (reason_logId, userId, itemName, itemSize, itemPrice, itemCategory, reasonText)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		try {
			psQuery.setString(1, reason_LogId);
			psQuery.setString(2, i.getUserId());
			psQuery.setString(3, i.getItemName());
			psQuery.setString(4, i.getItemSize());
			psQuery.setString(5, i.getItemPrice());
			psQuery.setString(6, i.getItemCategory());
			psQuery.setString(7, reasonText);
			psQuery.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
