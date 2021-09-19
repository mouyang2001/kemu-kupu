package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Finish {
	
	// this method is for pre loading things
	public void initialize() {
		
	}

	// new quiz action
	public void newQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToTopicScene();
	}

	// quit game action
	public void quitGame(ActionEvent e) throws IOException {
		SceneManager.closeWindow();
	}
}
