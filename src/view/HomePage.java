package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomePage implements EventHandler<ActionEvent> {
	
	private BorderPane frame;
	private VBox content;
	private Label title;
	private Button editItemBtn;
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
		editItemBtn = new Button("Edit Item Page");
		
		content = new VBox();
		
		scene = new Scene(frame, 1200, 600);
	}
	
	public void setAlignment() {
		frame.setTop(content);
		
		content.getChildren().addAll(title, editItemBtn);
		content.setAlignment(Pos.CENTER);
		content.setSpacing(15);
		content.setPadding(new Insets(20));
	}
	
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}
	
	public void events() {
		editItemBtn.setOnAction(e->handle(e));
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == editItemBtn) {
			System.out.println("Go to Edit Item Page");
			Main.redirect(new EditItemPage().scene);
		}
	}
}
