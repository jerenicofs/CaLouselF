package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button root = new Button();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Uploader and Viewer");
        primaryStage.show();
        System.out.println("TEST");
        System.out.println("INI TEST UNTUK BAS DARI NICO");
	}

}
