package application;

/** A score on the leaderboard. */
public class LeaderboardScore {
  private String name;
  
  private int score;
  
  private int placing;

  /**
   * Create a score that is on the leaderboard.
   * 
   * @param name The name of the user.
   * @param score The score the user obtained.
   * @param placing The placing in the leaderboard.
   */
  public LeaderboardScore(String name, int score, int placing) {
    this.name = name;
    this.score = score;
    this.placing = placing;
  }

  /**
   * Gets the name of the user on the leaderboard.
   * 
   * @return The name of the user.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the score the user obtained.
   * 
   * @return The score the user obtained.
   */
  public int getScore() {
    return score;
  }
  
  /**
   * Gets the placing on the leaderboard.
   * 
   * @return The placing on the leaderboard.
   */
  public int getPlacing() {
    return placing;
  }
  
  /**
   * Sets the placing on the leaderboard.
   * 
   * @param placing The placing on the leaderboard.
   */
  public void setPlacing(int placing) {
	this.placing = placing;
  }
}
