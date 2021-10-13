package application;

/** A statistic in the quiz statistics. */
public class Statistic {
  /** If the user was correct, incorrect, or skipped the attempt. */
  public enum Type {
    Correct,
    Incorrect,
    Skipped,
  };

  private String word;

  private Type type;

  private int score;

  private float time;

  /**
   * Create a statistic with the specified word, type, score, and time.
   *
   * @param word The word that was attempted.
   * @param type The type of statistic.
   * @param score The score the user got.
   * @param time The time it took for the user to attempt.
   */
  public Statistic(String word, Type type, int score, float time) {
    this.word = word;
    this.type = type;
    this.score = score;
    this.time = time;
  }

  /**
   * Get the word that was attempted.
   *
   * @return The word that was attempted.
   */
  public String getWord() {
    return word;
  }

  /**
   * Get the type of statistic.
   *
   * @return The type of statistic.
   */
  public Type getType() {
    return type;
  }

  /**
   * Get the score the user got.
   *
   * @return The score the user got.
   */
  public int getScore() {
    return score;
  }

  /**
   * Get the time the user took to attempt.
   *
   * @return The time the user took to attempt.
   */
  public float getTime() {
    return time;
  }
}
