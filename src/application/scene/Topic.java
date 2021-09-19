package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Topic {
	
	// this method is for pre loading things
	public void initialize() {
		
	}
	
	public void startQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToQuizScene();
	}
	
	/*
	 * TODO: functionality of choosing topic from list
	 */
}
