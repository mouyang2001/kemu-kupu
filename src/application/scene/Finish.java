package application.scene;

import java.util.Optional;

import application.LeaderboardControl;
import application.QuizGame;
import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;

/** Finish screen that shows the users score after the quiz. */
public class Finish {
	@FXML private Label wellDone;

	@FXML private Label scoreTitle;

	@FXML private Label score;

	@FXML private Button stats;

	@FXML private Button menu;

	@FXML private Button playAgain;

	@FXML private ImageView image;

	private final double MAX_SCORE = QuizGame.MAX_SCORE * QuizGame.NUMBER_OF_ROUNDS;

	private Statistics statistics;

	private LeaderboardControl leaderboard;

	/**
	 * Shows current score and sets feedback message.
	 *
	 * @param scoreVal Score at end of quiz.
	 */
	public void initialise(Statistics statistics, boolean playSound) {
		this.statistics = statistics;

		setMessages();

		if (playSound) {
			Sound.play(Sound.Complete);
			leaderboard = new LeaderboardControl(statistics.getScore());
			if (leaderboard.isWinner()) {
				showPopup();
			}
		}

	}

	/** Click handler for the new quiz button. */
	@FXML
	private void newQuiz() {
		SceneManager.switchToTopicScene();
	}

	/** Click handler for the stats button. */
	@FXML
	private void showStats() {
		SceneManager.switchToStatsScene(statistics);
	}

	/** Click handler for the leaderboard button. */
	@FXML
	private void leaderboard() {
		SceneManager.switchToLeaderboardScene(statistics, leaderboard);
	}

	/** Click handler for the menu button. */
	@FXML
	private void menu() {
		SceneManager.switchToMenuScene();
	}

	/**
	 * Helper method to adjust wellDone label message depending on score
	 *
	 * @param score Score to base the message off.
	 */
	private void setMessages() {
		score.setText(String.valueOf(statistics.getScore()));

		double percentage = statistics.getScore() / MAX_SCORE;

		if (percentage < 0.8) {
			wellDone.setText("Kia Kaha, keep learning!");
		} else {
			wellDone.setText("Ka Pai, great work!");
		}
	}
	
	  /**
	   * helper method to set popup if player set a new highscore
	   * 
	   * <p>Popup code inspired by https://code.makery.ch/blog/javafx-dialogs-official/
	   */
	  private void showPopup() {
		  TextInputDialog dialog = new TextInputDialog(" ");
		  dialog.setTitle("Congratulations!");
		  dialog.setHeaderText("You set a new highscore!");
		  dialog.setContentText("Please enter your name:");

		  // Traditional way to get the response value.
		  Optional<String> result = dialog.showAndWait();
		  if (result.isPresent()){
			  leaderboard.setName(result.get());
		  } else {
			  leaderboard.setName("unknown player");
		  }
	  }
}
