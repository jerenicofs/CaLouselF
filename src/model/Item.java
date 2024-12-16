package model;

public class Item {
	private String itemId; // unique id untuk setiap item
	private String itemName; // nama dari item
	private String itemSize; // ukuran dari item
	private String itemPrice; // harga item
	private String itemCategory; // kategori item
	private String itemStatus; // default: Pending
	private String itemWishlist; // default: null . Reference dari itemId terdapat di table wishlists
	private String itemOfferStatus; // default: null. Statusnya terdapat di table offers
	private String userId; // Foreign key, references users table. Menunjukkan user (seller) yang menjual item ini

	public Item(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory,
			String itemStatus, String itemWishlist, String itemOfferStatus, String userId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus;
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public void setItemWishlist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
