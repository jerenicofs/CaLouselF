package model;

public class Wishlist {
	private String wishlistId;
	private String itemid;
	private String userId;
	
	public Wishlist(String wishlistId, String item_id, String user_id) {
		super();
		this.wishlistId = wishlistId;
		this.itemid = item_id;
		this.userId = user_id;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
	}

	public String getItem_id() {
		return itemid;
	}

	public void setItem_id(String item_id) {
		this.itemid = item_id;
	}

	public String getUser_id() {
		return userId;
	}

	public void setUser_id(String user_id) {
		this.userId = user_id;
	}
	
	
	
	
}
