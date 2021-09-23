package application;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/** State of a single spelling game. */
public class QuizGame {
  /** Result of an attempt. */
  public enum Result {
    Correct,
    FirstIncorrect,
    SecondIncorrect,
  };

  private final List<String> words;

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
    } else if (!wasAttempted) {
      return Result.FirstIncorrect;
    } else {
      return Result.SecondIncorrect;
    }
  }
}
