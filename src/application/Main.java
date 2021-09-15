package application;

import application.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Show the main menu to the user.
		new SceneManager(stage).show();

		stage.setTitle("Spelling Whiz");
		stage.setMaximized(true);
	}
}
