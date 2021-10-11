package application;

import java.io.IOException;
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
  
  /** Helper method to show number of letters in word* */
  public String showLetters() {
    String word = getWord();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < word.length(); i++) {
      if (getHintLetterAtIndex(i).equals(" ")) {
        stringBuilder.append(" ");
      } else {
        stringBuilder.append("-");
      }
    }
    return stringBuilder.toString();
  }

  /**
   * Calculates the score taking into account time decay using linear model: y = c - mx.
   *
   * @param maxScore maximum possible score.
   * @param minScore minimum possible score to prevent negative values.
   * @param timeLimit seconds allowed before we just give minimum score by default.
   * @param elapsedTime seconds taken to answer.
   * @return
   */
  public int calculateScore(int maxScore, int minScore, int timeLimit, float elapsedTime) {
    int calScore = (int) (maxScore - (maxScore / timeLimit) * elapsedTime);
    return Math.max(minScore, calScore);
  }
}
