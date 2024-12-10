package model;

public class Reason_log {
	private String reason_logId; //id reason_log
	private String userId;		 //id user yang barangnya ditolak
	private String itemName;	 //nama item yang ditolak
	private String itemSize;	 //size item yang ditolak
	private String itemPrice;	 //price item yang ditolak
	private String itemCategory; //category item yang ditolak
	private String reasonText;	 //alasan kenapa item ditolak (telah diisi admin)
	
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
	
	
	
	
}
