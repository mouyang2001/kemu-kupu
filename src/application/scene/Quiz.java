package application.scene;

import application.AttemptResult.Type;
import application.Festival;
import application.QuizGame;
import application.QuizGame.Mode;
import application.SingleDelayedTask;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/** Quiz screen that quizzes the users spelling. */
public class Quiz {
  @FXML private Label score;

  @FXML private Label correct;

  @FXML private TextField input;

  @FXML private Button sound;

  @FXML private Button macronButton;

  @FXML private ImageView image;

  @FXML private Label hint;

  @FXML private Label slow;

  @FXML private Slider speed;

  @FXML private Label fast;

  @FXML private Button menu;

  @FXML private Button skip;

  @FXML private Button submit;

  @FXML private Button macronButtonA;
  
  @FXML private Button macronButtonE;
  
  @FXML private Button macronButtonI;
  
  @FXML private Button macronButtonO;
  
  @FXML private Button macronButtonU;

  private QuizGame quiz;
  
  private int caret = 0;

  private static final String RED = "#E88787";

  private static final String GREEN = "#9AF1A3";

  /**
   * Method allows SceneManager to access and transfer data topic selection data. Also it's the
   * entry point to start the quiz game.
   *
   * @param topic The chosen topic, file name.
   * @param mode The mode of the quiz to run.
   */
  public void beginQuiz(String topic, Mode mode) {
    try {
      // Start the quiz controller.
      quiz = new QuizGame(topic, mode);
    } catch (IOException e) {
      SceneManager.alert("Could not load word list.");
    }

    // Register speed handler.
    speed
        .valueProperty()
        .addListener((observable, oldValue, newValue) -> Festival.setSpeed(newValue.floatValue()));
    
    // Register caret position handler.
    input.caretPositionProperty().addListener((observable, oldValue, newValue) -> caret = oldValue.intValue());

    // Start the first word.
    nextWord();
  }

  /** Click handler for the menu button. */
  @FXML
  private void menu() {
    // Don't prompt for practices as nothing is saved.
    if (quiz.getMode() == Mode.Practice) {
      SceneManager.switchToMenuScene();
      return;
    }

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("");
    alert.setHeaderText("Are you sure you want to leave the quiz?");
    alert.setContentText("You will lose all your hard word :(");

    if (alert.showAndWait().get() == ButtonType.OK) {
      // Quit and return to menu.
      SceneManager.switchToMenuScene();
    } else {
      // Continue with quiz.
      alert.close();
      input.requestFocus();
    }
  }

  /** Click handler for the submit button. */
  @FXML
  private void submit() {
    checkSpelling();
  }

  /** Click handler for the skip button. Tells quiz object to go to the next word. */
  @FXML
  private void skip() {
    Sound.play(Sound.Incorrect);

    quiz.skip();

    setPrompt("Skipped", RED);
    nextWord();
  }

  /** Click handler for the sound button. Gets festival to say the word. */
  @FXML
  private void sound() {
    // Disable buttons while the word is spoken and re-enable after.
    disableInputs(true);
    Festival.speak(quiz.getWord(), () -> disableInputs(false));
  }

  /** Click handler for the macron ā button. */
  @FXML
  private void macronA() {
    insertString("ā");
  }

  /** Click handler for the macron ē button. */
  @FXML
  private void macronE() {
    insertString("ē");
  }

  /** Click handler for the macron ī button. */
  @FXML
  private void macronI() {
    insertString("ī");
  }

  /** Click handler for the macron ō button. */
  @FXML
  private void macronO() {
    insertString("ō");
  }

  /** Click handler for the macron ū button. */
  @FXML
  private void macronU() {
    insertString("ū");
  }

  /**
   * Inserts a string at the current cursor position.
   *
   * @param str String to insert.
   */
  private void insertString(String str) {
	String text = input.getText();
	String newText = text.substring(0, caret) + str + text.substring(caret);
	
    input.setText(newText);

    // Position the caret after the insertion.
    input.requestFocus();
    input.positionCaret(caret + str.length());
  }

