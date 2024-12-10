package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.DatabaseConnection;
import model.Item;
import model.Reason_log;
import util.Session;

public class ItemController {
	
	private DatabaseConnection db = DatabaseConnection.getInstance();
	
	
	// Method untuk fetch semua items yang sudah di accept/approve admin di database
	public ArrayList<Item> getAllItems(){
		 ArrayList<Item> items = new ArrayList<>();
		 String query = "SELECT * FROM items WHERE itemStatus = 'Approved'";
		 PreparedStatement prepQuery = db.prepareStatement(query);
		 try {
			 db.rs = prepQuery.executeQuery();

			 while (db.rs != null && db.rs.next()) {
				 	items.add(new Item(
	                db.rs.getString("itemId"),
	                db.rs.getString("itemName"),
	                db.rs.getString("itemSize"),
	                db.rs.getString("itemPrice"),
	                db.rs.getString("itemCategory"),
	                db.rs.getString("itemStatus"),
	                db.rs.getString("itemWishlist"),
	                db.rs.getString("itemOfferStatus"),
	                db.rs.getString("userId")
	            ));
	        }
		 } catch (SQLException e) {
	        e.printStackTrace();
		 }

		 return items;
	}
	//Method untuk fetch semua items yang menunggu di approve admin
	public ArrayList<Item> getAllPendingItems(){
		 ArrayList<Item> items = new ArrayList<>();
		 String query = "SELECT * FROM items WHERE itemStatus = 'Pending'";
		 PreparedStatement prepQuery = db.prepareStatement(query);
		 try {
			 db.rs = prepQuery.executeQuery();

			 while (db.rs != null && db.rs.next()) {
				 	items.add(new Item(
	                db.rs.getString("itemId"),
	                db.rs.getString("itemName"),
	                db.rs.getString("itemSize"),
	                db.rs.getString("itemPrice"),
	                db.rs.getString("itemCategory"),
	                db.rs.getString("itemStatus"),
	                db.rs.getString("itemWishlist"),
	                db.rs.getString("itemOfferStatus"),
	                db.rs.getString("userId")
	            ));
	        }
		 } catch (SQLException e) {
	        e.printStackTrace();
		 }

		 return items;
	}
	
	//Method untuk fetch semua items berdasarkan user yang logged in
	public ArrayList<Item> getAllItemsbyUser() {
	    ArrayList<Item> items = new ArrayList<>();
	    
	    String query = "SELECT * FROM items WHERE itemStatus = 'Approved' AND userId = ?";

	    PreparedStatement prepQuery = db.prepareStatement(query);
	    try {
	        prepQuery.setString(1, Session.getUser().getUserId());
	        db.rs = prepQuery.executeQuery();

	        while (db.rs != null && db.rs.next()) {
	            items.add(new Item(
	                db.rs.getString("itemId"),
	                db.rs.getString("itemName"),
	                db.rs.getString("itemSize"),
	                db.rs.getString("itemPrice"),
	                db.rs.getString("itemCategory"),
	                db.rs.getString("itemStatus"),
	                db.rs.getString("itemWishlist"),
	                db.rs.getString("itemOfferStatus"),
	                db.rs.getString("userId")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return items;
	}
	
	// Method untuk fetch semua reason_log untuk suatu user
	public ArrayList<Reason_log> getAllReason_Log(){
		 ArrayList<Reason_log> reasons = new ArrayList<>();
		 String query = "SELECT * FROM reason_logs WHERE userId = ?";
		 PreparedStatement prepQuery = db.prepareStatement(query);
		 try {
			 prepQuery.setString(1, Session.getUser().getUserId());
			 db.rs = prepQuery.executeQuery();

			 while (db.rs != null && db.rs.next()) {
				 	reasons.add(new Reason_log(
	                db.rs.getString("reason_logId"),
	                db.rs.getString("userId"),
	                db.rs.getString("itemName"),
	                db.rs.getString("itemSize"),
	                db.rs.getString("itemPrice"),
	                db.rs.getString("itemCategory"),
	                db.rs.getString("reasonText")
	            ));
	        }
		 } catch (SQLException e) {
	        e.printStackTrace();
		 }

		 return reasons;
	}
	
	// Method wajib: Method bagi seller untuk upload barang baru
	public void uploadItem(String name, String cat, String size, String price) {
		String query = "INSERT INTO items (itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus, userId)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
	
	// Method wajib: Method bagi seller untuk melalukan edit/update item yang sudah diaccept oleh admin
	public void editItem(String itemId, String name, String category, String size, String price) {
		
//		String query = String.format("UPDATE items SET itemName = '%s', itemCategory = '%s', itemSize = '%s', itemPrice = '%s' WHERE itemId = '%s'",
//				name, category, size, price, itemId);
		
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
	
	

	
	// Method wajib: Method untuk validasi item yang di upload/edit oleh seller
	public double checkItemValidation(String name, String category, String size, String price) {
		
		//Validate No Blank
		if(name.isEmpty()) {
			return -1.1;
		}else if(category.isEmpty()) {
			return -2.1;
		}else if(size.isEmpty()) {
			return -3.1;
		}else if(price.isEmpty()) {
			return -4.1;
		}
		
		//Validate Name
		if(name.length() < 3) {
			return -1.2;
		}
		
		//Validate Category
		if(category.length() < 3) {
			return -2.2;
		}
		
		//Validate Price
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
        getAllItemsbyUser();
    }
	
	//Method Tambahan buat  generate random unique itemId
	public String generateId() {
		String id ="";
		ArrayList<Item> items = new ArrayList<>();
		items = getAllItemsbyUser();
		
		Random rand = new Random();
		boolean isUnique = true;
		while(true) {
			int rand1 = rand.nextInt(9) + 1;
			int rand2 = rand.nextInt(10);
			int rand3 = rand.nextInt(10);
			id = String.format("I%d%d%d", rand1, rand2, rand3);
			
			for(Item x : items) {
				if(x.getItemId().equals(id)) {
					isUnique = false; break;
				}
			}
			if(isUnique) break;
			
		}
		return id;
	}
	
	//Method wajib: untuk meng-approve item oleh admin
	public void ApproveItem(String itemId) {
		String query = "UPDATE items SET itemStatus = 'Approved' WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Method wajib: untuk mendecline item oleh admin 
	public void DeclineItem(String itemId) {
		String query = "SELECT * FROM items WHERE itemId = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setString(1, itemId);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	
	//Method tambahan untuk dapet reason_logId terakhir
	public String getLastId() {
		String query = "SELECT reason_logId FROM reason_logs ORDER BY reason_logId DESC LIMIT 1";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("reason_logId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "R001";
		
		
	}
	
	//Method tambahan untuk generate reason_logId baru
	public String generateNewReasonLogId() {
		String lastId = getLastId();
		String numberId = lastId.substring(1);
		try {
			int num = Integer.parseInt(numberId);
			num++;
			return String.format("R%03d", num);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "R001";
	}
	
	//Method tambahan untuk validasi reason (tidak boleh blank)
	public int validateReason(String text) {
		if(text.isBlank()) {
			return -1;
		}
		return 1;
	}
	
	//Method tambahan untuk buat reason_log baru
	public void addReason_Log(Item i, String reasonText) {
		String query = "INSERT INTO reason_logs (reason_logId, userId, itemName, itemSize, itemPrice, itemCategory, reasonText)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
	

}
