package application;

/** Stats from single spelling quiz * */
public class Statistics {

  private int[] scores;

  private long[] times;

  private String[] words;

  private static int index;

  /** Statistics object constructor */
  public Statistics() {
    scores = new int[5];
    times = new long[5];
    words = new String[5];
    index = -1;
  }

  /** increase index position */
  public void increaseIndex() {
    index++;
  }

  /**
   * set word currently being tested
   *
   * @param word
   */
  public void setWord(String word) {
    words[index] = word;
  }

  /**
   * set score of word just tested
   *
   * @param score
   */
  public void setScore(int score) {
    scores[index] = score;
  }

  /**
   * set time taken to answer word just tested
   *
   * @param time
   */
  public void setTime(long time) {
    times[index] = time;
  }

  /**
   * getter method for list of words
   *
   * @return array of words
   */
  public String[] getWords() {
    return words;
  }

  /**
   * getter method for list of scores
   *
   * @return array of scores
   */
  public int[] getScores() {
    return scores;
  }

  /**
   * getter method for list of times
   *
   * @return array of times
   */
  public long[] getTimes() {
    return times;
  }
}
