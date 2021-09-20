package application.scene;

import application.Festival;
import application.QuizGame;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    
	private String RED = "#E88787";
    
	private String GREEN = "#9AF1A3";

	private final int NUMBER_OF_ROUNDS = 5;

	/**
	 * Method allows SceneManager to access and transfer data topic selection data.
	 * Also it's the entry point to start the quiz game.
	 *
	 * @param topic The chosen topic, file name.
	 * @throws IOException If an I/O error occured.
	 */
	public void beginQuiz(String topic) throws IOException {
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
	 * Scene initializer, for setting unique listener events on elements.
	 */
	@FXML
	public void initialize() {
		// Input TextField will listen to enter button pressed event.
		input.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					checkSpelling();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Click handler for the submit button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void submit(ActionEvent e) throws IOException {
		checkSpelling();
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
		setPrompt("Incorrect :(", RED);
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
	 * Method performs check spelling routine.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	private void checkSpelling() throws IOException {
		// Retrieve input in text field.
		String spelling = fetchInput();

		// Check spelling of word and proceed according to the case.
		switch (quiz.checkSpelling(spelling)) {
			case Correct:
				increaseScore();
				setPrompt("Correct", GREEN);
				nextWord();
				break;

			case FirstIncorrect:
				setPrompt("Incorrect, try again", RED);
				giveHint();
				break;

			case SecondIncorrect:
				setPrompt("Incorrect :(", RED);
				nextWord();
				break;
		}
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
	 * @param colour hex code of colour to set the message
	 */
	private void setPrompt(String message, String colour) {
		System.out.println(message);
		correct.setText(message);
		correct.setStyle("-fx-text-fill: " + colour);
		
		PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished(e -> correct.setText(""));
        wait.play();
	}

	/**
	 * Helper method to get text from input TextField and reset it.
	 *
	 * @return text that player inputted into the TextField input.
	 */
	private String fetchInput() {
		String text = input.getText();
		input.clear();
		input.requestFocus();
		return text;
	}

	/**
	 * Helper method to show the hint to the user.
	 */
	private void giveHint() {
		hint.setText("Hint: second letter is " + quiz.getHintLetterAtIndex(1));
	}

	/**
	 * Helper method to clear hint label.
	 */
	private void clearHint() {
		hint.setText("");
	}

	/**
	 * Helper method to jump to next word and reset UI elements.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	private void nextWord() throws IOException {
		// If NUMBER_OF_ROUNDS reached then switch to finish.
		if (currentRound == NUMBER_OF_ROUNDS) {
			SceneManager.switchToFinishScene(scoreVal);
			return;
		}
		
		// Increase current round count.
		currentRound++;

		// Clear hint and reset focus to input.
		clearHint();
		input.requestFocus();

		// Get next word.
		quiz.nextWord();
		System.out.println(quiz.getWord());

		// Festival say word.
		Festival.speak(quiz.getWord());
	}
}
