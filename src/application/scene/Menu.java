package application.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

  /** Click handler for the quit button. */
  @FXML
  private void quit() {
    SceneManager.closeWindow();
  }
}
