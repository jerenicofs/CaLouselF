package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EditItemPage extends Application {
	private BorderPane root;
	private GridPane grid;
	private Scene scene;
	private Label title, text;
	
	public void init() {
		root = new BorderPane();
		grid = new GridPane();
		scene = new Scene(root, 1100, 550);
		title = new Label("Shop Editor");
		text = new Label ("Edit your shop here");
	}
	
	public void setPosition() {
		root.setTop(title);
		root.setBottom(grid);
		
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setPrefHeight(350);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		title.setAlignment(Pos.TOP_CENTER);
		grid.add(text, 0, 0);
		
		
	}
	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}
	
	public EditItemPage() {
		init();
		setPosition();
		
		setStyle();
//		view.Main.redirect(scene);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Edit Item Page");
		stage.setScene(scene);
		stage.show();
		
	}

}
