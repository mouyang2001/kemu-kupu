package application;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/** State of a single spelling game. */
public class QuizGame {
  /** Mode to run the quiz in. */
  public enum Mode {
    Practice,
    Game,
  }

  private final List<String> words;

  private int index = 0;

  private int round = 0;

  private Mode mode;

  private long startTime;

  private boolean firstAttempt = true;

  Statistics statistics;

  public static final double HINT_REVEAL_PERCENTAGE = 0.3;

  public static final int NUMBER_OF_ROUNDS = 5;

  private int score = 0;

  public static final int MAX_SCORE = 1000;

  public static final int MIN_SCORE = 100;

  public static final int MAX_TIME = 10;

  /**
   * Creates a quiz game to quiz the user on their spelling.
   *
   * @param topic Filename of the topic file.
   * @throws IOException If an I/O error occurred.
   */
  public QuizGame(String topic, Mode mode) throws IOException {
    // Read and randomise the wordlist.
    words = Words.getWords(topic);
    Collections.shuffle(words);

    this.mode = mode;
    statistics = new Statistics(topic);
  }

  /**
   * Get the number of rounds in the quiz.
   *
   * @return The number of rounds in quiz.
   */
  public int getNumberOfRounds() {
    return NUMBER_OF_ROUNDS;
  }

  /**
   * Get the current round in the quiz.
   *
   * @return The current round of the quiz.
   */
  public int getCurrentRound() {
    return round;
  }

  /**
   * Get the mode of the quiz.
   *
   * @return The mode of the quiz.
   */
  public Mode getMode() {
    return mode;
  }

  /**
   * Get the score for the quiz.
   *
   * @return The score for the quiz.
   */
  public int getScore() {
    return score;
  }

  /**
   * If the quiz is finished.
   *
   * @return If the quiz is finished.
   */
  public boolean isFinished() {
    return statistics.getStats().size() == NUMBER_OF_ROUNDS;
  }

  /**
   * Get the statistics of the quiz.
   *
   * @return The statistics for the quiz.
   */
  public Statistics getStats() {
    return statistics;
  }

  /**
   * Gets the current word being quizzed.
   *
   * @return The current word being quizzed.
   */
  public String getWord() {
    return words.get(index);
  }

  /**
   * Gets a letter from the current word being quizzed.
   *
   * @param index The index of the letter to get.
   * @return The letter from the current word at the index.
   */
  public String getHintLetterAtIndex(int index) {
    return String.valueOf(getWord().charAt(index));
  }

  /** Sets the state of the game to the next word. */
  public void nextWord() {
    // Move to the next word.
    round++;
    index++;
    index %= words.size();

    // Reset attempt state.
    startTime = System.nanoTime();
    firstAttempt = true;
  }

  /**
   * Submit the users attempt at spelling the current word.
   *
   * @param spelling The spelling form the user.
   * @return The result of the attempt.
   */
  public AttemptResult submitAttempt(String spelling) {
    float time = calculateTime();

    // Determine if the user is correct or should re-attempt.
    AttemptResult.Type type;
    if (checkSpelling(spelling)) {
      type = AttemptResult.Type.Correct;
    } else if (mode == Mode.Practice && firstAttempt) {
      type = AttemptResult.Type.Reattempt;
      firstAttempt = false;
    } else {
      type = AttemptResult.Type.Incorrect;
    }

    // Increase score.
    int attemptScore = calculateScore(type, time);
    score += attemptScore;

    AttemptResult result = new AttemptResult(type, attemptScore, time);

    // Statistics are only required for games.
    if (mode == Mode.Game) {
      addStat(result);
    }

    return result;
  }

  /**
   * Compare the spelling to the current word.
   *
   * @param spelling The spelling to compare to the current word.
   * @return If the spelling is correct.
   */
  private boolean checkSpelling(String spelling) {
    // Case in-sensitive compare without leading and trailing whitespace.
    return spelling.trim().equalsIgnoreCase(getWord());
  }

  /**
   * Add a statistic to the game statistics.
   *
   * @param result The result of the attempt.
   */
  private void addStat(AttemptResult result) {
    Statistic.Type type;
    switch (result.getType()) {
      case Correct:
        type = Statistic.Type.Correct;
        break;
      case Incorrect:
        type = Statistic.Type.Incorrect;
        break;
      default:
        return;
    }

    statistics.add(new Statistic(getWord(), type, result.getScore(), result.getTime()));
  }

  /** Skips the current word. */
  public void addCurrentWordToSkipped() {
    statistics.add(new Statistic(getWord(), Statistic.Type.Skipped, 0, calculateTime()));
  }

  /**
   * Method randomly builds and returns a hint with a random mix of characters and '_' (blanks).
   * NOTE it will show at least one letter by default because of the nature of randomness, we don't
   * want blanks.
   *
   * @return Hint of the current word.
   */
  public String getHint() {
    StringBuilder hint = new StringBuilder();

    for (int i = 0; i < getWord().length(); i++) {
      // Chance of being a blank. But must show second letter at least.
      if (Math.random() > HINT_REVEAL_PERCENTAGE && i != 1) {
        hint.append("_");
      } else {
        hint.append(getHintLetterAtIndex(i));
      }

      // Give some spacing so you can see the '_' more clearly.
      hint.append(" ");
    }

    return hint.toString();
  }

  /**
   * Creates a template of the word using underscores to represent each non-space character in the
   * current word.
   *
   * @return Blank hint of the current word.
   */
  public String getBlankHint() {
    StringBuilder hint = new StringBuilder();

    for (char character : getWord().toCharArray()) {
      if (character == ' ') {
        hint.append(" ");
      } else {
        hint.append("_");
      }

      // Give some spacing so you can see the '_' more clearly.
      hint.append(" ");
    }

    return hint.toString();
  }

  /**
   * Calculate the time since the current word was selected.
   *
   * @return The elapsed time in seconds.
   */
  private float calculateTime() {
    long timeElapsedNanoseconds = System.nanoTime() - startTime;

    // Convert elapsed time into seconds.
    return (float) timeElapsedNanoseconds / 1000000000;
  }

  /**
   * Calculates the score of an attempt. Uses the linear time decay model: y = c - mx.
   *
   * @param type The result type from the attempt.
   * @param time The time in seconds the attempt took.
   * @return The score for the attempt.
   */
  private int calculateScore(AttemptResult.Type type, float time) {
    // Incorrect or re-attempted answers get no points.
    if (type != AttemptResult.Type.Correct || !firstAttempt) {
      return 0;
    }

    // Only one point for correct answers in practice mode.
    if (mode == Mode.Practice) {
      return 1;
    }

    int calScore = (int) (MAX_SCORE - (MAX_SCORE / MAX_TIME) * time);
    return Math.max(MIN_SCORE, calScore);
  }
}
