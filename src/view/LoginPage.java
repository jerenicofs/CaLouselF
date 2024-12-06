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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class LoginPage implements EventHandler<ActionEvent> {
	
	private BorderPane frame;
	private VBox content;
	private Label title, usernameLabel, passwordLabel;
	private TextField usernameField;
	private PasswordField passwordField;
	private Button registerBtn, loginBtn;
	private UserController uc;
	private ArrayList<User> users;
	
	private HBox hbButton;
	
	private static String userID = "";
	private static String userRole = "";
	
	public Scene scene;
	
	public LoginPage() {
		init();
		setAlignment();
		setStyle();
		events();
		Main.redirect(scene);
	}
	
	public void init() {
		frame = new BorderPane();
		title = new Label("Login Page");
		usernameLabel = new Label("Username: ");
		passwordLabel = new Label("Password: ");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		registerBtn = new Button("Register");
		loginBtn = new Button("Login");
		hbButton = new HBox();
		
		uc = new UserController();	
		users = uc.getAllUsers();
		
		content = new VBox();
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setCenter(content);
		
		usernameField.setPrefWidth(300);
		passwordField.setPrefWidth(300);
		
		hbButton.getChildren().addAll(registerBtn, loginBtn);
		hbButton.setSpacing(10);
		hbButton.setAlignment(Pos.CENTER);
		
		content.getChildren().addAll(title, usernameLabel, usernameField, passwordLabel, passwordField, hbButton);
		content.setAlignment(Pos.CENTER);
		content.setSpacing(15);
		content.setPadding(new Insets(20));
		
		content.setFillWidth(false);
		
	}
	
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
		
		usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");
		passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: lightgray;");

	}

	public void showAlert(String title, String errorMessage) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}
	
	public void events() {
		loginBtn.setOnAction(e -> handle(e));
		registerBtn.setOnAction(e -> handle(e));
	}
	@Override
	public void handle(ActionEvent event) {
		// Handle Login Button
		if(event.getSource() == loginBtn) {
			
			int res = uc.login(usernameField.getText(), passwordField.getText());
			
			// Error username
			if(res == -1) {
				showAlert("Invalid Username", "Username can't be empty."); return;
			}
			// Error Password
			else if(res == -2) {
				showAlert("Invalid Password", "Password can't be empty."); return;
			}
			// Error Both
			else if(res == -3) {
				showAlert("Login Invalid", "Username or Password Not Found."); return;
			}
			// Login success
			else {
				
				System.out.println("Admin Login");
				Main.redirect(new HomePage().scene);
			
			}
			
		}
		
		// Handle Register Button
		else if(event.getSource() == registerBtn) {
			Main.redirect(new RegisterPage().scene);
		}
		
	}
	
}
