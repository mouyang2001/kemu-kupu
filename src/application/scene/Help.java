package application.scene;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/** Help screen with instructions on how to use the application. */
public class Help {
  @FXML public Label title;

  @FXML public TextArea instructions;

  private static final String INSTRUCTIONS_FILE = "assets/instructions.txt";

  /**
   * Scene initializer. Populates scene components.
   *
   * @throws IOException If the instructions file doesn't exist.
   * @throws URISyntaxException If the instructions file doesn't exist.
   */
  @FXML
  public void initialize() throws IOException, URISyntaxException {
    Path path = Paths.get(getClass().getResource(INSTRUCTIONS_FILE).toURI());
    String contents = Files.lines(path).collect(Collectors.joining("\n"));

    instructions.setText(contents);
    instructions.setEditable(false);
    instructions.positionCaret(0);
  }

  /** Click handler for back button. Returns to menu scene. */
  @FXML
  public void back() {
    SceneManager.switchToMenuScene();
  }
}
