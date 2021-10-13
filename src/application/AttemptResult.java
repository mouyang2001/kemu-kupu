package application;

/** The result of a spelling attempt by the user. */
public class AttemptResult {
  /** Result if the user is correct, should re-attempt or is incorrect. */
  public enum Type {
    Correct,
    Reattempt,
    Incorrect,
  }

  private Type type;

  private int score;

  private float time;

  /**
   * Creates a result of a spelling attempt by the user.'
   *
   * @param type The result type.
   * @param score The score the user received.
   * @param time The time it took the user to answer.
   */
  public AttemptResult(Type type, int score, float time) {
    this.type = type;
    this.score = score;
    this.time = time;
  }

  /**
   * Gets the type of spelling attempt.
   *
   * @return The type of spelling attempt.
   */
  public Type getType() {
    return type;
  }

  /**
   * Get the score the user received.
   *
   * @return The score the user received.
   */
  public int getScore() {
    return score;
  }

  /**
   * Get the time it took the user to attempt.
   *
   * @return The time it took the user to attempt.
   */
  public float getTime() {
    return time;
  }
}
