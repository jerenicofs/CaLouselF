package controller;

import java.util.ArrayList;
import java.util.Random;
import model.Item;
import model.Offer;
import model.Offer_Log;
import model.Reason_log;
import model.Transaction;
import model.Wishlist;

public class ItemController {

	// Method tambahan untuk fetch semua items berdasarkan user yang logged in
	public ArrayList<Item> getAllItemsbyUser(){
		return Item.GetAllItemsbyUser();
	}
	
	// Method tambahan untuk fetch semua reason_log untuk suatu user
	public ArrayList<Reason_log> getAllReason_Log() {
		return Reason_log.GetAllReason_Log();
	}

	// Method tambahan untuk fetch semua offers yang ada
	public ArrayList<Offer> getAllOffers() {
		return Offer.GetAllOffers();
	}

	// Method wajib: Method bagi seller untuk upload barang baru
	public void uploadItem(String name, String cat, String size, String price) {
		String id = generateId();
		Item.UploadItem(id, name, cat, size, price);
	}

	// Method wajib: Method bagi seller untuk melalukan edit/update item yang sudah
	// diaccept oleh admin
	public void editItem(String itemId, String name, String category, String size, String price) {
		Item.EditItem(itemId, name, category, size, price);
	}

	// Method wajib: Method untuk menghapus item yang sudah di accept
	public void deleteItem(String itemId) {
		Item.DeleteItem(itemId);
	}

	// Method Tambahan buat generate random unique itemId
	public String generateId() {
		String id = "";
		ArrayList<Item> items = getAllItemsbyUser();

		Random rand = new Random();
		boolean isUnique = true;
		while (true) {
			int rand1 = rand.nextInt(9) + 1;
			int rand2 = rand.nextInt(10);
			int rand3 = rand.nextInt(10);
			id = String.format("I%d%d%d", rand1, rand2, rand3);

			for (Item x : items) {
				if (x.getItemId().equals(id)) {
					isUnique = false;
					break;
				}
			}
			if (isUnique)
				break;

		}
		return id;
	}

	// Method tambahan untuk generate random unique offerId
	public String generateOfferId() {
		String id = "";
		ArrayList<Offer> offers = new ArrayList<>();
		offers = getAllOffers();

		Random rand = new Random();
		boolean isUnique = true;
		while (true) {
			int rand1 = rand.nextInt(9) + 1;
			int rand2 = rand.nextInt(10);
			int rand3 = rand.nextInt(10);
			id = String.format("O%d%d%d", rand1, rand2, rand3);

			for (Offer x : offers) {
				if (x.getOfferId().equals(id)) {
					isUnique = false;
					break;
				}
			}
			if (isUnique)
				break;

		}
		return id;
	}

	// Method wajib: Tidak terdapat di deskripsi soal word, namun ada di class
	// diagram
	public void browseItem(String itemName) {

	}

	// Method wajib: Method untuk menampilkan semua item yang sudah diaccept oleh
	// admin
	public ArrayList<Item> viewItem() {
		return Item.ViewItem();
	}

	// Method wajib: Method untuk validasi item yang di upload/edit oleh seller
	public double checkItemValidation(String name, String category, String size, String price) {

		// Validate No Blank
		if (name.isEmpty()) {
			return -1.1;
		} else if (category.isEmpty()) {
			return -2.1;
		} else if (size.isEmpty()) {
			return -3.1;
		} else if (price.isEmpty()) {
			return -4.1;
		}

		// Validate Name
		if (name.length() < 3) {
			return -1.2;
		}

		// Validate Category
		if (category.length() < 3) {
			return -2.2;
		}

		// Validate Price
		try {
			int temp = Integer.parseInt(price);
			if (temp < 1) {
				return -4.2;
			}
		} catch (NumberFormatException e) {
			return -4.3;
		}
		
		// Jika validasi terlewati, maka uploadItem akan dilakukan
		uploadItem(name, category, size, price);
		return 1;
	}

	// Method wajib: Method untuk fetch semua items yang menunggu di approve admin
	// (berstatus pending)
	// Note: Parameter id dihilangkan karena tidak diperlukan.
	public ArrayList<Item> viewRequestedItem(String itemStatus) {
		return Item.ViewRequestedItem(itemStatus);
	}

	// Method tambahan: Untuk fetch semua offerlog dari user (buyer) yang login.
	public ArrayList<Offer_Log> getAllOfferLog(String userId) {
		return Offer_Log.GetAllOfferLog(userId);
	}

	// Method wajib: Buyer bisa melakukan offer price baru
	public void offerPrice(String itemId, String itemPrice) {
		String id = generateOfferId();
		Item.OfferPrice(id, itemId, itemPrice);
	}

	// Method tambahan untuk mendapatkan highest offer terakhir dari sebuah item
	public double getHighestOffer(String itemId) {
		return Offer.GetHighestOffer(itemId);
	}

