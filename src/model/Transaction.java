package model;

public class Transaction {
	private String userId; // Foreign key: references users (buyer) yang melakukan transaksi ini.
	private String itemId; // Foreign key: references item yang dibeli pada transaksi ini.
	private String transactionId; // Unique id untuk setiap transaction.
	
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
	
	
}
