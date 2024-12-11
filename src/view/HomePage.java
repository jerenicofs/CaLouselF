package view;

import java.util.ArrayList;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	private Button itemManageBtn, offerManageBtn, viewRejectedItemBtn, viewOfferedItemBtn;

	// Admin Buttons
	private Button requestManageBtn;

	// Buyer Buttons
	private Button purchaseBtn, makeOfferBtn, addToWishlistBtn, wishlistBtn;

	private ItemController ic;

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

		frame = new BorderPane();
		title = new Label("Home Page");
		// General button initialization
		logoutBtn = new Button("Logout");

		// Seller button initialization
		itemManageBtn = new Button("Item Management Page");
		offerManageBtn = new Button("Offer Management Page");
		viewRejectedItemBtn = new Button("View Rejected Item");
		viewOfferedItemBtn = new Button("View Offered Item");

		// Admin button initialization
		requestManageBtn = new Button("Request Item Management Page");

		// Buyer button initialization
		purchaseBtn = new Button("Purchase");
		makeOfferBtn = new Button("Make Offer");
		addToWishlistBtn = new Button("Add to wishlist");
		wishlistBtn = new Button("View Wishlist");

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
				navbar.getChildren().add(wishlistBtn);

			// Seller yang login
			else if (Session.getUser().getRole().equals("Seller"))
				navbar.getChildren().addAll(itemManageBtn, offerManageBtn, viewRejectedItemBtn, viewOfferedItemBtn);

			
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

		// Buyer Event
		makeOfferBtn.setOnAction(e -> handle(e));
		purchaseBtn.setOnAction(e -> handle(e));
		addToWishlistBtn.setOnAction(e -> handle(e));

	}

	@Override
	public void handle(ActionEvent event) {
		// General Event
		if (event.getSource() == logoutBtn) {
			// Log Out
			System.out.println("Log Out");
			Main.redirect(new LoginPage().scene);
		}

		// Admin Event
		if (event.getSource() == requestManageBtn) {
			// Admin Manage Pending Item
			System.out.println("Go to Admin Approval Page");
			Main.redirect(new AdminViewRequestedItemPage().scene);
		}

		// Buyer Event
		if (event.getSource() == makeOfferBtn) {
			// Buyer Make Offer for an Item
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
			Main.redirect(new SellerOfferedItem().scene);
		}
	}

	// Method untuk menampilkan item dan membuat satu card untuk setiap item
	public void loadItems() {
		ArrayList<Item> items = ic.getAllItems();

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
		card.setStyle(
				"-fx-border-color: lightgray; -fx-border-radius: 10px; -fx-background-color: #f9f9f9; -fx-background-radius: 10px;");
		card.setAlignment(Pos.CENTER_LEFT);
		card.setPrefHeight(200);
		card.setFillWidth(false);

		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		sizeLabel.setStyle("-fx-font-size: 14px;");
		priceLabel.setStyle("-fx-font-size: 14px;");
		categoryLabel.setStyle("-fx-font-size: 14px;");

		card.getChildren().addAll(nameLabel, sizeLabel, priceLabel, categoryLabel);

		// Tambah tombol "Make Offer" jika user adalah Buyer
		if (Session.getUser() != null && Session.getUser().getRole().equals("Buyer")) {
			Button makeOfferBtn = new Button("Make Offer");
			Button purchaseBtn = new Button("Purchase");
			Button addToWishlistBtn = new Button("Add to Wish List");
			HBox buttons = new HBox(2);
			card.setPadding(new Insets(10));
			buttons.getChildren().addAll(purchaseBtn, makeOfferBtn, addToWishlistBtn);

			card.getChildren().add(buttons);
		}

		return card;
	}

}
