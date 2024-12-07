package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.User;

public class ItemController {
	
	private DatabaseConnection db = DatabaseConnection.getInstance();
	
	//Method untuk ambil semua items
	public ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<>();
		String query = "SELECT * FROM items";
		//query harus dibenerin (maunya item milik si user)
		PreparedStatement prepQuery = db.prepareStatement(query);
		try {
			db.rs = prepQuery.executeQuery();
			while(db.rs.next()) {
				items.add(new Item(db.rs.getString("itemId"), db.rs.getString("itemName"), db.rs.getString("itemSize"),
						db.rs.getString("itemPrice"), db.rs.getString("itemCategory"), db.rs.getString("itemStatus"), db.rs.getString("itemWishlist"),
						db.rs.getString("itemOfferStatus")));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
		
	}
	
	public void uploadItem() {
		
	}
	
	
	public void deleteItem(String itemId) {
        String query = "DELETE FROM items WHERE itemId = ?";
        PreparedStatement ps = db.prepareStatement(query);

        try {
            ps.setString(1, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getAllItems();
    }
	

}
