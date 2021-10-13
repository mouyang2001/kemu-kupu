package application;

public class AttemptResult {
  public enum Type {
    Correct,
    Reattempt,
    Incorrect,
  }
  
  private Type type;
  
  private int score;
  
  public AttemptResult(Type type, int score) {
	  this.type = type;
	  this.score = score;
  }
  
  public Type getType() {
	  return type;
  }
  
  public int getScore() {
	  return score;
  }
}
