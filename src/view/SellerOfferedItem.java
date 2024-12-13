package view;

import java.util.ArrayList;
import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Offer;
import util.Session;

public class SellerOfferedItem {
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private ItemController ic;
	private TableView<Offer> table;

	
	public SellerOfferedItem() {
		ic = new ItemController();
		init();
		event();
		setPosition();
		setStyle();
		Main.redirect(scene);
	}
	
	public void init() {
		root = new BorderPane();
		grid = new GridPane();
		scene = new Scene(root, 1200, 600);
		title = new Label("Seller Dashboard");
		pageDesc = new Label("Here are the items offered by Buyer");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No items are being offered");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();
	}
	
	@SuppressWarnings("unchecked")
	private void setupTable() {
	    TableColumn<Offer, String> itemNameCol = new TableColumn<>("Item Name");
	    itemNameCol.setCellFactory(col -> new TableCell<>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow() == null) {
	                setText(null);
	            } else {
	                Offer offer = getTableView().getItems().get(getIndex());
	                String itemName = ic.getItemNameById(offer.getItemId());
	                setText(itemName);
	            }
	        }
	    });
	    itemNameCol.setMinWidth(200);

	    TableColumn<Offer, String> itemCategoryCol = new TableColumn<>("Category");
	    itemCategoryCol.setCellFactory(col -> new TableCell<>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow() == null) {
	                setText(null);
	            } else {
	                Offer offer = getTableView().getItems().get(getIndex());
	                String itemCategory = ic.getItemCategoryById(offer.getItemId());
	                setText(itemCategory);
	            }
	        }
	    });
	    itemCategoryCol.setMinWidth(150);

	    TableColumn<Offer, String> itemSizeCol = new TableColumn<>("Size");
	    itemSizeCol.setCellFactory(col -> new TableCell<>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow() == null) {
	                setText(null);
	            } else {
	                Offer offer = getTableView().getItems().get(getIndex());
	                String itemSize = ic.getItemSizeById(offer.getItemId());
	                setText(itemSize);
	            }
	        }
	    });
	    itemSizeCol.setMinWidth(100);

	    TableColumn<Offer, String> itemPriceCol = new TableColumn<>("Initial Price");
	    itemPriceCol.setCellFactory(col -> new TableCell<>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || getTableRow() == null) {
	                setText(null);
	            } else {
	                Offer offer = getTableView().getItems().get(getIndex());
	                String itemPrice = ic.getItemPriceById(offer.getItemId());
	                setText(itemPrice);
	            }
	        }
	    });
	    itemPriceCol.setMinWidth(150);

	    TableColumn<Offer, String> offeredPriceCol = new TableColumn<>("Offered Price");
	    offeredPriceCol.setCellValueFactory(new PropertyValueFactory<>("offeredPrice"));
	    offeredPriceCol.setMinWidth(150);

	    table.getColumns().addAll(itemNameCol, itemCategoryCol, itemSizeCol, itemPriceCol, offeredPriceCol);
	}

	
	private void loadData() {
	    ArrayList<Offer> offers = ic.viewOfferItem(Session.getUser().getUserId());
	    
	    // Group item berdasarkan nama, lalu sort berdasarkan offer price 
	    offers.sort((o1, o2) -> {
	        
	    	// Sort berdasarkan nama
	        String itemName1 = ic.getItemNameById(o1.getItemId());
	        String itemName2 = ic.getItemNameById(o2.getItemId());
	        int nameComparison = itemName1.compareToIgnoreCase(itemName2);

	        if (nameComparison != 0) {
	            return nameComparison;
	        }

	        // Jika namanya sama, maka sort berdasarkan harga
	        int price1 = Integer.parseInt(o1.getOfferedPrice());
	        int price2 = Integer.parseInt(o2.getOfferedPrice());
	        return Integer.compare(price1, price2);
	    });

	    table.getItems().setAll(offers);
	}


	
	public void setPosition() {
		root.setTop(title);
		root.setLeft(grid);

		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPrefHeight(350);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		title.setAlignment(Pos.TOP_CENTER);
		grid.add(pageDesc, 0, 0);
		grid.add(backBtn, 0, 1);
		grid.add(table, 0, 2);
	}

	public void event() {
		backBtn.setOnAction(e -> handle(e));
	}

	public void handle(ActionEvent event) {
		if (event.getSource() == backBtn) {
			Main.redirect(new HomePage().scene);
		}
	}

	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}
	
	
}
