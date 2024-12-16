package model;

// Class tambahan untuk menyimpan offer yang dilakukan oleh buyer terhadap seller terhadap suatu item.
public class Offer {
	private String offerId; // Unique id untuk setiap offer yang dilakukan.
	private String itemId; // Foreign key: references items, menunjukkan item mana yang sedang dioffer oleh buyer.
	private String userId; // Foreign key: references users, menunjukkan user mana yang melakukan offer.
	private String offeredPrice; // Harga yang ditawar oleh buyer.
	private String status; // default: Pending.
	
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
	

}
