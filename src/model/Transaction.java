package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;

public class Transaction {
	private String userId; // Foreign key: references users (buyer) yang melakukan transaksi ini.
	private String itemId; // Foreign key: references item yang dibeli pada transaksi ini.
	private String transactionId; // Unique id untuk setiap transaction.
	private static DatabaseConnection db = DatabaseConnection.getInstance();

	public Transaction(String userId, String itemId, String transactionId) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	// Method wajib untuk buyer melakukan purchase item
	// Nb: ditambahkan parameter tId yaitu transactionId yang sudah digenerate di
	// controller
	public static void PurchaseItems(String tId, String userId, String itemId) {
		// Buat transaction baru
		CreateTransaction(tId, userId, itemId);
	}

	// Method wajib: Method untuck fetch semua transaction yang dilakukan oleh user
	// yang sedang login.
	public static ArrayList<Transaction> ViewHistory(String userId) {
		ArrayList<Transaction> trans = new ArrayList<>();
		String query = "SELECT * from transactions WHERE userId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				trans.add(
						new Transaction(rs.getString("userId"), rs.getString("itemId"), rs.getString("transactionId")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trans;
	}

	// Method wajib: Bagi buyer untuk membuat transaksi baru
	// Parameter ditambahkan userId dan itemId
	public static void CreateTransaction(String transactionId, String userId, String itemId) {
		String query = "INSERT INTO transactions (userId, itemId, transactionId)" + "VALUES(?, ?, ?)";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, userId);
			ps.setString(2, itemId);
			ps.setString(3, transactionId);
			ps.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Method tambahan untuk fetch semua transactions yang ada
	public static ArrayList<Transaction> GetAllTransactions() {
		ArrayList<Transaction> transactions = new ArrayList<>();

		String query = "SELECT * from transactions";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				transactions.add(new Transaction(db.rs.getString("userId"), db.rs.getString("itemId"),
						db.rs.getString("transactionId")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return transactions;
	}

}
