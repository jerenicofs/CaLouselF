package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.stage = primaryStage;
		stage.setTitle("CaLouselF Group 1");
		new EditItemPage(); //Nanti harus diganti
	}
	
	public static void redirect(Scene redirectScene) {
		stage.setScene(redirectScene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

}
