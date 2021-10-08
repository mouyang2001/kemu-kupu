package application.scene;

import application.BackgroundExecutor;
import application.Festival;
import application.QuizGame;
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

/** Quiz screen that quizzes the users spelling. */
public class Quiz {
  @FXML private Label score;

  @FXML private Label correct;

  @FXML private TextField input;

  @FXML private Label showLetters;

  @FXML private Button sound;

  @FXML private Button macronButton;

  @FXML private ImageView image;

  @FXML private Button skip;

  @FXML private Button submit;

  private QuizGame quiz;

  private int scoreVal = 0;

  private int currentRound = 0;

  private Task<Void> delayedTask;

  private long timeStart;

  private long timeEnd;

  private long timeElapsed;

  private final String RED = "#E88787";

  private final String GREEN = "#9AF1A3";

  private final int NUMBER_OF_ROUNDS = 5;

  private final int DELAY = 2;

  /**
   * Method allows SceneManager to access and transfer data topic selection data. Also it's the
   * entry point to start the quiz game.
   *
   * @param topic The chosen topic, file name.
   */
  public void beginQuiz(String topic) {
    // Begin new QuizGame instance.
    try {
      quiz = new QuizGame(topic);
      //stats = new Statistics();
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
            timeEnd = System.nanoTime();
            calculateTime();
            checkSpelling();
          }
        });
  }

  /** Click handler for the submit button. */
  @FXML
  private void submit() {
    timeEnd = System.nanoTime();
    calculateTime();
    checkSpelling();
  }

  /** Click handler for the skip button. Tells quiz object to go to the next word. */
  @FXML
  private void skip() {
    setPrompt("Skipped", RED);
    timeEnd = System.nanoTime();
    calculateTime();
    //stats.setScore(-1);
    nextWord();
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
        setPrompt("Incorrect, but good effort.", RED);
        //quiz.score(0);
        nextWord();
        break;
    }
  }

  /** Helper method to show number of letters in word* */
  private void showLetters() {
    String word = quiz.getWord();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < word.length(); i++) {
      if (quiz.getHintLetterAtIndex(i).equals(" ")) {
        stringBuilder.append(" ");
      } else {
        stringBuilder.append("-");
      }
    }
    String promptLetters = stringBuilder.toString();
    // input.setPromptText(promptLetters);
    showLetters.setText(promptLetters);
  }

  /** Helper method to increase score and update label. */
  private void increaseScore() {
	  int thisScore;
	  if (timeElapsed < 3000000) {
		  thisScore = 5;
	  } else if (timeElapsed < 4000000) {
		  thisScore = 4;
	  } else if (timeElapsed < 5000000) {
		  thisScore = 3;
	  } else if (timeElapsed < 6000000) {
		  thisScore = 2;
	  } else {
		  thisScore = 1;
	  }
	  scoreVal += thisScore;
	  //quiz.score(thisScore);
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

  /** Helper method to calculate time taken to answer question */
  private void calculateTime() {
    long time = timeEnd - timeStart;
    timeElapsed = time / 1000;
    //quiz.timeTaken(timeElapsed);
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

  /** Helper method to jump to next word and reset UI elements. */
  private void nextWord() {
    disableButtons(true);

    // If NUMBER_OF_ROUNDS reached then switch to finish.
    if (currentRound == NUMBER_OF_ROUNDS) {
      endGame();
      return;
    }

    // Increase current round count.
    currentRound++;

    // Clear hint and reset focus to input.
    input.requestFocus();

    // Get next word.
    quiz.nextWord();

    // After festival says the word, enable the buttons again.
    Festival.speak(quiz.getWord(), () -> disableButtons(false));
    showLetters();
    timeStart = System.nanoTime();
  }

  /**
   * Helper method to Toggle buttons
   *
   * @param state boolean value on or off button.
   */
  public void disableButtons(Boolean state) {
    skip.setDisable(state);
    submit.setDisable(state);
    sound.setDisable(state);
    input.setDisable(state);
    input.requestFocus();
  }

  /** End of game subroutine. */
  public void endGame() {
    // Automatically switch to finish after timeout.
    delayTask(Duration.ofSeconds(DELAY), () -> SceneManager.switchToFinishScene(scoreVal));

    // Allow the user to click to finish before the timeout.
    submit.setText("Finish");
    submit.setOnAction(
        e -> {
          delayedTask.cancel();
          SceneManager.switchToFinishScene(scoreVal);
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
