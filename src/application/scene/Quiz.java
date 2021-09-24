package application.scene;

import application.BackgroundExecutor;
import application.Festival;
import application.QuizGame;

import java.io.IOException;
import java.time.Duration;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/** Quiz screen that quizzes the users spelling. */
public class Quiz {
  @FXML private Label score;

  @FXML private Label correct;

  @FXML private TextField input;

  @FXML private Label hint;

  @FXML private Button sound;

  private QuizGame quiz;

  private int scoreVal = 0;

  private int currentRound = 1;

  private Task<Void> delayedTask;

  private final String RED = "#E88787";

  private final String GREEN = "#9AF1A3";

  private final int NUMBER_OF_ROUNDS = 5;

  /**
   * Method allows SceneManager to access and transfer data topic selection data. Also it's the
   * entry point to start the quiz game.
   *
   * @param topic The chosen topic, file name.
   */
  public void beginQuiz(String topic) {
    try {
      // begin new QuizGame instance
      quiz = new QuizGame(topic);
    } catch (IOException e) {
      SceneManager.alert("Could not load word list.");
    }

    // start off with the first word
    Festival.speak(quiz.getWord());

    // DEBUG
    System.out.println(quiz.getWord());
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

  /**
   * Click handler for the submit button.
   *
   * @param e Event action information.
   * @throws IOException If FXML or CSS resources fail to load.
   */
  @FXML
  public void submit(ActionEvent e) {
    checkSpelling();
  }

  /**
   * Click handler for the skip button. Tells quiz object to go to the next word.
   *
   * @param e Event action information.
   */
  @FXML
  public void skip(ActionEvent e) {
    setPrompt("Incorrect :(", RED);
    nextWord();
  }

  /**
   * Click handler for the sound button. Gets festival to say the word.
   *
   * @param e Event action information.
   */
  @FXML
  public void sound(ActionEvent e) {
    sound.setDisable(true);
    Festival.speak(quiz.getWord(), () -> sound.setDisable(false));
  }

  /** Method performs check spelling routine. */
  private void checkSpelling() {
    // Retrieve input in text field.
    String spelling = fetchInput();

    // Check spelling of word and proceed according to the case.
    switch (quiz.checkSpelling(spelling)) {
      case Correct:
        increaseScore();
        setPrompt("Correct", GREEN);
        nextWord();
        break;

      case FirstIncorrect:
        setPrompt("Incorrect, try again", RED);
        giveHint();
        Festival.speak(quiz.getWord());
        break;

      case SecondIncorrect:
        setPrompt("Incorrect :(", RED);
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

    delayTask(Duration.ofSeconds(3), () -> correct.setText(""));
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
    hint.setText("Hint: second letter is " + quiz.getHintLetterAtIndex(1));
  }

  /** Helper method to jump to next word and reset UI elements. */
  private void nextWord() {
    // If NUMBER_OF_ROUNDS reached then switch to finish.
    if (currentRound == NUMBER_OF_ROUNDS) {
      delayTask(Duration.ofSeconds(3), () -> SceneManager.switchToFinishScene(scoreVal));
      return;
    }

    // Increase current round count.
    currentRound++;

    // Clear hint and reset focus to input.
    hint.setText("");
    input.requestFocus();

    // Get next word.
    quiz.nextWord();
    System.out.println(quiz.getWord());

    // Festival say word.
    Festival.speak(quiz.getWord());
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
