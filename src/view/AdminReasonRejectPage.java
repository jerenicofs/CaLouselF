package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;

public class AdminReasonRejectPage implements EventHandler<ActionEvent> {
	private BorderPane root;
    private GridPane grid;
    public Scene scene;
    private Button submitBtn, backBtn;
    private Label title, text;
    private TextArea reasonField;
    private Item item;
    private ItemController ic;
    
    public AdminReasonRejectPage(Item i) {
    	this.item = i;
    	init();
    	setAlignment();
    	setStyle();
    	event();
    	Main.redirect(scene);
    }
    
    
    public void init() {
    	root = new BorderPane();
    	grid = new GridPane();
    	scene = new Scene(root, 1200, 600);
    	submitBtn = new Button("Submit");
    	backBtn = new Button("Back");
    	
    	title = new Label("Rejecting item: "+ item.getItemName());
    	text = new Label("State The Reasons");
    	
    	reasonField = new TextArea();
    	reasonField.setPrefHeight(200);
    	reasonField.setPrefWidth(30);
    	ic = new ItemController();
    	
    
    }
    
    public void setAlignment() {
		root.setLeft(grid);
		grid.setPadding(new Insets(20));
		grid.setVgap(10);
		grid.setHgap(10);
		
		grid.add(title, 0, 0);
		grid.add(text, 0, 1);
		grid.add(reasonField, 0, 2);
		grid.add(submitBtn, 0, 3);
		grid.add(backBtn, 1, 3);
    }


	public void event() {
		submitBtn.setOnAction(e -> handle(e));
		backBtn.setOnAction(e -> handle(e));
	}
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == submitBtn) {
			int res;
			res= ic.declineItem(item,reasonField.getText());
			// Item Name validation
			if (res < 0) {
				showAlert("Invalid Reasons", "Reasons can't be empty.");
				return;
			}
			else {
				showSuccess("Success", "Item " + item.getItemName() + " has been rejected");
				System.out.println("Create Reject_Log Success");
				Main.redirect(new AdminViewRequestedItemPage().scene);
			}

		} else if (event.getSource() == backBtn) {
			Main.redirect(new AdminViewRequestedItemPage().scene);
		}
	}

	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
		reasonField.setStyle("-fx-text-alignment: left; -fx-text-origin: top ; -fx-border-color: blue; -fx-border-width: 2px;");
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
