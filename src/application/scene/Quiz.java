package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

public class Quiz extends SceneManager{
	
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
		switchToQuizScene(e);
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
		switchToFinishScene(e);
		//else
		switchToQuizScene(e);
	}
	
	public void sound() {
		
	}
}
