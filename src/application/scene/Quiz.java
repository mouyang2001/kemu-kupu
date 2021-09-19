package application.scene;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Festival;
import application.QuizGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	private final int numberOfRounds = 5;

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
				// TODO: correct popup.
				System.out.println("Correct!");

				increaseScore();
				nextWord();
				break;
			case 2:
				// TODO: incorrect try again popup
				System.out.println("Incorrect, try again");

				giveHint();
				break;
			case 3:
				// TODO: incorrect/encouraging message popup
				System.out.println("Wrong, but *Encouraging Message*");

				nextWord();
				break;
		}
	}

	/**
	 * Click handler for the skip button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void skip(ActionEvent e) throws IOException {
		nextWord();
	}
	
	/**
	 * Click handler for the sound button.
	 * 
	 * @param e Event action information.
	 */
	@FXML
	public void sound(ActionEvent e) {
		
	}

	/**
	 * Helper method to increase score and update label.
	 */
	private void increaseScore() {
		scoreVal++;
		score.setText(String.valueOf(scoreVal));
	}

	/**
	 * Helper method to set a hint to the hint label.
	 */
	private void giveHint() {
		String letter = quiz.getHintLetterAtIndex(1);
		hint.setText("Hint: second letter is " + letter);
	}

	/**
	 * Helper method to clear all prompt labels (everything except score).
	 */
	private void clearLabels() {
		hint.setText("");
		correct.setText("");
	}

	/**
	 * Helper method to jump to next word.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	private void nextWord() throws IOException {
		if (currentRound == numberOfRounds) SceneManager.switchToFinishScene();
		else {
			currentRound++;

			quiz.nextWord();
			Festival.speak(quiz.getWord());

			System.out.println(quiz.getWord());

			input.requestFocus();
		}
	}
}
