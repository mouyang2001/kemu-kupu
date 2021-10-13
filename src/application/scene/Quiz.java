package application.scene;

import application.Festival;
import application.QuizGame;
import application.QuizGame.Mode;
import application.SingleDelayedTask;
import java.io.IOException;
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

  // Game Only.
  @FXML private Label showLetters;

  @FXML private Button sound;

  @FXML private Button macronButton;

  @FXML private ImageView image;
  
  @FXML private Label hint;

  @FXML private Button skip;

  @FXML private Button submit;
  
  // Practice Only.
  @FXML private Button quit;

  private QuizGame quiz;

  private static final String RED = "#E88787";

  private static final String GREEN = "#9AF1A3";

  /**
   * Method allows SceneManager to access and transfer data topic selection data. Also it's the
   * entry point to start the quiz game.
   *
   * @param topic The chosen topic, file name.
   */
  public void beginQuiz(String topic, Mode mode) {
    // Begin new QuizGame instance.
    try {
      quiz = new QuizGame(topic, mode);
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
            submit();
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
  
  @FXML
  private void quit() {
    SceneManager.switchToTopicScene();
  }

  /** Method performs check spelling routine. */
  private void checkSpelling() {
    // Retrieve input in text field.
    String spelling = fetchInput();
    
    switch (quiz.submitAttempt(spelling).getType()) {
	    case Correct:
	    	increaseScore();
	        setPrompt("Correct!", GREEN);
	        nextWord();
	    	return;
	    case Reattempt:
	    	setPrompt("Try once more", RED);
	        giveHint();
	    	return;
	    case Incorrect:
	    	break;
    }
    
    switch (quiz.getMode()) {
	    case Game:
	    	setPrompt("Incorrect, but good effort.", RED);
	    	break;
	    case Practice:
	        setPrompt("Incorrect", RED);
	        revealAnswer();
	        nextWord();
	    	break;
    }
  }
  
  /** Helper method to show the hint to the user. */
  private void giveHint() {
    hint.setText(quiz.getWord().length() + " letters: " + quiz.getHint());
  }

  /** Helper method to reveal the answer to the user. */
  private void revealAnswer() {
    hint.setText("Answer: " + quiz.getWord());
    SingleDelayedTask.scheduleDelayedTask(() -> hint.setText(""));
  }

  /** Helper method to show number of letters in word */
  private void showLetters() {
	if (quiz.getMode() == Mode.Practice) {
		return;
	}
	  
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
    showLetters.setText(promptLetters);
  }

  /** Helper method to increase score and update label. */
  private void increaseScore() {
    score.setText(String.valueOf(quiz.getScore()));
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

    SingleDelayedTask.scheduleDelayedTask(() -> correct.setText(""));
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
    if (quiz.isFinished()) {
      endGame();
      return;
    }

    // Clear hint and reset focus to input.
    input.requestFocus();

    // Get next word.
    quiz.nextWord();

    // Speak the word.
    sound();

    // Show hint if in a game.
    if (quiz.getMode() == Mode.Game) {
	  showLetters();
    }
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
    macronButton.setDisable(state);
    
    input.setDisable(state);
    input.requestFocus();
    
    if (quiz.getMode() == Mode.Practice) {
    	quit.setDisable(state);    	
    }
  }

  /** End of game subroutine. */
  public void endGame() {
    // Automatically switch to finish after timeout.
    SingleDelayedTask.scheduleDelayedTask(() -> SceneManager.switchToFinishScene(quiz.getStats()));

    // Allow the user to click to finish before the timeout.
    submit.setText("Finish");
    submit.setOnAction(
        e -> {
          SingleDelayedTask.cancel();
          SceneManager.switchToFinishScene(quiz.getStats());
        });

    // Only allow the finish button.
    disableButtons(true);
    submit.setDisable(false);
  }
}
