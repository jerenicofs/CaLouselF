package model;

public class Offer {
	//GPT bilang tambah atributnya object Item item;
	private String offerId;
	private String itemId;
	private String userId;
	private String curentPrice;
	private String offeredPrice;
	
	public Offer(String offerId, String itemId, String userId, String curentPrice, String offeredPrice) {
		super();
		this.offerId = offerId;
		this.itemId = itemId;
		this.userId = userId;
		this.curentPrice = curentPrice;
		this.offeredPrice = offeredPrice;
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

	public String getCurentPrice() {
		return curentPrice;
	}

	public void setCurentPrice(String curentPrice) {
		this.curentPrice = curentPrice;
	}

	public String getOfferedPrice() {
		return offeredPrice;
	}

	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	
	
	
	
	

}
