package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class RegisterPage implements EventHandler<ActionEvent>{

	private BorderPane frame;
	private VBox content;
	
	private Label title, usernameLabel, passwordLabel, phoneLabel, addressLabel, roleLabel;
	private TextField usernameField, phoneField, addressField;
	private PasswordField passwordField;
	private ToggleGroup role;
	private RadioButton buyerBtn, sellerBtn;
	
	private Button registerBtn, backBtn;
	private HBox hbButton;
	private HBox roleBtn;
	
	private UserController uc;
	private ArrayList<User> users;
	
	public Scene scene;
	
	public RegisterPage() {
		init();
		event();
		setAlignment();
		setStyle();
		Main.redirect(scene);
	}
	
	public void init() {
		frame = new BorderPane();
		title = new Label("Register Page");
		
		usernameLabel = new Label("Username: ");
		passwordLabel = new Label("Password: ");
		phoneLabel = new Label("Phone Number: ");
		addressLabel = new Label("Address");
		roleLabel = new Label("Role");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		phoneField = new TextField();
		addressField = new TextField();
		
		role = new ToggleGroup();
		buyerBtn = new RadioButton("Buyer");
		sellerBtn = new RadioButton("Seller");
		buyerBtn.setToggleGroup(role);
		sellerBtn.setToggleGroup(role);
		buyerBtn.setSelected(true);
		
		registerBtn = new Button("Register");
		backBtn = new Button("Back");
		hbButton = new HBox();
		roleBtn = new HBox();
		
		uc = new UserController();
		users = uc.getAllUsers();
		
		content = new VBox();
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setCenter(content);
		
		usernameField.setPrefWidth(300);
		passwordField.setPrefWidth(300);
		addressField.setPrefWidth(300);
		phoneField.setPrefWidth(300);
		
		hbButton.getChildren().addAll(registerBtn, backBtn);
		hbButton.setSpacing(10);
		hbButton.setAlignment(Pos.CENTER);
		
		roleBtn.getChildren().addAll(buyerBtn, sellerBtn);
		roleBtn.setSpacing(10);
        roleBtn.setAlignment(Pos.CENTER);
		
		content.getChildren().addAll(title, usernameLabel, usernameField, passwordLabel, passwordField, phoneLabel, phoneField, addressLabel, addressField, roleLabel, roleBtn, hbButton);
		content.setAlignment(Pos.CENTER);
		content.setSpacing(15);
		content.setPadding(new Insets(20));
		
		content.setFillWidth(false);
	}
	
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
		
		usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");
		passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");
		phoneField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");
		addressField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");
		
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

	public void event() {
		backBtn.setOnAction(e -> handle(e));
		registerBtn.setOnAction(e -> handle(e));
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == backBtn) {
			Main.redirect(new LoginPage().scene);
		}
		else {
			String getRole = "";
			if(role.getSelectedToggle() == buyerBtn) getRole = "Buyer";
			else getRole = "Seller";
			
			double res = uc.checkAccountValidation(usernameField.getText(), passwordField.getText(), phoneField.getText(), addressField.getText(), getRole);
			
			// Username validation
			if(res <= -1 && res > -2) {
				if(res == -1.1) {
					showAlert("Invalid Username", "Username can't be empty."); return;
				}
				else if(res == -1.2) {
					showAlert("Invalid Username", "Username must be at least 3 character long."); return;
				}
				else {
					showAlert("Invalid Username", "The username has already been taken."); return;
				}
				
			}
			// Password validation
			else if(res <= -2 && res > -3) {
				if(res == -2.1) {
					showAlert("Invalid Password", "Password can't be empty."); return;
				}
				else if(res == -2.2) {
					showAlert("Invalid Password", "Password must be at least 8 character long."); return;
				}
				else {
					showAlert("Invalid Password", "Password must include special characters (!, @, #, $, %, ^, &, *)."); return;
				}
			}
			// Phone number validation
			else if(res <= -3 && res > -4) {
				if(res == -3.1) {
					showAlert("Invalid Phone Number", "Phone number can't be empty."); return;
				}
				else if(res == -3.2) {
					showAlert("Invalid Phone Number", "Phone number must at least contains a +62 and 10 numbers long."); return;
				}
			}
			// Address validation
			else if(res <= -4) {
				showAlert("Invalid Address", "Address can't be empty."); return;
			}
			// Register Success
			else {
				
				showSuccess("Success", "Your account has been created!");
				Main.redirect(new LoginPage().scene);
			}
		
		}
		
	}
	
}
