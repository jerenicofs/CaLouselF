package view;

import java.util.ArrayList;

import controller.ItemController;
import controller.TransactionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Item;
import util.Session;

public class HomePage implements EventHandler<ActionEvent> {

	private BorderPane frame;
	private HBox navbar;
	private VBox header;
	private TilePane container;
	private Label title;
	// General Buttons
	private Button logoutBtn;

	// Seller Buttons
	private Button itemManageBtn, uploadBtn, viewRejectedItemBtn, viewOfferedItemBtn;

	// Admin Buttons
	private Button requestManageBtn;

	// Buyer Buttons
	private Button wishlistBtn, viewHistoryBtn, viewOfferLog;

	private ItemController ic;
	private TransactionController tc;

	public Scene scene;

	public HomePage() {
		init();
		setAlignment();
		setStyle();
		events();
		loadItems();
		Main.redirect(scene);
	}

	public void init() {
		ic = new ItemController();
		tc = new TransactionController();

		frame = new BorderPane();
		title = new Label("Home Page");
		// General button initialization
		logoutBtn = new Button("Logout");

		// Seller button initialization
		itemManageBtn = new Button("Item Management Page");
		uploadBtn = new Button("Upload New Item");
		viewRejectedItemBtn = new Button("View Rejected Item");
		viewOfferedItemBtn = new Button("View Offered Item");

		// Admin button initialization
		requestManageBtn = new Button("Request Item Management Page");

		// Buyer button initialization
		wishlistBtn = new Button("View Wishlist");
		viewHistoryBtn = new Button("View Purchase History");
		viewOfferLog = new Button("View Your Offer Log");

		navbar = new HBox();
		header = new VBox();

		container = new TilePane();

		scene = new Scene(frame, 1200, 600);
	}

