package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class IncorrectTryAgain extends SceneManager {
	
	// this method is for pre loading things
	public void initialize() {
		System.out.println("Menu Started");
	}

	public void tryAgain(ActionEvent e) throws IOException {
		switchToQuizSecondAttemptScene(e);
	}
}
