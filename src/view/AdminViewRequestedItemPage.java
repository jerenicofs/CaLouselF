package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;

public class AdminViewRequestedItemPage {
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private ItemController ic;
	private TableView<Item> table;

	public AdminViewRequestedItemPage() {
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
		title = new Label("Admin Dashboard");
		pageDesc = new Label("Here are the items waiting for approval");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No items waiting");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();

	}

	@SuppressWarnings("unchecked")
	private void setupTable() {
		TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		nameCol.setMinWidth(150);

		TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		sizeCol.setMinWidth(100);

		TableColumn<Item, String> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceCol.setMinWidth(100);

		TableColumn<Item, String> catCol = new TableColumn<>("Category");
		catCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		catCol.setMinWidth(150);

		// Approve Btn
		TableColumn<Item, Void> approveCol = new TableColumn<>("Approve");
		approveCol.setCellFactory(param -> new TableCell<>() {
			Button approveBtn = new Button("Approve");

			{
				approveBtn.setOnAction(event -> {
					Item item = getTableView().getItems().get(getIndex());
					System.out.println("Approving item: " + item.getItemName());
					getTableView().getItems().remove(item);
					//Approve Item
					ic.ApproveItem(item.getItemId());
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(approveBtn);
				}
			}
		});

		// Decline Btn
		TableColumn<Item, Void> rejectCol = new TableColumn<>("Decline");
		rejectCol.setCellFactory(param -> new TableCell<>() {
			Button declineBtn = new Button("Decline");

			{
				declineBtn.setOnAction(event -> {
					Item item = getTableView().getItems().get(getIndex());
					getTableView().getItems().remove(item);
					System.out.println("Decline item: " + item.getItemName());
					// Reject item
					Main.redirect(new AdminReasonRejectPage(item).scene);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(declineBtn);
				}
			}
		});
		table.getColumns().addAll(nameCol, catCol, sizeCol, priceCol, approveCol, rejectCol);
	}

	private void loadData() {
		table.getItems().setAll(ic.getAllPendingItems());
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
