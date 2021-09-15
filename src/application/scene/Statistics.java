package application.scene;

import application.Statistic;
import application.Statistics.Type;
import application.Wordlist;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Statistics menu for the user to view the number of mastered,
 * faulted, and failed attempts for each word in a wordlist.
 */
public class Statistics implements Scene {
	private Wordlist wordlist;
	
	/**
	 * Creates a statistics menu of the provided wordlist.
	 *
	 * @param wordlist Wordlist to show statistics of.
	 */
	public Statistics(Wordlist wordlist) {
		this.wordlist = wordlist;
	}
	
	/**
	 * Renders the menu scene to the screen.
	 *
	 * @param manager SceneManager of stage to render to.
	 * @return Root JavaFX node to render.
	 */
	@Override
	public Parent render(SceneManager manager) {
		Label title = UI.title("Statistics");
	
		TableView<Statistic> table = table(manager);
		
		// Navigation buttons.
		Button menu = UI.button("Menu", () -> manager.showMenu());
		Button clear = UI.button("Clear", () -> {
			application.Statistics.clear();
			// Rerender cleared statistics.
			manager.showStatistics(wordlist);
		});
		
		// Show navigation buttons horisontally.
		HBox hbox = new HBox(20, menu, clear);
		hbox.setAlignment(Pos.TOP_CENTER);
		
		// Show title, table, and buttons vertically.
		VBox vbox = new VBox(20, title, table, hbox);
		vbox.setAlignment(Pos.TOP_CENTER);
		
		return vbox;
	}
	
	/**
	 * Creates a table of statistics from the wordlist.
	 *
	 * @param manager SceneManager to display the table on.
	 * @return Table of statistics.
	 */
	private TableView<Statistic> table(SceneManager manager) {
		TableView<Statistic> table = new TableView<>();
		table.setMaxWidth(manager.getWidth() * 0.8);
		
		// Column of attempted words.
		TableColumn<Statistic, String> wordColumn = new TableColumn<>("WORD");
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("Word"));
		table.getColumns().add(wordColumn);
		
		// Create a column for each statistic type.
		for (Type type : application.Statistics.Type.values()) {
			String name = type.toString();
			
			// Construct name of property getter.
			String property = name.substring(0, 1) + name.substring(1).toLowerCase();
			
			TableColumn<Statistic, Integer> column = new TableColumn<>(name);
			column.setCellValueFactory(new PropertyValueFactory<>(property));
			table.getColumns().add(column);
		}
		
		for (String word : wordlist) {
			Statistic statistic = application.Statistics.getStatistics(word);
			
			// Only populate attempted words.
			if (statistic.getMastered() != 0
				|| statistic.getFaulted() != 0
				|| statistic.getFailed() != 0) {
				table.getItems().add(statistic);
			}
		}
		
		return table;
	}
}
