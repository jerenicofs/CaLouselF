package view;

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
import model.Item;
import model.Offer;

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
		title = new Label("Admin Dashboard");
		pageDesc = new Label("Here are the items offered by Buyer");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No items are being offered");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();

	}
	
	private void setupTable() {
		
	}
	
	private void loadData() {
		
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
