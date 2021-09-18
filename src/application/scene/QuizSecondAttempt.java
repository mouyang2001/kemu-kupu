package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class QuizSecondAttempt extends SceneManager{
	
	// this method is for pre loading things
	public void initialize() {
		System.out.println("Menu Started");
	}

	public void submit(ActionEvent e) throws IOException {
		//if correct
		switchToCorrectScene(e);
		//if incorrect 
		switchToIncorrectNextScene(e);
	}
	
	public void skip(ActionEvent e) throws IOException {
		switchToIncorrectNextScene(e);
	}
}
