package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomePage implements EventHandler<ActionEvent> {
	
	private BorderPane frame;
	private VBox content;
	private Label title;
	
	public Scene scene;
	
	public HomePage() {
		init();
		setAlignment();
		setStyle();
		events();
		Main.redirect(scene);
	}
	
	public void init() {
		frame = new BorderPane();
		title = new Label("Home Page");
		
		content = new VBox();
		
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setTop(content);
		
		content.getChildren().addAll(title);
		content.setAlignment(Pos.CENTER);
		content.setSpacing(15);
		content.setPadding(new Insets(20));
	}
	
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}
	
	public void events() {
		
	}

	@Override
	public void handle(ActionEvent event) {
		
	}
}
