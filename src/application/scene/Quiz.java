package application.scene;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Festival;
import application.QuizGame;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Quiz screen that quizzes the users spelling.
 */
public class Quiz {
	@FXML
	private Label score;
	
	@FXML
	private Label correct;
	
	@FXML
	private TextField input;
	
	@FXML
	private Label hint;

	private QuizGame quiz;
	private int scoreVal = 0;
	private int currentRound = 1;

	private final int NUMBER_OF_ROUNDS = 5;

	/**
	 * Method allows SceneManager to access and transfer data topic selection data.
	 * Also it's the entry point to start the quiz game.
	 *
	 * @param topic The chosen topic, file name.
	 */
	public void beginQuiz(String topic) throws FileNotFoundException {
		// clear default labels on fxml
		clearLabels();

		// begin new QuizGame instance
		quiz = new QuizGame(topic);

		// start off with the first word
		Festival.speak(quiz.getWord());

		// TODO: delete these outputs later
		System.out.println("Quiz topic is: " + topic);
		System.out.println(quiz.getWords());
		System.out.println(quiz.getWord());
	}
	
	/**
	 * Click handler for the submit button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void submit(ActionEvent e) throws IOException {
		// Retrieve, clear input and return focus to it.
		String spelling = input.getText();
		input.clear();
		input.requestFocus();

		// The cases are:
		// 1: Correct
		// 2: Incorrect, try again
		// 3: Incorrect, next word
		switch(quiz.checkSpelling(spelling)) {
			case 1:
				setPrompt("Correct", "#9AF1A3");

				increaseScore();
				nextWord();
				break;
			case 2:
				setPrompt("Incorrect, try again", "#E88787");

				giveHint();
				break;
			case 3:
				setPrompt("Incorrect :(", "#E88787");

				nextWord();
				break;
		}
	}

	/**
	 * Click handler for the skip button.
	 * Tells quiz object to go to the next word.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void skip(ActionEvent e) throws IOException {
		setPrompt("Incorrect :(", "#E88787");
		nextWord();
	}
	
	/**
	 * Click handler for the sound button.
	 * Gets festival to say the word.
	 * 
	 * @param e Event action information.
	 */
	@FXML
	public void sound(ActionEvent e) {
		Festival.speak(quiz.getWord());
	}

	/**
	 * Helper method to increase score and update label.
	 */
	private void increaseScore() {
		scoreVal++;
		score.setText(String.valueOf(scoreVal));
	}

	/**
	 * Helper method to set prompt message.
	 *
	 * @param message of what we want to set in prompt
	 * @param hex code of colour to set the message
	 */
	private void setPrompt(String message, String colour) {
		System.out.println(message);
		correct.setText(message);
		correct.setStyle("-fx-text-fill: "+ colour);
        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        wait.setOnFinished((e) -> {
            /*YOUR METHOD*/
        	correct.setText(" ");
        });
        wait.play();
	}

	/**
	 * Helper method to set a hint to the hint label.
	 */
	private void giveHint() {
		String letter = quiz.getHintLetterAtIndex(1);
		hint.setText("Hint: second letter is " + letter);
	}

	/**
	 * Helper method to clear all prompt labels (everything except score label).
	 */
	private void clearLabels() {
		hint.setText("");
		correct.setText("");
	}

	/**
	 * Helper method to jump to next word and reset UI elements.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	private void nextWord() throws IOException {
		// If NUMBER_OF_ROUNDS reached then switch to finish.
		if (currentRound == NUMBER_OF_ROUNDS) SceneManager.switchToFinishScene();
		else {
			// Increase current round count.
			currentRound++;

			// Clear labels and reset focus to input.
			clearLabels();
			input.requestFocus();

			// Get next word.
			quiz.nextWord();
			System.out.println(quiz.getWord());

			// Festival say word.
			Festival.speak(quiz.getWord());
		}
	}
	
	public int getScore() {
		return scoreVal;
	}
}
