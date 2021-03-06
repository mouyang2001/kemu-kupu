package application.scene;

import application.LeaderboardScore;
import application.QuizGame.Mode;
import application.Statistics;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/** Controls which scene is displayed to the user through changing the JavaFX stage. */
public class SceneManager {
  private static Stage stage;

  /**
   * Show the main menu scene to the user. This must be called before any switch... methods.
   *
   * @param stage The JavaFX stage to use.
   */
  public static void show(Stage stage) {
    SceneManager.stage = stage;

    // Load empty to allow scene switch.
    stage.setScene(new Scene(new Group()));

    // Show menu scene.
    switchToMenuScene();
    stage.show();
  }

  /** Show the menu scene to the user. */
  public static void switchToMenuScene() {
    changeScene("Menu");
  }

  /** Show the topic scene to the user. */
  public static void switchToTopicScene() {
    changeScene("Topic");
  }

  /** Show the topic scene to the user. */
  public static void switchToHelpScene() {
    changeScene("Help");
  }

  /**
   * Show the quiz scene to the user and transfer topic selection data.
   *
   * @param topic The chosen topic, file name.
   * @param mode The mode of the quiz to run.
   */
  public static void switchToQuizScene(String topic, Mode mode) {
    Quiz quiz = changeScene("Quiz").getController();
    quiz.beginQuiz(topic, mode);
  }

  /**
   * Show the finish scene to the user.
   *
   * @param statistics Statistics of the quiz.
   * @param returning If returning to the finish screen.
   */
  public static void switchToFinishScene(Statistics stats, boolean returning) {
    Finish finish = changeScene("Finish").getController();
    finish.initialise(stats, returning);
  }

  /**
   * Show the stats scene to the user.
   *
   * @param statistics The stats to show.
   */
  public static void switchToStatsScene(Statistics statistics) {
    Stats stats = changeScene("Stats").getController();
    stats.initialise(statistics);
  }

  /**
   * Show the leaderboard scene to the user.
   *
   * @param quiz If being shown after a quiz.
   * @param statistic The stats to return to.
   * @param leaderboardScore The score obtained on the leaderboard.
   */
  public static void switchToLeaderboardScene(
      boolean quiz, Statistics statistics, LeaderboardScore leaderboardScore) {
    Leaderboard leaderboard = changeScene("Leaderboard").getController();
    leaderboard.initialise(quiz, statistics, leaderboardScore);
  }

  /**
   * Changes the currently displayed scene to the user.
   *
   * @param name The name of the FXML and CSS files without the file extension.
   * @return The FXMLLoader to allow access to the controller instance.
   */
  private static FXMLLoader changeScene(String name) {
    try {
      // Load resources.
      FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("fxml/" + name + ".fxml"));
      Parent root = loader.load();

      String css = SceneManager.class.getResource("css/" + name + ".css").toExternalForm();

      // Show new scene.
      Scene scene = stage.getScene();
      scene.setRoot(root);

      // Apply CSS.
      scene.getStylesheets().clear();
      scene.getStylesheets().add(css);

      return loader;
    } catch (IOException e) {
      alert("Could not load FXML or CSS resources.");
      return null;
    }
  }

  /**
   * Show an alert dialog box to the user.
   *
   * @param message The message to show in the dialog box.
   */
  public static void alert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(message);
    alert.showAndWait();
  }

  /** Closes the JavaFX window. */
  public static void closeWindow() {
    stage.close();
  }
}
