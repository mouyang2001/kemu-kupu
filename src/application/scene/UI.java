package application.scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Common UI components.
 */
public class UI {
	/**
	 * Creats a label, styled as a title.
	 * 
	 * @param text Text to display.
	 * @return Configured label.
	 */
	public static Label title(String text) {
		Label title = new Label(text);
		title.setFont(new Font(30));
		title.setPrefHeight(300);
		return title;
	}
	
	/**
	 * Creates navigation button with
	 * the provided text and click handler.
	 *
	 * @param text Text to display on the button.
	 * @param handler Button click handler.
	 * @return Configured navigation button.
	 */
	public static Button button(String text, Runnable handler) {
		Button button = new Button(text);
		button.setPrefWidth(150);
		button.setOnAction(e -> handler.run());
		return button;
	}
	
}
