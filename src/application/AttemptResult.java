package application;

public class AttemptResult {
  public enum Type {
    Correct,
    Reattempt,
    Incorrect,
  }
  
  private Type type;
  
  private int score;
  
  private float time;
  public AttemptResult(Type type, int score, float time) {
	  this.type = type;
	  this.score = score;
	  this.time = time;
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
