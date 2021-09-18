package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Correct extends SceneManager {
	
	// this method is for pre loading things
	public void initialize() {
		System.out.println("Menu Started");
	}

	public void next(ActionEvent e) throws IOException {
		//if still more words
		switchToQuizScene(e);
		//else
		switchToFinishScene(e);
	}
}