	// Method tambahan untuk validasi offer price;
	public int validateOfferPrice(String itemId, String itemPrice) {
		// Validasi offer price tidak boleh kosong
		if (itemPrice.isEmpty())
			return -1;

		// Validasi offer price harus lebih dari 0
		double offer;
		try {
			offer = Double.parseDouble(itemPrice);
		} catch (NumberFormatException e) {
			return -2;
		}

		if (offer < 1) {
			return -2;
		}

		// Validasi bahwa offer price harus lebih besar dari highest offer
		double highestOffer = getHighestOffer(itemId);
		if (offer <= highestOffer) {
			return -3;
		}
		
		// Setelah validasi terlewati, maka offerPrice akan dilakukan
		offerPrice(itemId, itemPrice);

		return 1;
	}

	// Method wajib: Method bagi admin untuk melakukan accept terhadap offer yang
	// ada
	// Ditambahkan parameter userId untuk mendapatkan id dari buyer yang melakukan
	// offer
	public void acceptOffer(String userId, String itemId) {

		// Setelah offer di accept, buat transaksi baru
		String tId = generateTransactionId();
		Item.AcceptOffer(tId, userId, itemId);
		
		// Remove item dari database offers
		Offer.DeleteOfferByItemId(itemId);

		// Remove item dari database whishlists
		Wishlist.DeleteWishlistByItemId(itemId);
	}

	// Method tambahan untuk generate unique transaction id 
	public String generateTransactionId() {
		String id = "";
		ArrayList<Transaction> transactions = Transaction.GetAllTransactions();

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

	// Method wajib: Bagi seller untuk melakukan decline offer
	public void declineOffer(String itemId) {
		// Remove item dari database offers
		Item.DeclineOffer(itemId);
	}

	// Method tambahan bagi seller untuk validasi decline reason
	public int validateDecline(Offer o, String reason, String itemId) {
		if (reason.isEmpty())
			return -1;

		// Kalau validasi berhasil terpenuhi
		addOffer_Log(o, reason);
		declineOffer(itemId);

		return 1;
	}

	// Method wajib: untuk meng-approve item oleh admin
	public void approveItem(String itemId) {
		Item.ApproveItem(itemId);
	}

	// Method wajib: untuk mendecline (reject) item oleh admin
	// Notes: Parameternya diganti dengan objek Item dan text validasi
	public int declineItem(Item item, String text) {
		if (text.isBlank()) {
			return -1;
		}
		addReason_Log(item, text);
		// Delete item yang didecline
		Item.DeclineItem(item.getItemId());
		return 1;
	}

	// Method wajib: Bagi buyer untuk fetch item berdasarkan itemId
	public Item viewAcceptedItem(String itemId) {
		return Item.ViewAcceptedItem(itemId);
	}

	// Method wajib: Method untuk seller untuk melihat semua offer price request
	// dari buyer
	public ArrayList<Offer> viewOfferItem(String userId) {
		return Item.ViewOfferItem(userId);
	}

	// Method tambahan untuk fetch itemName berdasarkan itemId
	public String getItemNameById(String itemId) {
		return Item.GetItemNameById(itemId);
	}

	// Method tambahan untuk fetch itemCategory berdasarkan itemId
	public String getItemCategoryById(String itemId) {
		return Item.GetItemCategoryById(itemId);
	}

	// Method tambahan untuk fetch itemSize berdasarkan itemId
	public String getItemSizeById(String itemId) {
		return Item.GetItemSizeById(itemId);
	}

	// Method tambahan untuk fetch itemPrice berdasarkan itemId
	public String getItemPriceById(String itemId) {
		return Item.GetItemPriceById(itemId);
	}

	// Method tambahan untuk dapet reason_logId terakhir
	public String getLastReasonLogId() {
		return Reason_log.GetLastReasonLogId();
	}

	// Method tambahan untuk generate reason_logId baru
	public String generateNewReasonLogId() {
		String lastId = getLastReasonLogId();
		String numberId = lastId.substring(1);
		try {
			int num = Integer.parseInt(numberId);
			num++;
			return String.format("R%03d", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "R001";
	}

	// Method tambahan untuk buat reason_log baru
	public void addReason_Log(Item i, String reasonText) {
		String reason_LogId = generateNewReasonLogId();
		Reason_log.AddReason_Log(reason_LogId, i, reasonText);
	}

	// Method tambahan untuk menambahkan offer log baru 
	public void addOffer_Log(Offer o, String reasonText) {
		String offer_LogId = generateNewOfferLogId();
		Offer_Log.AddOffer_Log(offer_LogId, o, reasonText);
	}

	// Method tambahan untuk mendapatkan id offerlog terakhir, yang nantinya akan digunakan untuk mengenerate id baru
	public String getLastOfferLogId() {
		return Offer_Log.GetLastOfferLogId();
	}
	
	// Method tambahan untuk mengenerate offerlogId baru
	public String generateNewOfferLogId() {
		String lastId = getLastOfferLogId();
		String numberId = lastId.substring(2);
		try {
			int num = Integer.parseInt(numberId);
			num++;
			return String.format("OL%03d", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OL001";
	}

}
