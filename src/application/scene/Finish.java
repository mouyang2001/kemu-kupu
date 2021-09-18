package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Finish extends SceneManager{
	
	// this method is for pre loading things
	public void initialize() {
		System.out.println("Menu Started");
	}

	// new quiz action
	public void newQuiz(ActionEvent e) throws IOException {
		switchToTopicScene(e);
	}

	// quit game action
	public void quitGame(ActionEvent e) throws IOException {
		closeWindow(e);
	}
}
