package application;

import java.util.ArrayList;
import java.util.List;

import application.Statistic.Type;

/** Statistics from single spelling quiz * */
public class Statistics {
  private List<Statistic> stats = new ArrayList<>();
  
  private int score = 0;

  /**
   * Add a statistic to the statistics.
   * 
   * @param stat The statistic to add.
   */
  public void add(Statistic stat) {
	  stats.add(stat);
	  score += stat.getScore();
  }
  
  /**
   * Get all the stored statistics.
   * 
   * @return The stored statistics.
   */
  public List<Statistic> getStats() {
	  return stats;
  }
  
  /**
   * Get the total score from all attempts.
   * 
   * @return The total score.
   */
  public int getScore() {
	  return score;
  }
  
  /**
   * Get the number of correct attempts.
   * 
   * @return The number of correct attempts.
   */
  public int getNumCorrect() {
	  int correct = 0;
	  
	  for (Statistic stat : stats) {
		  if (stat.getType() == Type.Correct) {
			  correct++;
		  }
	  }
	  
	  return correct;
  }
  
  /**
   * Get the total time of all attempts.
   * 
   * @return The total time.
   */
  public float getTotalTime() {
	  float time = 0;
	  
	  for (Statistic stat : stats) {
		  time += stat.getTime();
	  }
	  
	  return time;
  }
}
