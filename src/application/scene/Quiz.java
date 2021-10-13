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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/** Quiz screen that quizzes the users spelling. */
public class Quiz {
  @FXML private Label score;

  @FXML private Label correct;

  @FXML private TextField input;

  @FXML private Button sound;

  @FXML private Button macronButton;

  @FXML private ImageView image;

  @FXML private Label hint;

  @FXML private Button menu;

  @FXML private Button skip;

  @FXML private Button submit;

  private QuizGame quiz;

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
      // quit and return to menu
      SceneManager.switchToMenuScene();
    } else {
      // continue with quiz
      alert.close();
      input.requestFocus();
    }
  }

  /** Click handler for the submit button. */
  @FXML
  private void submit() {
    checkSpelling();
  }

  /** Key handler for the input text field. */
  @FXML
  private void inputKeyPressed(KeyEvent e) {
    if (e.getCode() == KeyCode.ENTER) {
      submit();
    }
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
    disableButtons(true);
    Festival.speak(quiz.getWord(), () -> disableButtons(false));
  }

  /** Click handler for the macronise button. Converts the latest character to macron. */
  @FXML
  private void macronise() {
    // Grab input if empty just return.
    String text = input.getText();
    if (text.isEmpty()) {
      return;
    }

    // Split input up into first substring and last letter, eg "welcome" -> "welcom" + "e".
    String substring = text.substring(0, text.length() - 1);
    char lastLetter = text.charAt(text.length() - 1);

    // Vowels character arrays setup.
    final String vowelsCharacters = "AEIOUaeiou";
    final String macronVowels = "ĀĒĪŌŪāēīōū";

    // Check if the last character is a vowel.
    int index = vowelsCharacters.indexOf(lastLetter);
    if (index == -1) {
      return;
    }

    // Replace the last character with the macronised vowel.
    input.setText(substring + macronVowels.charAt(index));

    // Return cursor to the end of the text field.
    input.requestFocus();
    input.end();
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
        break;
      case Practice:
        setPrompt("Incorrect, but good effort.", RED);
        revealAnswer();
        break;
    }

    nextWord();
  }

  /** Show the hint to the user. */
  private void giveHint() {
    hint.setText(quiz.getWord().length() + " letters: " + quiz.getHint());
  }

  /** Reveal the answer to the user. */
  private void revealAnswer() {
    hint.setText("Answer: " + quiz.getWord());
    SingleDelayedTask.scheduleDelayedTask(() -> hint.setText(""));
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

    hint.setText(quiz.getWord().length() + " letters");
  }

  /** End the game and show to finish screen. */
  private void endGame() {
    // Automatically switch to finish after timeout.
    SingleDelayedTask.scheduleDelayedTask(
        () -> SceneManager.switchToFinishScene(quiz.getStats(), true));

    // Allow the user to click to finish before the timeout.
    submit.setText("Finish");
    submit.setOnAction(
        e -> {
          SingleDelayedTask.cancel();
          SceneManager.switchToFinishScene(quiz.getStats(), true);
        });

    // Only allow the finish button.
    disableButtons(true);
    submit.setDisable(false);
  }

  /**
   * Disable or enable all on screen buttons.
   *
   * @param state If the buttons should be disabled.
   */
  private void disableButtons(boolean state) {
    macronButton.setDisable(state);
    skip.setDisable(state);
    sound.setDisable(state);
    submit.setDisable(state);
    menu.setDisable(state);

    input.setDisable(state);
    input.requestFocus();
  }
}
