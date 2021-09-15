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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Quizzes the user on the spelling of the provided wordlist.
 * Speaks the words to the user and tells them if they are correct or not.
 */
public class Quiz extends Scene {
	private String title;
	
	private Wordlist wordlist;
	
	private String word;
	
	private boolean attempted;
	
	/**
	 * Runs a quiz on the provided wordlist.
	 * Renders the provided title at the top of the screen.
	 *
	 * @param manager The associated SceneManager handling this scene.
	 * @param title Title to put at the top of the screen.
	 * @param wordlist Wordlist to quiz the user on.
	 */
	Quiz(SceneManager manager, String title, Wordlist wordlist) {
		super(manager);
		
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
	public Parent render() {
		Label title = UI.title(this.title);
		
		// TODO: Replace with a loading message.
		if (word == null) {
			return title;
		}
		
		// Label to tell the user which word is being tested.
		Label label = new Label();
		label.setText("Spell word " + wordlist.getUsed() + " of " + wordlist.getSize());
		label.setFont(new Font(20));
		label.setPrefHeight(100);
		
		// Text field for the user to input their spelling.
		TextField input = new TextField();
		input.setOnKeyPressed(e -> textFieldHandler(e, input.getText()));
		
		// Submit button for the user to submit their spelling.
		Button submit = new Button("Submit");
		submit.setOnAction(e -> submit(input.getText()));
		
		// Display the text field and submit horisontally.
		HBox hbox = new HBox(input, submit);
		hbox.setAlignment(Pos.TOP_CENTER);
		
		// Display the title, label, and inputs vertically.
		VBox vbox = new VBox(title, label, hbox);
		vbox.setAlignment(Pos.TOP_CENTER);
		
		return vbox;
	}
	
	/**
	 * Starts testing the first word as soon as the scene is shown.
	 */
	@Override
	public void didMount() {
		quizNextWord();
	}
	
	/**
	 * Quizzes the user on the next word from the wordlist.
	 * Speaks the word to the user and waits for their submission.
	 */
	private void quizNextWord() {
		try {
			// Get the word to test.
			word = wordlist.getRandomWord();
			attempted = false;
			
			speak(word, false);
			
			// Update the GUI with the new counter.
			manager.update();
		} catch (NoWordsException e) {
			// If there are not more words to test,
			// return to the main menu.
			manager.showMenu();
		}
	}
	
	/**
	 * Submits the attempt if the user pressing enter in the text field.
	 * 
	 * @param event The keyboard event that occurred.
	 * @param attempt The users current spelling attempt.
	 */
	private void textFieldHandler(KeyEvent event, String attempt) {
		// Allow submission by pressing enter in the text field.
		if (event.getCode().equals(KeyCode.ENTER)) {
			submit(attempt);
		}
	}
	
	/**
	 * Handles the users submission of their spelling attempt.
	 * Tells the user if they are correct and updates the statistics.
	 * 
	 * @param attempt The users current spelling attempt.
	 */
	private void submit(String attempt) {
		if (attempt.toLowerCase().equals(word.toLowerCase())) {
			speak("Correct", true);
			
			// Update statistics based on if the word was attempted multiple times.
			updateStatistics(attempted ? Type.FAULTED : Type.MASTERED, word);
			
			quizNextWord();
		} else if (!attempted) {
			// Allow another attempt if the word hasn't been attempted before.
			speak("Incorrect, try once more", true);
			speak(word, true);
			speak(word, true);
			
			// Mark the word as attempted, so only two attempts are made.
			attempted = true;
		} else {
			// The user has gotten both attempts incorrect.
			speak("Incorrect", true);
			
			updateStatistics(Type.FAILED, word);
			
			quizNextWord();
		}
		
		// Update the GUI with the new counter
		// and clears the text field.
		manager.update();
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
