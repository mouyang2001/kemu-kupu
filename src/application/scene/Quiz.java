package application.scene;

import java.io.IOException;

import application.Festival;
import application.NoWordsException;
import application.Statistics;
import application.Statistics.Type;
import application.Wordlist;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Quizes the user on the spelling of the provided wordlist.
 * Speaks the words to the user and tells them if they are correct or not.
 */
public class Quiz implements Scene {
	private String title;
	
	private Wordlist wordlist;
	
	/**
	 * Runs a quiz on the provided wordlist.
	 * Renders the provided title at the top of the screen.
	 *
	 * @param title Title to put at the top of the screen.
	 * @param wordlist Wordlist to quiz the user on.
	 */
	Quiz(String title, Wordlist wordlist) {
		this.title = title;
		this.wordlist = wordlist;
	}
	
	/**
	 * Renders the quiz scene to the screen.
	 *
	 * @param manager SceneManager of stage to render to.
	 * @return Root JavaFX node to render.
	 */
	@Override
	public Parent render(SceneManager manager) {
		Label title = UI.title(this.title);
		
		// Label on which word is being tested.
		// Contents is initialised in testWord.
		Label label = new Label();
		label.setFont(new Font(20));
		label.setPrefHeight(100);
		
		// Textbox for the user to input their spelling.
		TextField input = new TextField();

		// Submit button for the user to submit their spelling.
		// The handler is initialised in setHandlers.
		Button submit = new Button("Submit");
		
		// Display the textbox and submit horisontally.
		HBox hbox = new HBox(input, submit);
		hbox.setAlignment(Pos.TOP_CENTER);
		
		// Display the title, label, and inputs vertically.
		VBox vbox = new VBox(title, label, hbox);
		vbox.setAlignment(Pos.TOP_CENTER);
		
		// Start testing the first word.
		testWord(manager, label, input, submit);
		
		return vbox;
	}
	
	/**
	 * Tests the user on their spelling of a word.
	 *
	 * @param manager SceneManager to allow error messages and automatic return to menu.
	 * @param label Label to show the user which number word they are spelling.
	 * @param input Textbox the user is inputting their spelling into.
	 * @param submit Submit button to wait for the user to press.
	 */
	private void testWord(SceneManager manager, Label label, TextField input, Button submit) {
		try {
			String word = wordlist.getRandomWord();
			
			// Set submission handlers for an unattempted quiz.
			setHandlers(false, manager, label, input, submit, word);

			// Tell the user to spell the random word.
			label.setText("Spell word " + wordlist.getUsed() + " of " + wordlist.getSize());
			speak(word, false);
		} catch (NoWordsException e) {
			// Return to the main menu if the
			// wordlist has no more words to test.
			manager.showMenu();
		} 
	}
	
	/**
	 * Set the submission handlers for the submit button
	 * and for when the enter key is pressed in the textbox.
	 *
	 * @param attempted If the current word has already been attempted.
	 * @param manager SceneManager of the current scene.
	 * @param label Label to update when the user is tested on the next word.
	 * @param input Textbox to set the handler of.
	 * @param submit Submit button to set the handler of.
	 * @param word Current word being tested.
	 */
	private void setHandlers(
		boolean attempted,
		SceneManager manager,
		Label label,
		TextField input,
		Button submit,
		String word
	) {
		submit.setOnAction(e -> submit(attempted, manager, label, input, submit, word));

		input.setOnKeyPressed(e -> {
			// Allow submission by pressing enter in the textbox.
			if (e.getCode().equals(KeyCode.ENTER)) {
				submit(attempted, manager, label, input, submit, word);
			}
		});
	}
	
	/**
	 * Checks if the user has spelled the quizzed word correctly.
	 * Quizes the next word if correct, allows another attempt if it is
	 * the first try or moves on to the next word if attempted and incorrect.
	 *
	 * @param attempted If the current word has already been attempted.
	 * @param manager SceneManager of the current scene.
	 * @param label Label to update when the user is tested on the next word.
	 * @param input Textbox to set the handler of.
	 * @param submit Submit button to set the handler of.
	 * @param word Current word being tested.
	 */
	private void submit(
		boolean attempted,
		SceneManager manager,
		Label label,
		TextField input,
		Button submit,
		String word
	) {
		String guess = input.getText();

		input.clear();
		
		// Check if the spelling is correct, ignoring case.
		if (guess.toLowerCase().equals(word.toLowerCase())) {
			speak("Correct", true);

			// Update statistics based on if the word was attempted multiple times.
			updateStatistics(attempted ? Type.FAULTED : Type.MASTERED, word);

			// Test the next word.
			testWord(manager, label, input, submit);
		} else if (!attempted) {
			// Allow another attempt if the word hasn't been attempted before.
			speak("Incorrect, try once more", true);
			speak(word, true);
			speak(word, true);

			// Update handlers for the next submission to have been attempted before.
			setHandlers(true, manager, label, input, submit, word);
		} else {
			// The user has gotten both attempts incorrect.
			speak("Incorrect", true);

			updateStatistics(Type.FAILED, word);

			// Test the next word.
			testWord(manager, label, input, submit);
		}
	}
	
	/**
	 * Updates the statistics file, showing a dialog box if an error occurs.
	 *
	 * @param type Type of statistic to update.
	 * @param word Word from wordlist to update statistics of.
	 */
	private void updateStatistics(Type type, String word) {
		try {
			Statistics.add(type, word);
		} catch (IOException e) {
			SceneManager.alert("File Error", "Statistics Files Could Not Be Updated");
		}
	}
	
	/**
	 * Speaks the text to the user, showing a dialog box if an error occurs.
	 *
	 * @param text Text to speak to the user.
	 * @param block If the operation should block until completion.
	 */
	private void speak(String text, boolean block) {
		try {
			Festival.speak(text, block);
		} catch (IOException | InterruptedException e) {
			SceneManager.alert("Text To Speech Error", "Text To Speech Of \"" + text + "\" Failed");
		}
	}
}
