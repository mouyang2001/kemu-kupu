package application;

public class Highscore {
	private String name;
	private int score;

	/**
	 * 
	 * @param name of highscorer
	 * @param score 
	 */
	public Highscore(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * getter method for name
	 * @return name of highscorer
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter method for score
	 * @return score 
	 */
	public int getScore() {
		return score;
	}

	/**
	 * setter method for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
