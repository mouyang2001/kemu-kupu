package application.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

/**
 * Main menu for the user to chose what they want to do. Provides navigation to all other scenes.
 */
public class Menu {
  @FXML private Label title;

  @FXML private Button newQuiz;

  @FXML private Button quit;

  /** Click handler for the new quiz button. */
  @FXML
  private void newQuiz() {
    SceneManager.switchToTopicScene();
  }

  /** Click handler for the quit button. 
   *  with confirmation popup 
  */
  @FXML
  private void quit() {
	  Alert alert = new Alert(AlertType.CONFIRMATION);
	  alert.setTitle("");
	  alert.setHeaderText("Are you sure you want to leave the game?");
	  alert.setContentText("");

	  if (alert.showAndWait().get() == ButtonType.OK) {
		  SceneManager.closeWindow();
	  } else {
		  alert.close();
	  }
  }

  /** Click handler for the quit button. */
  @FXML
  private void help() {
    SceneManager.switchToHelpScene();
  }
}