	public void setAlignment() {
		frame.setTop(header);
		frame.setCenter(container);

		navbar.getChildren().add(logoutBtn);
		if (Session.getUser() != null) {
			// Buyer yang login
			if (Session.getUser().getRole().equals("Buyer"))
				navbar.getChildren().addAll(wishlistBtn, viewHistoryBtn, viewOfferLog);

			// Seller yang login
			else if (Session.getUser().getRole().equals("Seller"))
				navbar.getChildren().addAll(itemManageBtn, uploadBtn, viewRejectedItemBtn, viewOfferedItemBtn);

			
		}// Admin yang login
		else{
			
			navbar.getChildren().add(requestManageBtn);
		}
		
		navbar.setSpacing(10);
		navbar.setAlignment(Pos.CENTER);

		header.getChildren().addAll(title, navbar);
		header.setAlignment(Pos.CENTER);
		header.setSpacing(15);
		header.setPadding(new Insets(20));

		container.setHgap(20);
		container.setVgap(20);
		container.setPadding(new Insets(20));
		container.setAlignment(Pos.CENTER);

	}

	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}

	public void events() {
		// General Event
		logoutBtn.setOnAction(e -> handle(e));

		// Admin Event
		requestManageBtn.setOnAction(e -> handle(e));

		// Seller Event
		viewRejectedItemBtn.setOnAction(e -> handle(e));
		viewOfferedItemBtn.setOnAction(e -> handle(e));
		itemManageBtn.setOnAction(e -> handle(e));
		uploadBtn.setOnAction(e -> handle(e));

		// Buyer Event
		wishlistBtn.setOnAction(e -> handle(e));
		viewHistoryBtn.setOnAction(e -> handle(e));
		viewOfferLog.setOnAction(e ->handle(e));
	}

	@Override
	public void handle(ActionEvent event) {
		// General Event
		if (event.getSource() == logoutBtn) {
			// Log Out
			System.out.println("Log Out");
			Session.clearSession();
			Main.redirect(new LoginPage().scene);
		}

		// Buyer Event
		if(event.getSource() == wishlistBtn) {
			Main.redirect(new BuyerViewWishlistPage().scene);
		}
		if(event.getSource() == viewHistoryBtn) {
			Main.redirect(new BuyerViewPurchaseHistoryPage().scene);
		}
		if(event.getSource() == viewOfferLog) {
			Main.redirect(new BuyerViewOfferLog().scene);
		}
		
		// Admin Event
		if (event.getSource() == requestManageBtn) {
			// Admin Manage Pending Item
			System.out.println("Go to Admin Approval Page");
			Main.redirect(new AdminViewRequestedItemPage().scene);
		}

		// Seller Event
		if (event.getSource() == itemManageBtn) {
			// Seller Edit Item
			System.out.println("Go to Edit Item Page");
			Main.redirect(new SellerItemsPage().scene);

		}
		if (event.getSource() == viewRejectedItemBtn) {
			// Seller View Rejected Item
			System.out.println("Go to Seller Rejected Item Page");
			Main.redirect(new SellerRejectedItemPage().scene);
		}
		if (event.getSource() == viewOfferedItemBtn) {
			// Seller View Offered Item
			System.out.println("Go to Seller Offered Item Page");
			Main.redirect(new SellerOfferedItemPage().scene);
		}
	 	if(event.getSource() == uploadBtn) {
    		System.out.println("Upload Button Terpencet");
    		Main.redirect(new SellerUploadItemPage().scene);
    	}
	}

	// Method untuk menampilkan item dan membuat satu card untuk setiap item
	public void loadItems() {
		ArrayList<Item> items = ic.viewItem();

		if (items.isEmpty())
			System.out.println("NO ITEM");

		for (Item item : items) {
			VBox card = createItemCard(item);
			container.getChildren().add(card);
		}
	}

	// Method untuk membuat card untuk setiap item yang ada
	public VBox createItemCard(Item item) {
		VBox card = new VBox(10);

		Label nameLabel = new Label("Name: " + item.getItemName());
		Label sizeLabel = new Label("Size: " + item.getItemSize());
		Label priceLabel = new Label("Price: " + item.getItemPrice());
		Label categoryLabel = new Label("Category: " + item.getItemCategory());

		card.setPadding(new Insets(15));
		card.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10px; -fx-background-color: #f9f9f9; -fx-background-radius: 10px;");
		card.setAlignment(Pos.CENTER_LEFT);
		card.setPrefHeight(200);
		card.setFillWidth(false);

		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		sizeLabel.setStyle("-fx-font-size: 14px;");
		priceLabel.setStyle("-fx-font-size: 14px;");
		categoryLabel.setStyle("-fx-font-size: 14px;");

		card.getChildren().addAll(nameLabel, sizeLabel, priceLabel, categoryLabel);

		// Tambah tombol "Make Offer", "Purchase", dan "Add to WishList" jika user adalah Buyer
		if (Session.getUser() != null && Session.getUser().getRole().equals("Buyer")) {
			Button makeOfferBtn = new Button("Make Offer");
			Button purchaseBtn = new Button("Purchase");
			HBox buttons = new HBox(2);
			card.setPadding(new Insets(10));
			buttons.getChildren().addAll(purchaseBtn, makeOfferBtn);
			
			makeOfferBtn.setOnAction(e -> {
				Main.redirect(new BuyerMakeOfferPage(item).scene);
			});
			
			purchaseBtn.setOnAction(e -> showPurchaseConfirmation(item));
			
			
			card.getChildren().add(buttons);
			
			card.setOnMouseEntered(event -> card.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10px; -fx-background-color: #f0f0f0; -fx-background-radius: 10px; -fx-cursor: hand;"));
	        card.setOnMouseExited(event -> card.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10px; -fx-background-color: #f9f9f9; -fx-background-radius: 10px;"));
		}
		
		card.setOnMouseClicked(event -> {
	        if (Session.getUser() != null && Session.getUser().getRole().equals("Buyer")) {
	            Main.redirect(new BuyerViewAcceptedItemPage(item.getItemId()).scene);
	        }
	    });

		return card;
	}
	
	private void showPurchaseConfirmation(Item item) {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
	        "Are you sure you want to purchase this item?", 
	        javafx.scene.control.ButtonType.YES, 
	        javafx.scene.control.ButtonType.NO);
	    
	    alert.setTitle("Confirm Purchase");
	    alert.setHeaderText(null);

	    alert.showAndWait().ifPresent(response -> {
	        if (response == javafx.scene.control.ButtonType.YES) {
	            tc.purchaseItems(Session.getUser().getUserId(), item.getItemId());
	            showSuccess("Purchase Successful", "You have successfully purchased the item!");
	            refreshCard();
	        }
	    });
	}
	
	private void refreshCard() {
	    container.getChildren().clear(); 
	    loadItems();                     
	}
	
	public void showSuccess(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}
