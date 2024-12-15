package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import model.Offer_Log;
import util.Session;

public class BuyerViewOfferLog implements EventHandler<ActionEvent>{
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private ItemController ic;
	private TableView<Offer_Log> table;
	
	public BuyerViewOfferLog(){
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
		TableColumn<Offer_Log, String> nameCol = new TableColumn<>("Item Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		nameCol.setMinWidth(150);

		TableColumn<Offer_Log, String> sizeCol = new TableColumn<>("Item Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		sizeCol.setMinWidth(150);
		
		TableColumn<Offer_Log, String> priceCol = new TableColumn<>("Item Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceCol.setMinWidth(150);
		
		TableColumn<Offer_Log, String> catCol = new TableColumn<>("Item Category");
		catCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		catCol.setMinWidth(150);
		
		TableColumn<Offer_Log, String> offerPriceCol = new TableColumn<>("Your Offered Price");
		offerPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemOfferPrice"));
		offerPriceCol.setMinWidth(150);
		
		TableColumn<Offer_Log, String> reasonCol = new TableColumn<>("Status");
		reasonCol.setCellValueFactory(new PropertyValueFactory<>("reasonText"));
		reasonCol.setMinWidth(150);
		table.getColumns().addAll(nameCol, catCol, sizeCol, priceCol, offerPriceCol, reasonCol);
	}

	private void loadData() {
		table.getItems().setAll(ic.getAllOfferLog(Session.getUser().getUserId()));
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

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == backBtn) {
			Main.redirect(new HomePage().scene);
		}
	}
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}
}
