package application.scene;

import javafx.scene.Parent;

/**
 * Abstract class of scenes that can be rendered
 * by a SceneManager to the screen and will be
 * provided with the lifecycle methods.
 */
public abstract class Scene {
	protected SceneManager manager;
	
	/**
	 * The SceneManager to associate with the scene.
	 * This can be used for navigation and updating the GUI.
	 * 
	 * @param manager The associated SceneManager handling this scene.
	 */
	public Scene(SceneManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Gets the JavaFX nodes to render to the screen.
	 *
	 * @return Root JavaFX node to render.
	 */
	public abstract Parent render();
	
	/**
	 * This is called before the first render.
	 * This method will be exactly once.
	 * Background tasks such as data loading should be done here.
	 */
	public void didMount() {}
	
	/**
	 * This is called after each render, except the initial render.
	 * You may call SceneManager.update() in this method,
	 * but it must be inside a condition to avoid an infinite loop.
	 */
	public void didUpdate() {}
	
	/**
	 * This is called after the last render when the SceneManager
	 * is no longer going to use the scene.
	 * This is the last lifecycle method to be called.
	 * Cleanup should be done in this method.
	 */
	public void willUnmount() {}
}
