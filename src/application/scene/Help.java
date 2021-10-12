package application.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;

public class Help {
    @FXML
    public Label title;

    @FXML
    public TextArea instructions;

    private final String ABS_PATH_TO_INSTRUCTIONS = "application/scene/assets/instructions.txt";

    /**
     * Scene initializer. Populates scene components.
     *
     * @throws IOException if no path to instructions text file exists.
     */
    @FXML
    public void initialize() throws IOException {
        // Each line in instructions text file.
        InputStream in = getClass().getClassLoader().getResourceAsStream(ABS_PATH_TO_INSTRUCTIONS);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        // Output lines in instructions text file to the instructions textArea.
        while ( (line = reader.readLine()) != null) instructions.appendText(line + "\n");

        // Setup instructions textArea.
        instructions.setEditable(false);
        instructions.positionCaret(0);
    }

    /** Click handler for back button. Returns to menu scene. */
    @FXML
    public void back() {
        SceneManager.switchToMenuScene();
    }
}
