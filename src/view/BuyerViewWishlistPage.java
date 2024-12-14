package view;

import java.util.ArrayList;
import java.util.function.Function;

import controller.ItemController;
import controller.WishlistController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Item;
import model.Wishlist;
import util.Session;

public class BuyerViewWishlistPage implements EventHandler<ActionEvent>{
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private WishlistController wc;
	private ItemController ic;
	private TableView<Wishlist> table;
	
	public BuyerViewWishlistPage() {
		wc = new WishlistController();
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
		title = new Label(Session.getUser().getUsername() + "'s wishlist");
		pageDesc = new Label("Here are your wishlist");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No wishlist");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();
	}
	

	@SuppressWarnings("unchecked")
	private void setupTable() {
	    TableColumn<Wishlist, String> itemNameCol = createColumn("Item Name", 200, Item::getItemName);
	    TableColumn<Wishlist, String> itemSizeCol = createColumn("Item Size", 100, Item::getItemSize);
	    TableColumn<Wishlist, String> itemPriceCol = createColumn("Item Price", 100, Item::getItemPrice);
	    TableColumn<Wishlist, String> itemCategoryCol = createColumn("Item Category", 150, Item::getItemCategory);

	    TableColumn<Wishlist, Void> actionCol = new TableColumn<>("Action");
	    actionCol.setMinWidth(150);
	    actionCol.setCellFactory(param -> new TableCell<>() {
	        Button removeBtn = new Button("Remove from Wishlist");

	        {
	            removeBtn.setOnAction(event -> {
	                Wishlist wishlist = getTableView().getItems().get(getIndex());
	                wc.removeWishlist(wishlist.getWishlistId());
	                getTableView().getItems().remove(wishlist);
	                showSuccess("Wishlist", "Item removed from your wishlist!");
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            setGraphic(empty ? null : new HBox(10, removeBtn));
	        }
	    });


	    table.getColumns().addAll(itemNameCol, itemSizeCol, itemPriceCol, itemCategoryCol, actionCol);
	}

	// Method untuk generate setiap kolom dari table
	private TableColumn<Wishlist, String> createColumn(String title, int width, Function<Item, String> e) {
	    TableColumn<Wishlist, String> column = new TableColumn<>(title);
	    column.setMinWidth(width);
	    column.setCellValueFactory(wishlist -> {
	        Item item = ic.viewAcceptedItem(wishlist.getValue().getItemId());
	        return new SimpleStringProperty(item != null ? e.apply(item) : "-");
	    });
	    return column;
	}

	
	private void loadData() {
	    ArrayList<Wishlist> w = wc.viewWishlist(Session.getUser().getUserId());
	    
	    table.getItems().setAll(w);
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
	
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}

	public void event() {
		backBtn.setOnAction(e -> handle(e));
	}
	
	@Override
	public void handle(ActionEvent event) {
		Main.redirect(new HomePage().scene);
	}
	
	public void showSuccess(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
