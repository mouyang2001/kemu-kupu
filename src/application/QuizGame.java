package application;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import application.AttemptResult.Type;

/** State of a single spelling game. */
public class QuizGame {
  /** Mode to run the quiz in. */
  public enum Mode {
	  Practice,
	  Game,
  }
  
  private final List<String> words;

  private int index = 0;
  
  private Mode mode;
  
  private long startTime;
  
  private boolean firstAttempt = true;
  
  Statistics stats = new Statistics();
  
  private final double HINT_REVEAL_PERCENTAGE = 0.3;

  private int currentRound = 0;

  private final int NUMBER_OF_ROUNDS = 5;  
  
  private int score = 0;
  
  private final int MAX_SCORE = 1000;

  private final int MIN_SCORE = 100;

  private final int MAX_TIME = 10;

  /**
   * QuizGame constructor. Calls Words class to retrieve words ArrayList and shuffles them.
   *
   * @param topic filename of the specific topic file.
   * @throws IOException If an I/O error occurred.
   */
  public QuizGame(String topic, Mode mode) throws IOException {
    // read words from file then rearrange in random
    // order
    words = Words.getWords(topic);
    Collections.shuffle(words);
    
    this.mode = mode;
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
    index %= words.size();
    
    startTime = System.nanoTime();
    firstAttempt = true;
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
  
  public AttemptResult submitAttempt(String spelling) {
	  float time = calculateTime();
	  
	  Type type;
	  if (checkSpelling(spelling)) {
		  type = Type.Correct;
	  } else if (mode == Mode.Practice && firstAttempt) {
		  type = Type.Reattempt;
		  firstAttempt = false;
	  } else {
		  type = Type.Incorrect;
	  }
	  
	  if (type != Type.Reattempt) {
		  currentRound++;
	  }
	  
	  AttemptResult result = new AttemptResult(type, calculateScore(type, time));
	  
	  addStat(result, time);
	  
	  return result;
  }

  /**
   * Method checks spelling of input word.
   *
   * @param spelling user inputted word.
   * @return attempt result.
   */
  private boolean checkSpelling(String spelling) {
    // Check spelling with current word, trimming
    // white spaces on ends and ignoring case.
    return spelling.trim().equalsIgnoreCase(getWord());
  }
  
  private void addStat(AttemptResult result, float time) {
	  application.Statistic.Type type;
	  switch (result.getType()) {
	  case Correct:
		  type = application.Statistic.Type.Correct;
		  break;
	  case Incorrect:
		  type = application.Statistic.Type.Incorrect;
		  break;
	  default:
		  return;
	  }
	  
	  stats.add(new Statistic(getWord(), type, result.getScore(), time));
  }
  
  public void skip() {
	  stats.add(new Statistic(getWord(), application.Statistic.Type.Skipped, -1, calculateTime()));
  }

  /**
   * Method randomly builds and returns a hint with a random mix of characters and '_' (blanks).
   * NOTE it will show at least one letter by default because of the nature of randomness, we don't
   * want blanks.
   *
   * @return hint as a string.
   */
  public String getHint() {
    StringBuilder hint = new StringBuilder();

    for (int i = 0; i < getWord().length(); i++) {
      // Chance of being a blank. But must show second letter at least.
      if (Math.random() > HINT_REVEAL_PERCENTAGE && i != 1) {
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
  
  public Mode getMode() {
	  return mode;
  }
  
  private float calculateTime() {
	  long timeElapsedNanoseconds = System.nanoTime() - startTime;
	  
	  // Convert elapsed time into seconds.
	  return (float)timeElapsedNanoseconds / 1000000000;
  }

  /**
   * Calculates the score taking into account time decay using linear model: y = c - mx.
   * 
   * @return 
   */
  private int calculateScore(Type type, float time) {
	if (type != Type.Correct) {
		return 0;
	}
	  
	int calScore = (int)(MAX_SCORE - (MAX_SCORE / MAX_TIME) * time);
	return Math.max(MIN_SCORE, calScore);
  }
  
  public int getScore() {
	  return score;
  }
  
  public boolean isFinished() {
	  return mode == Mode.Game && currentRound == NUMBER_OF_ROUNDS;
  }
  
  public Statistics getStats() {
	  return stats;
  }
}
