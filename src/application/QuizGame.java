package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** State of a single spelling game. */
public class QuizGame {
  /** Result of an attempt. */
  public enum Result {
    Correct,
    Incorrect,
  };

  private final List<String> words;
  
  private List<Integer> correct;
  
  private List<Long> time;

  private int index = 0;

  private boolean attempted = false;

  /**
   * QuizGame constructor. Calls Words class to retrieve words ArrayList and shuffles them.
   *
   * @param topic filename of the specific topic file.
   * @throws IOException If an I/O error occured.
   */
  public QuizGame(String topic) throws IOException {
    // read words from file then rearrange in random
    // order
    words = Words.getWords(topic);
    Collections.shuffle(words);
    
    correct = new ArrayList<Integer>();
    time = new ArrayList<Long>();
  }

  /**
   * Getter method for list of words.
   *
   * @return List of shuffled words.
   */
  public List<String> getWords() {
    return words;
  }

  /**
   * method to store scores of words tested
   *
   * @param score of word just tested 0 if incorrect 1 if correct -1 if skipped
   */
  public void isCorrect(int score) {
	  correct.add(score);
  }

  /**
   * method to store time taken to answer each question
   *
   * @param time taken to answer question
   */
  public void timeTaken(long timeTaken) {
	  time.add(timeTaken);
  }

  /**
   * Getter method for current word.
   *
   * @return current word being tested as string.
   */
  public String getWord() {
    return words.get(index);
  }

  /** Sets the state of the game to the next word. */
  public void nextWord() {
    index++;
    // If word list size reached, flip over to beginning again.
    if (index == words.size()) index = 0;
    attempted = false;
  }

  /**
   * Method gets the letter at specified index of the current word.
   *
   * @param index integer position of letter.
   * @return hint letter.
   */
  public String getHintLetterAtIndex(int index) {
    return String.valueOf(getWord().charAt(index));
  }

  /**
   * Method checks spelling of input word.
   *
   * @param spelling user inputted word.
   * @return attempt result.
   */
  public Result checkSpelling(String spelling) {
    boolean wasAttempted = attempted;

    attempted = true;

    // Check spelling with current word, trimming
    // white spaces on ends and ignoring
    // case.
    if (spelling.trim().equalsIgnoreCase(getWord())) {
      return Result.Correct;
    } else {
      return Result.Incorrect;
    }
  }

  /**
   * Method randomly builds and returns a hint with a random mix of characters and '_' (blanks).
   * NOTE it will show at least one letter by default because of the nature of randomness, we don't
   * want blanks.
   *
   * @param percentage specifies percentage of letters to blank out.
   * @return hint as a string.
   */
  public String getHint(double percentage) {
    StringBuilder hint = new StringBuilder();

    for (int i = 0; i < getWord().length(); i++) {
      // Chance of being a blank. But must show second letter at least.
      if (Math.random() > percentage && i != 1) {
        hint.append("_");
      } else {
        String currentLetter = getHintLetterAtIndex(i);
        hint.append(currentLetter);
      }

      // Give some spacing so you can see the '_' more clearly.
      hint.append(" ");
    }

    return hint.toString();
  }
}
