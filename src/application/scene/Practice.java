package application.scene;

import application.BackgroundExecutor;
import application.Festival;
import application.QuizGame;
import application.Statistics;

import java.io.IOException;
import java.time.Duration;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Practice {
  @FXML private Label score;

  @FXML private Label correct;

  @FXML private TextField input;

  @FXML private Button sound;

  @FXML private Button macronButton;

  @FXML private ImageView image;

  @FXML private Label hint;

  @FXML private Button skip;

  @FXML private Button submit;

  @FXML private Button quit;

  private QuizGame quiz;

  private int scoreVal = 0;

  private int currentRound = 0;

  private Task<Void> delayedTask;

  private final String RED = "#E88787";

  private final String GREEN = "#9AF1A3";

  private final double HINT_REVEAL_PERCENTAGE = 0.3;

  private final int DELAY = 2;

  private Boolean firstAttempt = true;
  
  private Statistics stats;

  /**
   * Method allows SceneManager to access and transfer data topic selection data. Also it's the
   * entry point to start the quiz game.
   *
   * @param topic The chosen topic, file name.
   */
  public void beginPractice(String topic) {
    // Begin new QuizGame instance.
    try {
      quiz = new QuizGame(topic);
    } catch (IOException e) {
      SceneManager.alert("Could not load word list.");
    }

    // Kick off the first word.
    nextWord();
  }

  /** Scene initializer, for setting unique listener events on elements. */
  @FXML
  public void initialize() {
    // Input TextField will listen to enter button pressed event.
    input.setOnKeyReleased(
        e -> {
          if (e.getCode() == KeyCode.ENTER) {
            checkSpelling();
          }
        });
  }

  /** Click handler for the submit button. */
  @FXML
  private void submit() {
    checkSpelling();
  }

  /** Click handler for the skip button. Tells quiz object to go to the next word. */
  @FXML
  private void skip() {
    setPrompt("Skipped", RED);
    revealAnswer();
    nextWord();
  }

  @FXML
  private void quit() {
    SceneManager.switchToTopicScene();
  }

  /** Click handler for the sound button. Gets festival to say the word. */
  @FXML
  private void sound() {
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
    // Retrieve input in text field.
    String spelling = fetchInput();

    // Check spelling of word and proceed according to the case.
    switch (quiz.checkSpelling(spelling)) {
      case Correct:
        increaseScore();
        setPrompt("Correct!", GREEN);
        nextWord();
        break;
      case Incorrect:
        if (firstAttempt) {
          firstAttempt = false;
          setPrompt("Try once more", RED);
          giveHint();
          break;
        }
        setPrompt("Incorrect", RED);
        revealAnswer();
        nextWord();
        break;
    }
  }

  /** Helper method to increase score and update label. */
  private void increaseScore() {
    scoreVal++;
    score.setText(String.valueOf(scoreVal));
  }

  /**
   * Helper method to set prompt message.
   *
   * @param message of what we want to set in prompt
   * @param colour hex code of colour to set the message
   */
  private void setPrompt(String message, String colour) {
    correct.setText(message);
    correct.setStyle("-fx-text-fill: " + colour);

    delayTask(Duration.ofSeconds(DELAY), () -> correct.setText(""));
  }

  /**
   * Helper method to get text from input TextField and reset it.
   *
   * @return text that player inputted into the TextField input.
   */
  private String fetchInput() {
    String text = input.getText();
    input.clear();
    input.requestFocus();
    return text;
  }

  /** Helper method to show the hint to the user. */
  private void giveHint() {
    hint.setText(quiz.getWord().length() + " letters: " + quiz.getHint(HINT_REVEAL_PERCENTAGE));
  }

  /** Helper method to reveal the answer to the user. */
  private void revealAnswer() {
    System.out.println("answer");
    hint.setText("Answer: " + quiz.getWord());
    delayTask(Duration.ofSeconds(DELAY), () -> hint.setText(""));
  }

  /** Helper method to jump to next word and reset UI elements. */
  private void nextWord() {
    firstAttempt = true;

    disableButtons(true);

    // Reset focus to input.
    input.requestFocus();

    // Get next word.
    quiz.nextWord();

    // After festival says the word, enable the buttons again.
    Festival.speak(quiz.getWord(), () -> disableButtons(false));
  }

  /**
   * Helper method to Toggle buttons
   *
   * @param state boolean value on or off button.
   */
  public void disableButtons(Boolean state) {
    skip.setDisable(state);
    submit.setDisable(state);
    quit.setDisable(state);
    sound.setDisable(state);
    input.setDisable(state);
    input.requestFocus();
  }

  /** End of game subroutine. */
  public void endGame() {
    // Automatically switch to finish after timeout.
    delayTask(Duration.ofSeconds(DELAY), () -> SceneManager.switchToFinishScene(scoreVal, stats));

    // Allow the user to click to finish before the timeout.
    submit.setText("Finish");
    submit.setOnAction(
        e -> {
          delayedTask.cancel();
          SceneManager.switchToFinishScene(scoreVal, stats);
        });

    // Only allow the finish button.
    submit.setDisable(false);
    sound.setDisable(true);
    skip.setDisable(true);
    input.setDisable(true);
  }

  /**
   * Runs a task after the specified delay. Only one task will be in-flight, prioritising newer
   * tasks.
   *
   * @param duration The duration to delay by.
   * @param task The task to run after the delay.
   */
  private void delayTask(Duration duration, Runnable task) {
    // Cancel the in-flight task.
    if (delayedTask != null && !delayedTask.isDone()) {
      delayedTask.cancel();
    }

    // Create the new task.
    delayedTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            if (!isCancelled()) {
              // Run on the GUI thread.
              Platform.runLater(task);
            }

            return null;
          }
        };

    // Schedule the task for execution.
    BackgroundExecutor.executeDelayed(duration, delayedTask);
  }
}
