package application;

import java.util.ArrayList;
import java.util.List;

import application.Statistic.Type;

/** Stats from single spelling quiz * */
public class Statistics {
  private List<Statistic> stats = new ArrayList<>();
  
  private int score = 0;

  public void add(Statistic stat) {
	  stats.add(stat);
	  score += stat.getScore();
  }
  
  public List<Statistic> getStats() {
	  return stats;
  }
  
  public int getScore() {
	  return score;
  }
  
  public int getNumCorrect() {
	  int correct = 0;
	  
	  for (Statistic stat : stats) {
		  if (stat.getType() == Type.Correct) {
			  correct++;
		  }
	  }
	  
	  return correct;
  }
  
  public float getTotalTime() {
	  float time = 0;
	  
	  for (Statistic stat : stats) {
		  time += stat.getTime();
	  }
	  
	  return time;
  }
}
