package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.DatabaseConnection;
import model.Item;
import util.Session;

public class ItemController {
	
	private DatabaseConnection db = DatabaseConnection.getInstance();
	
	
	// Method untul fetch semua items di database
	public ArrayList<Item> getAllItems(){
		 ArrayList<Item> items = new ArrayList<>();
		 String query = "SELECT * FROM items WHERE itemStatus = 'Accepted'";
		 
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
	    
	    String query = "SELECT * FROM items WHERE itemStatus = 'Accepted' AND userId = ?";

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
	

}