  /** Method performs check spelling routine. */
  private void checkSpelling() {
    Type result = quiz.submitAttempt(fetchInput()).getType();

    // Play result sound.
    if (result == Type.Correct) {
      Sound.play(Sound.Correct);
    } else {
      Sound.play(Sound.Incorrect);
    }

    // Update GUI.
    switch (result) {
      case Correct:
        setPrompt("Correct!", GREEN);
        updateScore();
        nextWord();
        return;
      case Reattempt:
        setPrompt("Incorrect, try once more", RED);
        giveHint();
        return;
      case Incorrect:
        // Continued below for readability.
        break;
    }

    // If the spelling is incorrect.
    switch (quiz.getMode()) {
      case Game:
        setPrompt("Incorrect", RED);
        nextWord();
        break;
      case Practice:
        setPrompt("Incorrect, but good effort.", RED);
        revealAnswer();
        break;
    }
  }

  /** Show the hint to the user. */
  private void giveHint() {
    hint.setText(quiz.getHint());
  }

  /** Reveal the answer to the user. */
  private void revealAnswer() {
    hint.setText("Answer: " + quiz.getWord());

    // Disable all buttons exception submit.
    disableInputs(true);
    submit.setDisable(false);

    // Change submit button to new 'next' word function.
    submit.setText("Next");
    submit.setOnAction(
        (event) -> {
          // Change button back to normal functionality.
          submit.setText("Submit");
          submit.setOnAction((eventInner) -> checkSpelling());
          nextWord();
        });
  }

  /** Update the score. */
  private void updateScore() {
    score.setText(String.valueOf(quiz.getScore()));
  }

  /**
   * Set the prompt message and hide after a delay.
   *
   * @param message The message to show to the user.
   * @param colour Hex colour code of the text.
   */
  private void setPrompt(String message, String colour) {
    correct.setText(message);
    correct.setStyle("-fx-text-fill: " + colour);

    SingleDelayedTask.scheduleDelayedTask(() -> correct.setText(""));
  }

  /**
   * Helper method to get text from input text field and reset it.
   *
   * @return The contents of the text field.
   */
  private String fetchInput() {
    String text = input.getText();

    input.clear();
    input.requestFocus();

    return text;
  }

  /** Quiz the next word in the wordlist. */
  private void nextWord() {
    if (quiz.isFinished()) {
      endGame();
      return;
    }

    quiz.nextWord();

    // TODO: Remove.
    System.out.println(quiz.getWord());

    // Speak the word.
    sound();

    // Reset GUI.
    input.clear();
    input.requestFocus();

    hint.setText(quiz.getBlankHint());
  }

  /** End the game and show to finish screen. */
  private void endGame() {
    // Automatically switch to finish after timeout.
    SingleDelayedTask.scheduleDelayedTask(
        () -> SceneManager.switchToFinishScene(quiz.getStats(), false));

    // Allow the user to click to finish before the timeout.
    submit.setText("Finish");
    submit.setOnAction(
        e -> {
          SingleDelayedTask.cancel();
          SceneManager.switchToFinishScene(quiz.getStats(), false);
        });

    // Only allow the finish button.
    disableInputs(true);
    submit.setDisable(false);
  }

  /**
   * Disable or enable user inputs.
   *
   * @param state If the inputs should be disabled.
   */
  private void disableInputs(boolean state) {
    // Disable buttons.
    skip.setDisable(state);
    sound.setDisable(state);
    submit.setDisable(state);
    menu.setDisable(state);
    speed.setDisable(state);

    // Disable macron buttons.
    macronButtonA.setDisable(state);
    macronButtonE.setDisable(state);
    macronButtonI.setDisable(state);
    macronButtonO.setDisable(state);
    macronButtonU.setDisable(state);

    // Disable Input
    input.setDisable(state);
    input.requestFocus();
  }
}
