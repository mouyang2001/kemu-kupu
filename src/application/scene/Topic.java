package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Topic extends SceneManager{
	
	// this method is for pre loading things
	public void initialize() {
		
	}
	
	public void startQuiz(ActionEvent e) throws IOException {
		switchToQuizScene(e);
	}
	
	/*
	 * TODO: functionality of choosing topic from list
	 */
}
