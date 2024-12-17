package controller;

import java.util.ArrayList;
import java.util.Random;
import model.Offer;
import model.Transaction;
import model.Wishlist;

public class TransactionController {
	
	// Method wajib untuk buyer melakukan purchase item
	public void purchaseItems(String userId, String itemId) {
		
		// Buat transaction baru 
		String tId = generateTransactionId();
		Transaction.PurchaseItems(tId, userId, itemId);
		
		// Remove item yang telah dipurchase dari seluruh wishlist user
		Wishlist.DeleteWishlistByItemId(itemId);
		// Remove item dari database offers
	    Offer.DeleteOfferByItemId(itemId);
	}
	
	// Method wajib: Method untuck fetch semua transaction yang dilakukan oleh user yang sedang login.
	public ArrayList<Transaction> viewHistory(String userId) {
		return Transaction.ViewHistory(userId);
	}
	
	// Method wajib: Bagi buyer untuk membuat transaksi baru
	// Parameter ditambahkan userId dan itemId
	public void createTransaction(String transactionId, String userId, String itemId) {
		Transaction.CreateTransaction(transactionId, userId, itemId);
	}
	
	// Method tambahan untuk fetch semua transactions yang ada
	public ArrayList<Transaction> getAllTransactions(){
		return Transaction.GetAllTransactions();
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
