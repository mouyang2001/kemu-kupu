package application.scene;

import javafx.scene.Parent;

/**
 * Interface of scenes that can be rendered
 * by a SceneManager to the screen.
 */
public interface Scene {
	/**
	 * Gets the JavaFX nodes to render to the screen.
	 *
	 * @param manager SceneManager of stage being rendered to.
	 * @return Root JavaFX node to render.
	 */
	Parent render(SceneManager manager);
}
