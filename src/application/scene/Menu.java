package application.scene;

import application.NoWordsException;
import application.Wordlist;

import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Main menu for the user to chose what they want to do.
 * Provides navigation to all other scenes.
 */
public class Menu extends Scene {
	public Menu(SceneManager manager) {
		super(manager);
	}
	
	private final String wordsFile = "words/popular";
	
	/**
	 * Renders the menu scene to the screen.
	 *
	 * @return Root JavaFX node to render.
	 */
	@Override
	public Parent render() {
		// Title at the top of the screen.
		Label title = new Label("Spelling Whiz!");
		title.setFont(new Font(30));
		title.setPrefHeight(300);
		
		// Navigation buttons.
		Button quiz = UI.button(
			"New Spelling Quiz",
			() -> openQuiz("Spelling Quiz", wordsFile)
		);
		
		Button mistakes = UI.button(
			"Review Mistakes",
			() -> openMistakes(application.Statistics.mistakesFile.getPath())
		);
		
		Button stats = UI.button(
			"Statistics",
			() -> openStatistics(wordsFile)
		);
		
		// Show title and buttons vertically.
		VBox vbox = new VBox(20, title, quiz, mistakes, stats);
		vbox.setAlignment(Pos.TOP_CENTER);
		
		return vbox;
	}
	
	/**
	 * Open a quiz with the specified title and wordlist file.
	 *
	 * @param title Title to show at the top of the quiz.
	 * @param file Wordlist file to load and quiz.
	 */
	private void openQuiz(String title, String file) {
		try {
			manager.showQuiz(title, new Wordlist(file));
		} catch (FileNotFoundException e) {
			missingWordlistAlert(file);
		}
	}
	
	/**
	 * Open a quiz to review mistakes of a wordlist.
	 *
	 * @param file Wordlist file to load and quiz.
	 */
	private void openMistakes(String file) {
		try {
			Wordlist wordlist = new Wordlist(file);
			
			// Ensure there is at least 1 mistake to review.
			if (wordlist.getSize() == 0) {
				throw new NoWordsException();
			}
			
			manager.showQuiz("Review Mistakes", wordlist);
		} catch (FileNotFoundException | NoWordsException e) {
			// Show an error message if the wordlist file doesn't exist, or it is empty.
			SceneManager.alert("No Mistakes", "No Mistakes To Review");
		}
	}
	
	/**
	 * Open the statistics menu.
	 *
	 * @param file Wordlist file to load statistics for.
	 */
	private void openStatistics(String file) {
		try {
			manager.showStatistics(new Wordlist(file));
		} catch (FileNotFoundException e) {
			missingWordlistAlert(file);
		}
	}
	
	/**
	 * Shows a missing wordlist file alert.
	 *
	 * @param file Wordlist file that couldn't be found.
	 */
	private void missingWordlistAlert(String file) {
		SceneManager.alert("File Not Found", "Wordlist \"" + file + "\" Not Found");
	}
}
