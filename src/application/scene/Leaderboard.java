package application.scene;

import application.Statistics;

public class Leaderboard {
	private int score;

	public void initialise(Statistics statistics) {
		score = statistics.getScore();
		
	}

}
