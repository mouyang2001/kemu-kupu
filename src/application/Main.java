package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// loads menu scene first
		try {
			// load fxml
			Parent root = FXMLLoader.load(getClass().getResource("./scene/fxml/Menu.fxml"));
			Scene scene = new Scene(root);

			// load css
			String homeSceneCss = this.getClass().getResource("./scene/css/Menu.css").toExternalForm();
			scene.getStylesheets().add(homeSceneCss);

			// options
			stage.setScene(scene);
			stage.setTitle("Spelling Whiz");
			stage.setMaximized(true);

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
