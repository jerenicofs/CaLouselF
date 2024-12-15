package model;

public class Offer_Log {
	private String offer_logId;	 //id offer_log
	private String userId;		 //id user yang offernya ditolak
	private String itemName;	 //nama item yang ditolak
	private String itemSize;	 //size item yang ditolak
	private String itemPrice;	 //price item yang ditolak
	private String itemCategory; //category item yang ditolak
	private String itemOfferPrice;//price yang ditawarkan untuk item
	private String reasonText;	 //alasan kenapa item ditolak (telah diisi admin)
	
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
	
	

}
