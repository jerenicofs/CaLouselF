package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Item;

public class BuyerMakeOfferPage implements EventHandler<ActionEvent>{
	
	private BorderPane frame;
	private VBox container;
	private Label itemNameLbl, itemSizeLbl, itemPriceLbl, itemCategoryLbl, highestPriceLbl, offerPriceLbl, title;
	private TextField offerPriceField;
	private Button makeOfferBtn, backBtn;
	
	private ItemController ic;
	public Scene scene;
	
	private Item item;
	
	public BuyerMakeOfferPage(Item i) {
		this.item = i;
		ic = new ItemController();
		init();
		event();
		setAlignment();
		setStyle();
		Main.redirect(scene);
	}
	
	public void init() {
		frame = new BorderPane();
		title = new Label();
		title.setText("Make a new offer for " + item.getItemName());
		
		itemNameLbl = new Label("Name: " + item.getItemName());
		itemSizeLbl = new Label("Size: " + item.getItemSize());
		itemPriceLbl = new Label("Price: " + item.getItemPrice());
		itemCategoryLbl = new Label("Category: " + item.getItemCategory());
		highestPriceLbl = new Label("Highest Offer Price: " + ic.getHighestOffer(item.getItemId()));
		offerPriceLbl = new Label("Offer better price: ");
		offerPriceField = new TextField();
		
		makeOfferBtn = new Button("Make offer");
		backBtn = new Button("Back");
		
		container = new VBox();
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setCenter(container);
		
		container.getChildren().addAll(title, itemNameLbl, itemSizeLbl, itemPriceLbl, itemCategoryLbl, highestPriceLbl, offerPriceLbl, offerPriceField, makeOfferBtn, backBtn);
		container.setAlignment(Pos.CENTER);
		container.setSpacing(15);
		container.setPadding(new Insets(20));
		container.setFillWidth(false);
	}
	
	public void setStyle() {
	    title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

	    itemNameLbl.setStyle("-fx-font-size: 18px;-fx-font-weight: bold; -fx-text-fill: #34495e;");
	    itemSizeLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
	    itemPriceLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
	    itemCategoryLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
	    highestPriceLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #16a085; -fx-font-weight: bold;");
	    offerPriceLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");

	    offerPriceField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-radius: 5px;");

	}

	
	public void event() {
		makeOfferBtn.setOnAction(e -> handle(e));
		backBtn.setOnAction(e -> handle(e));
	}
	

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == makeOfferBtn){
			int res = ic.validateOfferPrice(item.getItemId(), offerPriceField.getText());
			
			if(res == -1) {
				showAlert("Invalid Price", "Offer Price can't be empty."); return;
			}
			else if(res == -2) {
				showAlert("Invalid Price", "Offer Price must be more than 0."); return;
			}
			else if(res == -3) {
				showAlert("Invalid Price", "Offer Price cannot be lower than the highest offer"); return;
			}
			// Berhasil membuat offer baru
			else {
				showSuccess("Offer Success", "New Offer has been created!");
				Main.redirect(new HomePage().scene);
			}
		}
		else {
			Main.redirect(new HomePage().scene);
		}
		
	}
	
	public void showAlert(String title, String errorMessage) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}
	
	public void showSuccess(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
