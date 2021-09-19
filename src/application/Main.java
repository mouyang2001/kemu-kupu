package application;

import java.io.IOException;

import application.scene.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		// Set window options.
		stage.setTitle("Spelling Whiz");
		stage.setMaximized(true);

		SceneManager.show(stage);
	}
}
