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
import model.Reason_log;
import model.Reason_log;
import util.Session;

public class SellerRejectedItemPage implements EventHandler<ActionEvent>{
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label title, pageDesc, tablePlaceholder;
	private Button backBtn;
	private ItemController ic;
	private TableView<Reason_log> table;
	
	public SellerRejectedItemPage() {
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
		title = new Label("Rejected Item");
		pageDesc = new Label("Here are the list of Seller: " + Session.getUser().getUsername() + "'s Rejected Items");
		backBtn = new Button("Back");
		table = new TableView<>();
		tablePlaceholder = new Label("No items");
		table.setPlaceholder(tablePlaceholder);
		setupTable();
		loadData();

	}
	
	@SuppressWarnings("unchecked")
	private void setupTable() {
		TableColumn<Reason_log, String> nameCol = new TableColumn<>("Item Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		nameCol.setMinWidth(150);

		TableColumn<Reason_log, String> sizeCol = new TableColumn<>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		sizeCol.setMinWidth(100);

		TableColumn<Reason_log, String> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceCol.setMinWidth(100);

		TableColumn<Reason_log, String> catCol = new TableColumn<>("Category");
		catCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		catCol.setMinWidth(150);
		
		TableColumn<Reason_log, String> reasonsCol = new TableColumn<>("Reasons");
		reasonsCol.setCellValueFactory(new PropertyValueFactory<>("reasonText"));
		reasonsCol.setMinWidth(150);

		table.getColumns().addAll(nameCol, catCol, sizeCol, priceCol, reasonsCol);
	}

	private void loadData() {
		table.getItems().setAll(ic.getAllReason_Log());
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
		if (event.getSource() == backBtn) {
			Main.redirect(new HomePage().scene);
		}
	}

	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}


}
