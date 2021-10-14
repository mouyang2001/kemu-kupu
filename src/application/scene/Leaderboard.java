package application.scene;

import application.Statistics;
import javafx.fxml.FXML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import application.LeaderboardControl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Leaderboard {
	private int score;
	
	private Statistics stats;
	
	@FXML Button back;
	
	@FXML Label label;
	
	@FXML GridPane table;
	
	private LeaderboardControl leaderboard;

	/**
	 * initialse label and table
	 * @param statistics
	 */
	public void initialise(Statistics statistics) {
		this.stats = statistics;
		score = statistics.getScore();
		leaderboard = new LeaderboardControl(score);
		printLabel();
		if (leaderboard.getPlace() > 4) {
			try {
				leaderboard.updateFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			table();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * helper method to populate grid view
	 * @throws FileNotFoundException
	 */
	private void table() throws FileNotFoundException {
		File file = new File("./.stats/.leaderboard.txt");
	    Scanner scanner = new Scanner(file);
		for (int i = 0; i < 3; i++) {
			String nameString = scanner.next();
			Label name;
			Label score;
		    name = new Label(nameString);
		    score = new Label(scanner.next());
			if (nameString.equals("null")) {
				name = new Label(" ");
				score = new Label(" ");
			}
		    table.add(name, 2, i);
		    table.add(score, 3, i);
		}
	    
	    scanner.close();
	    table.add(new Label("1st"), 1, 0);
	    table.add(new Label("2nd"), 1, 1);
	    table.add(new Label("3rd"), 1, 2);
	}
	
	/**
	 * Helper method to print message depending on your score
	 */
	private void printLabel() {
		int place = leaderboard.getPlace();
		if (place == 1) {
			label.setText("Congratulations! You set a new high score!");
		} else if (place == 2) {
			label.setText("Congratulations! You came 2nd!");
		} else if (place == 3) {
			label.setText("Congratulations! You came 3rd!");
		} else {
			label.setText("You didn't set a new high score :( Practise more and try again!");
		}
	}
	
	  /** Click handler to go back to finish screen. */
	  @FXML
	  private void back() {
	    SceneManager.switchToFinishScene(stats, false);
	  }

}
