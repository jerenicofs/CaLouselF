package view;

import java.util.ArrayList;

import controller.ItemController;
import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;
import model.Transaction;
import util.Session;

public class BuyerViewPurchaseHistoryPage implements EventHandler<ActionEvent> {
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private TransactionController tc;
	private ItemController ic;
	private TableView<Transaction> table;

	public BuyerViewPurchaseHistoryPage() {
		tc = new TransactionController();
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
		title = new Label(Session.getUser().getUsername() + "'s Purchase History");
		pageDesc = new Label("Here is your purchase history");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No purchase history found");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();
	}

	@SuppressWarnings("unchecked")
	private void setupTable() {
		TableColumn<Transaction, String> transactionIdCol = new TableColumn<>("Transaction ID");
		transactionIdCol.setMinWidth(150);
		transactionIdCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransactionId()));

		TableColumn<Transaction, String> itemNameCol = new TableColumn<>("Item Name");
		itemNameCol.setMinWidth(200);
		itemNameCol.setCellValueFactory(data -> {
			Item item = ic.viewAcceptedItem(data.getValue().getItemId());
			return new SimpleStringProperty(item != null ? item.getItemName() : "-");
		});

		TableColumn<Transaction, String> itemCategoryCol = new TableColumn<>("Category");
		itemCategoryCol.setMinWidth(150);
		itemCategoryCol.setCellValueFactory(data -> {
			Item item = ic.viewAcceptedItem(data.getValue().getItemId());
			return new SimpleStringProperty(item != null ? item.getItemCategory() : "-");
		});

		TableColumn<Transaction, String> itemSizeCol = new TableColumn<>("Size");
		itemSizeCol.setMinWidth(100);
		itemSizeCol.setCellValueFactory(data -> {
			Item item = ic.viewAcceptedItem(data.getValue().getItemId());
			return new SimpleStringProperty(item != null ? item.getItemSize() : "-");
		});

		TableColumn<Transaction, String> itemPriceCol = new TableColumn<>("Price");
		itemPriceCol.setMinWidth(100);
		itemPriceCol.setCellValueFactory(data -> {
			Item item = ic.viewAcceptedItem(data.getValue().getItemId());
			return new SimpleStringProperty(item != null ? item.getItemPrice() : "-");
		});

		table.getColumns().addAll(transactionIdCol, itemNameCol, itemCategoryCol, itemSizeCol, itemPriceCol);
	}

	private void loadData() {
		ArrayList<Transaction> transactions = tc.viewHistory(Session.getUser().getUserId());
		table.getItems().setAll(transactions);
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
