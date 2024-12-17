package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import util.Session;

public class Item {
	private String itemId; // unique id untuk setiap item
	private String itemName; // nama dari item
	private String itemSize; // ukuran dari item
	private String itemPrice; // harga item
	private String itemCategory; // kategori item
	private String itemStatus; // default: Pending
	private String itemWishlist; // default: null . Reference dari itemId terdapat di table wishlists
	private String itemOfferStatus; // default: null. Statusnya terdapat di table offers
	private String userId; // Foreign key, references users table. Menunjukkan user (seller) yang menjual
							// item ini
	private static DatabaseConnection db = DatabaseConnection.getInstance();

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

	// Method wajib: Method bagi seller untuk upload barang baru
	// Nb: Ditambahkan Parameter id yang sudah digenerate random di controller
	public static void UploadItem(String id, String name, String cat, String size, String price) {
		String query = "INSERT INTO items (itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus, userId)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement psQuery = db.prepareStatement(query);
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
	public static void EditItem(String itemId, String name, String category, String size, String price) {
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
	public static void DeleteItem(String itemId) {
		String query = "DELETE FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: Tidak terdapat di deskripsi soal word, namun ada di class
	// diagram
	public static void BrowseItem(String itemName) {

	}

	// Method wajib: Method untuk menampilkan semua item yang sudah diaccept oleh
	// admin
	public static ArrayList<Item> ViewItem() {
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

	// Method wajib: Method untuk validasi item yang di upload/edit oleh seller
	// Nb: Validasi sudah dilakukan di controller
	public static void CheckItemValidation(String name, String category, String size, String price) {

	}

	// Method wajib: Method untuk fetch semua items yang menunggu di approve admin
	// (berstatus pending)
	// Note: Parameter id dihilangkan karena tidak diperlukan.
	public static ArrayList<Item> ViewRequestedItem(String itemStatus) {
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

	// Method wajib: Buyer bisa melakukan offer price baru
	// Nb: Ditambahkan parameter id yaitu id baru yang sudah digenerate di controller
	public static void OfferPrice(String id, String itemId, String itemPrice) {
		String query = "Insert into offers (offerId, itemId, userId, offeredPrice, status)" + "VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = db.prepareStatement(query);

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

	// Method wajib: Method bagi admin untuk melakukan accept terhadap offer yang
	// ada
	// Ditambahkan parameter userId untuk mendapatkan id dari buyer yang melakukan
	// offer
	public static void AcceptOffer(String tId, String userId, String itemId) {

		// Setelah offer di accept, buat transaksi baru
		String query = "INSERT INTO transactions (userId, itemId, transactionId)" + "VALUES(?, ?, ?)";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, userId);
			ps.setString(2, itemId);
			ps.setString(3, tId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: Bagi seller untuk melakukan decline offer
	public static void DeclineOffer(String itemId) {
		String query = "DELETE FROM offers WHERE itemId = ?";
		PreparedStatement ops = db.prepareStatement(query);
		try {
			ops.setString(1, itemId);
			ops.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: untuk meng-approve item oleh admin
	public static void ApproveItem(String itemId) {
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
	public static void DeclineItem(String itemId) {
		String query = "DELETE FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);

		try {
			ps.setString(1, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method wajib: Bagi buyer untuk fetch item berdasarkan itemId
	public static Item ViewAcceptedItem(String itemId) {
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
	public static ArrayList<Offer> ViewOfferItem(String userId) {
		ArrayList<Offer> offers = new ArrayList<>();
		ArrayList<String> items = new ArrayList<>();

		// Step 1: Fetch semua itemId yang menjadi milik user yang logged in
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

	// Method tambahan untuk fetch semua items berdasarkan user yang logged in
	public static ArrayList<Item> GetAllItemsbyUser() {
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

	// Method tambahan untuk fetch itemName berdasarkan itemId
	public static String GetItemNameById(String itemId) {
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
	public static String GetItemCategoryById(String itemId) {
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
	public static String GetItemSizeById(String itemId) {
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
	public static String GetItemPriceById(String itemId) {
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

}
