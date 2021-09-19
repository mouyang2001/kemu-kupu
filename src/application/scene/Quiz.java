package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Quiz {
	// this method is for pre loading things
	public void initialize() {
		
	}

	public void submit(ActionEvent e) throws IOException {
		//if correct
		/*
		 * TODO: correct popup
		 */
		isFinished(e);
		//if incorrect 1st attempt
		/*
		 * TODO: incorrect try again popup
		 */
		SceneManager.switchToQuizScene();
		//if incorrect 2nd attempt
		/*
		 * TODO: incorrect popup
		 */
		isFinished(e);
	}
	
	public void skip(ActionEvent e) throws IOException {
		/*
		 * TODO: incorrect popup
		 */
		isFinished(e);
	}
	
	public void isFinished(ActionEvent e) throws IOException {
		//if word list is empty
		SceneManager.switchToFinishScene();
		//else
		SceneManager.switchToQuizScene();
	}
	
	public void sound() {
		
	}
}
