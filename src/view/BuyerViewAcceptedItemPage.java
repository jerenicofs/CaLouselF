package view;

import controller.ItemController;
import controller.WishlistController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Item;
import util.Session;

public class BuyerViewAcceptedItemPage implements EventHandler<ActionEvent>{
	private VBox root;
    public Scene scene;
    private Label nameLabel, sizeLabel, priceLabel, categoryLabel;
    private Button wishlistBtn, backBtn;
    private Item item;
    
    private WishlistController wc;
    private ItemController ic;

    public BuyerViewAcceptedItemPage(String itemId) {
        wc = new WishlistController();
        ic = new ItemController();
    	this.item = ic.viewAcceptedItem(itemId);
        init();
        setAlignment();
        setStyle();
        updateWishlistBtn();
        events();
        Main.redirect(scene);
    }

    private void init() {
        root = new VBox(15);
        scene = new Scene(root, 1200, 600);
        
        nameLabel = new Label("Name: " + item.getItemName());
        sizeLabel = new Label("Size: " + item.getItemSize());
        priceLabel = new Label("Price: " + item.getItemPrice());
        categoryLabel = new Label("Category: " + item.getItemCategory());

        wishlistBtn = new Button("Add to Wishlist");
        backBtn = new Button("Back");

    }
    
    private void setAlignment() {
    	root.getChildren().addAll(nameLabel, sizeLabel, priceLabel, categoryLabel, wishlistBtn, backBtn);
    	root.setAlignment(Pos.CENTER);
    	root.setPadding(new Insets(20));
    	
    }

    private void setStyle() {
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        sizeLabel.setStyle("-fx-font-size: 16px;");
        priceLabel.setStyle("-fx-font-size: 16px;");
        categoryLabel.setStyle("-fx-font-size: 16px;");
        wishlistBtn.setStyle("-fx-font-size: 14px;");
        backBtn.setStyle("-fx-font-size: 14px;");
    }

    private void events() {
        wishlistBtn.setOnAction(e -> handle(e));
        backBtn.setOnAction(e -> handle(e));
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == wishlistBtn) {
			// Kalau item belum ada di wishlist user
			if (wishlistBtn.getText().equals("Add to Wishlist")) {
	            wc.addWishlist(item.getItemId(), Session.getUser().getUserId());
	            showSuccess("Wishlist Added", "Item added to your wishlist!");
	        } 
			// Kalau item sudah di wishlist user
			else {
	        	String wishlistId = wc.getWishlistIdByItemIdAndUserId(item.getItemId(), Session.getUser().getUserId());
                wc.removeWishlist(wishlistId);
                showSuccess("Wishlist Removed", "Item removed from your wishlist!");
	            
	        }
	        updateWishlistBtn(); 
		}
		else if(event.getSource() == backBtn) {
			 Main.redirect(new HomePage().scene);
		}
		
	}
	
	private void updateWishlistBtn() {
	    if (wc.isItemInWishlist(item.getItemId(), Session.getUser().getUserId())) {
	        wishlistBtn.setText("Remove from Wishlist");
	    } else {
	        wishlistBtn.setText("Add to Wishlist");
	    }
	}

}
