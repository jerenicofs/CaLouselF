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
import model.Offer;

public class SellerDeclineOfferItemPage implements EventHandler<ActionEvent>{
	
	private BorderPane frame;
	private VBox container;
	private Label itemNameLbl, itemSizeLbl, itemPriceLbl, itemCategoryLbl, offerPriceLbl, declineLbl, title;
	private TextField declineField;
	private Button declineBtn, backBtn;
	
	private ItemController ic;
	public Scene scene;
	
	private Offer offer;
	
	public SellerDeclineOfferItemPage(Offer o) {
		this.offer = o;
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
		title.setText("Declining offer for " + ic.getItemNameById(offer.getItemId()));
		
		itemNameLbl = new Label("Name: " + ic.getItemNameById(offer.getItemId()));
		itemSizeLbl = new Label("Size: " + ic.getItemSizeById(offer.getItemId()));
		itemPriceLbl = new Label("Price: " + ic.getItemPriceById(offer.getItemId()));
		itemCategoryLbl = new Label("Category: " + ic.getItemCategoryById(offer.getItemId()));
		offerPriceLbl = new Label("Offered price: " + offer.getOfferedPrice());
		declineLbl = new Label("Input your reason: ");
		declineField = new TextField();
		
		declineBtn = new Button("Decline offer");
		backBtn = new Button("Back");
		
		container = new VBox();
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setCenter(container);
		
		container.getChildren().addAll(title, itemNameLbl, itemSizeLbl, itemPriceLbl, itemCategoryLbl, offerPriceLbl, declineLbl ,declineField, declineBtn, backBtn);
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
	    offerPriceLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
	    declineLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");

		declineField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-radius: 5px;");

	}
	
	public void event() {
		declineBtn.setOnAction(e -> handle(e));
		backBtn.setOnAction(e -> handle(e));
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == declineBtn) {
			int res = ic.validateDecline(declineField.getText(), offer.getItemId());
			
			if(res == -1) {
				showAlert("Reason Invalid", "Reason cannot be empty!"); return;
			}
			else {
				showSuccess("Decline Success", "Offer successfuly declined.");
				Main.redirect(new SellerOfferedItemPage().scene);
			}
			
		}
		else {
			Main.redirect(new SellerOfferedItemPage().scene);
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
