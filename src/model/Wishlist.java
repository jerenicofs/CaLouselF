package model;

public class Wishlist {
	private String wishlistId; // Unique id untuk setiap wishlist.
	private String itemId; // Foreign key: references items yang ditandai sebagai wishlist oleh user.
	private String userId; // Foreign key: references users yang menandai sebuah item sebagai wishlist mereka.
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
	
	
	

}
