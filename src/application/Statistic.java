package application;

public class Statistic {
  public enum Type {
    Correct,
    Incorrect,
    Skipped,
  };
  
  private String word;
  
  private Type type;
  
  private int score;
  
  private float time;
  
  public Statistic(String word, Type type, int score, float time) {
	  this.word = word;
	  this.type = type;
	  this.score = score;
	  this.time = time;
  }
  
  public String getWord() {
	  return word;
  }
  
  public Type getType() {
	  return type;
  }
  
  public int getScore() {
	  return score;
  }
  
  public float getTime() {
	  return time;
  }
}
