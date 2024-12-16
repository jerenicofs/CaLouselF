package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.DatabaseConnection;
import model.Transaction;

public class TransactionController {
	
	private DatabaseConnection db = DatabaseConnection.getInstance();
	
	// Method wajib untuk buyer melakukan purchase item
	public void purchaseItems(String userId, String itemId) {
		
		// Buat transaction baru 
		String tId = generateTransactionId();
		createTransaction(tId, userId, itemId);
		
		// Remove item yang telah dipurchase dari seluruh wishlist user
		deletewishlistByItemId(itemId);
		
		// Remove item dari database offers
		deleteOfferByItemId(itemId);
	    
	}
	
	// Method tambahan untuk delete offer berdasarkan itemId
	public void deleteOfferByItemId(String itemId) {
		String offQuery = "DELETE FROM offers WHERE itemId = ?";
		PreparedStatement ops = db.prepareStatement(offQuery);
		try {
			ops.setString(1, itemId);
			ops.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method tambahan untuk delete wishlist berdasarkan itemId
	public void deletewishlistByItemId(String itemId) {
		String wQuery = "DELETE FROM wishlists WHERE itemId = ?";
		PreparedStatement wps = db.prepareStatement(wQuery);
		try {
			wps.setString(1, itemId);
			wps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method wajib: Method untuck fetch semua transaction yang dilakukan oleh user yang sedang login.
	public ArrayList<Transaction> viewHistory(String userId) {
		ArrayList<Transaction> trans = new ArrayList<>();
		String query = "SELECT * from transactions WHERE userId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

	        while (rs != null && rs.next()) {
	            trans.add(new Transaction(
	                rs.getString("userId"),
	                rs.getString("itemId"),
	                rs.getString("transactionId")
	            ));
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trans;
	}
	
	// Method wajib: Bagi buyer untuk membuat transaksi baru
	// Parameter ditambahkan userId dan itemId
	public void createTransaction(String transactionId, String userId, String itemId) {
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
	public ArrayList<Transaction> getAllTransactions(){
		ArrayList<Transaction> transactions = new ArrayList<>();

		String query = "SELECT * from transactions";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				transactions.add(new Transaction(db.rs.getString("userId"), db.rs.getString("itemId"), db.rs.getString("transactionId")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return transactions;
	}
	
	// Method tambahan untuk generate unique random transactionId
	public String generateTransactionId() {
		String id = "";
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions = getAllTransactions();

		Random rand = new Random();
		boolean isUnique = true;
		while (true) {
			int rand1 = rand.nextInt(9) + 1;
			int rand2 = rand.nextInt(10);
			int rand3 = rand.nextInt(10);
			id = String.format("T%d%d%d", rand1, rand2, rand3);

			for (Transaction x : transactions) {
				if (x.getTransactionId().equals(id)) {
					isUnique = false;
					break;
				}
			}
			if (isUnique)
				break;

		}
		return id;
	}
}
