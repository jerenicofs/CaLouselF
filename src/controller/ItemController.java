package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.DatabaseConnection;
import model.Item;
import model.Offer;
import model.Offer_Log;
import model.Reason_log;
import model.Transaction;
import util.Session;

public class ItemController {

	private DatabaseConnection db = DatabaseConnection.getInstance();

	// Method untuk fetch semua items yang sudah di accept/approve admin di database
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		String query = "SELECT * FROM items WHERE itemStatus = 'Approved'";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			db.rs = prepQuery.executeQuery();

			while (db.rs != null && db.rs.next()) {
				items.add(new Item(db.rs.getString("itemId"), db.rs.getString("itemName"), db.rs.getString("itemSize"),
						db.rs.getString("itemPrice"), db.rs.getString("itemCategory"), db.rs.getString("itemStatus"),
						db.rs.getString("itemWishlist"), db.rs.getString("itemOfferStatus"),
						db.rs.getString("userId")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	// Method untuk fetch semua items berdasarkan user yang logged in
	public ArrayList<Item> getAllItemsbyUser() {
		ArrayList<Item> items = new ArrayList<>();

		String query = "SELECT * FROM items WHERE itemStatus = 'Approved' AND userId = ?";

		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, Session.getUser().getUserId());
			db.rs = prepQuery.executeQuery();

			while (db.rs != null && db.rs.next()) {
				items.add(new Item(db.rs.getString("itemId"), db.rs.getString("itemName"), db.rs.getString("itemSize"),
						db.rs.getString("itemPrice"), db.rs.getString("itemCategory"), db.rs.getString("itemStatus"),
						db.rs.getString("itemWishlist"), db.rs.getString("itemOfferStatus"),
						db.rs.getString("userId")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	// Method untuk fetch semua reason_log untuk suatu user
	public ArrayList<Reason_log> getAllReason_Log() {
		ArrayList<Reason_log> reasons = new ArrayList<>();
		String query = "SELECT * FROM reason_logs WHERE userId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, Session.getUser().getUserId());
			db.rs = prepQuery.executeQuery();

			while (db.rs != null && db.rs.next()) {
				reasons.add(new Reason_log(db.rs.getString("reason_logId"), db.rs.getString("userId"),
						db.rs.getString("itemName"), db.rs.getString("itemSize"), db.rs.getString("itemPrice"),
						db.rs.getString("itemCategory"), db.rs.getString("reasonText")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reasons;
	}

	// Method tambahan untuk fetch semua offers yang ada
	public ArrayList<Offer> getAllOffers() {
		ArrayList<Offer> offers = new ArrayList<>();

		String query = "SELECT * from offers";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				offers.add(new Offer(db.rs.getString("offerId"), db.rs.getString("itemId"), db.rs.getString("userId"),
						db.rs.getString("offeredPrice"), db.rs.getString("status")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return offers;
	}

	// Method tambahan untuk fetch semua transactions yang ada
	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> transactions = new ArrayList<>();

		String query = "SELECT * from transactions";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				transactions.add(new Transaction(db.rs.getString("userId"), db.rs.getString("itemId"),
						db.rs.getString("transactionId")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return transactions;
	}

	// Method wajib: Method bagi seller untuk upload barang baru
	public void uploadItem(String name, String cat, String size, String price) {
		String query = "INSERT INTO items (itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus, userId)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		String id = generateId();
		try {
			psQuery.setString(1, id);
			psQuery.setString(2, name);
			psQuery.setString(3, size);
			psQuery.setString(4, price);
			psQuery.setString(5, cat);
			psQuery.setString(6, "Pending");
			psQuery.setString(7, null);
			psQuery.setString(8, null);
			psQuery.setString(9, Session.getUser().getUserId());
			psQuery.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Method wajib: Method bagi seller untuk melalukan edit/update item yang sudah
	// diaccept oleh admin
	public void editItem(String itemId, String name, String category, String size, String price) {
		String query = "UPDATE items SET itemName = ?, itemCategory = ?, itemSize = ?, itemPrice = ? WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, size);
			ps.setString(4, price);
			ps.setString(5, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: Method untuk menghapus item yang sudah di accept
	public void deleteItem(String itemId) {
		String query = "DELETE FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method Tambahan buat generate random unique itemId
	public String generateId() {
		String id = "";
		ArrayList<Item> items = new ArrayList<>();
		items = getAllItemsbyUser();

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
		return getAllItems();
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

		return 1;
	}

	// Method wajib: Method untuk fetch semua items yang menunggu di approve admin
	// (berstatus pending)
	// Note: Parameter id dihilangkan karena tidak diperlukan.
	public ArrayList<Item> viewRequestedItem(String itemStatus) {
		ArrayList<Item> items = new ArrayList<>();
		String query = "SELECT * FROM items WHERE itemStatus = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, itemStatus);
			db.rs = prepQuery.executeQuery();
			while (db.rs != null && db.rs.next()) {
				items.add(new Item(db.rs.getString("itemId"), db.rs.getString("itemName"), db.rs.getString("itemSize"),
						db.rs.getString("itemPrice"), db.rs.getString("itemCategory"), db.rs.getString("itemStatus"),
						db.rs.getString("itemWishlist"), db.rs.getString("itemOfferStatus"),
						db.rs.getString("userId")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	// Method tambahan: Untuk fetch semua offerlog dari user (buyer) yang login.
	public ArrayList<Offer_Log> getAllOfferLog(String userId) {
		ArrayList<Offer_Log> offerLog = new ArrayList<>();
		String query = "SELECT * FROM offer_logs WHERE userId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, userId);
			db.rs = prepQuery.executeQuery();
			while (db.rs != null && db.rs.next()) {
				offerLog.add(new Offer_Log(db.rs.getString("offer_logId"), db.rs.getString("userId"),
						db.rs.getString("itemName"), db.rs.getString("itemSize"), db.rs.getString("itemPrice"),
						db.rs.getString("itemCategory"), db.rs.getString("itemOfferPrice"),
						db.rs.getString("reasonText")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return offerLog;
	}

	// Method wajib: Buyer bisa melakukan offer price baru
	public void offerPrice(String itemId, String itemPrice) {
		String query = "Insert into offers (offerId, itemId, userId, offeredPrice, status)" + "VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = db.prepareStatement(query);
		String id = generateOfferId();

		try {
			ps.setString(1, id);
			ps.setString(2, itemId);
			ps.setString(3, Session.getUser().getUserId());
			ps.setString(4, itemPrice);
			ps.setString(5, "Pending");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Method tambahan untuk mendapatkan highest offer terakhir dari sebuah item
	public double getHighestOffer(String itemId) {
		double highestOffer = 0.0;
		String query = "SELECT MAX(offeredPrice) AS highestOffer FROM offers WHERE itemId = ?";

		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, itemId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				highestOffer = rs.getDouble("highestOffer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return highestOffer;
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

		offerPrice(itemId, itemPrice);

		return 1;
	}

	// Method wajib: Method bagi admin untuk melakukan accept terhadap offer yang
	// ada
	// Ditambahkan parameter userId untuk mendapatkan id dari buyer yang melakukan
	// offer
	public void acceptOffer(String userId, String itemId) {

		// Setelah offer di accept, buat transaksi baru
		String query = "INSERT INTO transactions (userId, itemId, transactionId)" + "VALUES(?, ?, ?)";
		String tId = generateTransactionId();
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, userId);
			ps.setString(2, itemId);
			ps.setString(3, tId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Remove item dari database offers
		deleteOfferByItemId(itemId);

		// Remove item dari database whishlists
		deletewishlistByItemId(itemId);
	}

	// Method tambahan untuk delete items berdasarkan itemId
	public void deleteItemByItemId(String itemId) {
		String delQuery = "DELETE FROM items WHERE itemId = ?";
		PreparedStatement dps = db.prepareStatement(delQuery);
		try {
			dps.setString(1, itemId);
			dps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk delete offer berdasarkan itemId
	public void deleteOfferByItemId(String itemId) {
		String offQuery = "DELETE FROM offers WHERE itemId = ?";
		PreparedStatement ops = db.prepareStatement(offQuery);
		try {
			ops.setString(1, itemId);
			ops.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk delete wishlist berdasarkan itemId
	public void deletewishlistByItemId(String itemId) {
		String wQuery = "DELETE FROM wishlists WHERE itemId = ?";
		PreparedStatement wps = db.prepareStatement(wQuery);
		try {
			wps.setString(1, itemId);
			wps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk generate unique transaction id 
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

	// Method wajib: Bagi seller untuk melakukan decline offer
	public void declineOffer(String itemId) {

		// Remove item dari database offers
		deleteOfferByItemId(itemId);

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
		String query = "UPDATE items SET itemStatus = 'Approved' WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: untuk mendecline (reject) item oleh admin
	// Notes: Parameternya diganti dengan objek Item dan text validasi
	public int declineItem(Item item, String text) {
		if (text.isBlank()) {
			return -1;
		}
		addReason_Log(item, text);
		deleteItem(item.getItemId());
		return 1;
	}

	// Method wajib: Bagi buyer untuk fetch item berdasarkan itemId
	public Item viewAcceptedItem(String itemId) {
		String query = "SELECT * FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		Item item = null;
		try {
			ps.setString(1, itemId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item = new Item(rs.getString("itemId"), rs.getString("itemName"), rs.getString("itemSize"),
						rs.getString("itemPrice"), rs.getString("itemCategory"), rs.getString("itemStatus"),
						rs.getString("itemWishlist"), rs.getString("itemOfferStatus"), rs.getString("userId"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	// Method wajib: Method untuk seller untuk melihat semua offer price request
	// dari buyer
	public ArrayList<Offer> viewOfferItem(String userId) {
		ArrayList<Offer> offers = new ArrayList<>();
		ArrayList<String> items = new ArrayList<>();

		// Step 1: Fetch all itemIds that belong to the given userId
		String itemQuery = "SELECT itemId FROM items WHERE userId = ?";
		PreparedStatement ps = db.prepareStatement(itemQuery);

		// Fetch semua item milik seller yang login
		try {
			ps.setString(1, userId);
			db.rs = ps.executeQuery();

			while (db.rs != null && db.rs.next()) {
				items.add(db.rs.getString("itemId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return offers;
		}

		// Fetch item milik seller yang ada offer dari buyer
		if (!items.isEmpty()) {
			StringBuilder placeholders = new StringBuilder();
			for (int i = 0; i < items.size(); i++) {
				placeholders.append("?");
				if (i < items.size() - 1) {
					placeholders.append(", ");
				}
			}

			String offerQuery = "SELECT * FROM offers WHERE itemId IN (" + placeholders + ")";
			PreparedStatement psOffer = db.prepareStatement(offerQuery);

			try {
				for (int i = 0; i < items.size(); i++) {
					psOffer.setString(i + 1, items.get(i));
				}

				db.rs = psOffer.executeQuery();

				while (db.rs != null && db.rs.next()) {
					offers.add(new Offer(db.rs.getString("offerId"), db.rs.getString("itemId"),
							db.rs.getString("userId"), db.rs.getString("offeredPrice"), db.rs.getString("status")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return offers;
	}

	// Method tambahan untuk fetch itemName berdasarkan itemId
	public String getItemNameById(String itemId) {
		String query = "SELECT itemName FROM items WHERE itemId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, itemId);
			db.rs = prepQuery.executeQuery();
			if (db.rs.next()) {
				return db.rs.getString("itemName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Method tambahan untuk fetch itemCategory berdasarkan itemId
	public String getItemCategoryById(String itemId) {
		String query = "SELECT itemCategory FROM items WHERE itemId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, itemId);
			db.rs = prepQuery.executeQuery();
			if (db.rs.next()) {
				return db.rs.getString("itemCategory");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Method tambahan untuk fetch itemSize berdasarkan itemId
	public String getItemSizeById(String itemId) {
		String query = "SELECT itemSize FROM items WHERE itemId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, itemId);
			db.rs = prepQuery.executeQuery();
			if (db.rs.next()) {
				return db.rs.getString("itemSize");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Method tambahan untuk fetch itemPrice berdasarkan itemId
	public String getItemPriceById(String itemId) {
		String query = "SELECT itemPrice FROM items WHERE itemId = ?";
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			prepQuery.setString(1, itemId);
			db.rs = prepQuery.executeQuery();
			if (db.rs.next()) {
				return db.rs.getString("itemPrice");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Method tambahan untuk dapet reason_logId terakhir
	public String getLastReasonLogId() {
		String query = "SELECT reason_logId FROM reason_logs ORDER BY reason_logId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("reason_logId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "R001";

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
		String query = "INSERT INTO reason_logs (reason_logId, userId, itemName, itemSize, itemPrice, itemCategory, reasonText)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		String reason_LogId = generateNewReasonLogId();
		try {
			psQuery.setString(1, reason_LogId);
			psQuery.setString(2, i.getUserId());
			psQuery.setString(3, i.getItemName());
			psQuery.setString(4, i.getItemSize());
			psQuery.setString(5, i.getItemPrice());
			psQuery.setString(6, i.getItemCategory());
			psQuery.setString(7, reasonText);
			psQuery.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk menambahkan offer log baru 
	public void addOffer_Log(Offer o, String reasonText) {
		String query = "SELECT * FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		Item i = null;
		try {
			ps.setString(1, o.getItemId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				i = new Item(rs.getString("itemId"), rs.getString("itemName"), rs.getString("itemSize"),
						rs.getString("itemPrice"), rs.getString("itemCategory"), rs.getString("itemStatus"),
						rs.getString("itemWishlist"), rs.getString("itemOfferStatus"), rs.getString("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "INSERT INTO offer_logs (offer_logId, userId, itemName, itemSize, itemPrice, itemCategory, itemOfferPrice, reasonText)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
		String offer_LogId = generateNewOfferLogId();
		try {
			psQuery.setString(1, offer_LogId);
			psQuery.setString(2, o.getUserId());
			psQuery.setString(3, i.getItemName());
			psQuery.setString(4, i.getItemSize());
			psQuery.setString(5, i.getItemPrice());
			psQuery.setString(6, i.getItemCategory());
			psQuery.setString(7, o.getOfferedPrice());
			psQuery.setString(8, reasonText);
			psQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method tambahan untuk mendapatkan id offerlog terakhir, yang nantinya akan digunakan untuk mengenerate id baru
	public String getLastOfferLogId() {
		String query = "SELECT offer_logId FROM offer_logs ORDER BY offer_logId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("offer_logId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "OL000";

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
