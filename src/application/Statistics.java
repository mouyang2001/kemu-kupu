package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Manages the persistent statistics files of all attempts.
 */
public class Statistics {
	/**
	 * The type of each attempt.
	 */
	public enum Type {
		MASTERED,
		FAULTED,
		FAILED,
	}
	
	public static final File statsDir = new File(".stats");
	
	public static final File mistakesFile = new File(statsDir, "mistakes");
	
	/**
	 * Adds a attempt of type to the statistics of the provided word.
	 *
	 * @param type Type of attempt to add the statistic to.
	 * @param word The attempted word to add the statistic to.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void add(Type type, String word) throws IOException {
		append(filename(type), word);
		updateMistakes(type, word);
	}
	
	/**
	 * Get the statistics of a word.
	 *
	 * @param word The word to get the statistics of.
	 * @return The statistics of the provided word.
	 */
	public static Statistic getStatistics(String word) {
		int mastered = occurences(filename(Type.MASTERED), word);
		int faulted = occurences(filename(Type.FAULTED), word);
		int failed = occurences(filename(Type.FAILED), word);
			
		return new Statistic(word, mastered, faulted, failed);
	}
	
	/**
	 * Clears all statistics.
	 * Doesn't modify the mistakes.
	 */
	public static void clear() {
		for (Type type : Type.values()) {
			filename(type).delete();
		}
	}
	
	/**
	 * Gets the statistics file of a specific type.
	 *
	 * @param type The type to get the file for.
	 * @return The file of the statistic type.
	 */
	private static File filename(Type type) {
		switch (type) {
		case MASTERED:
			return new File(statsDir, "mastered");
		case FAULTED:
			return new File(statsDir, "faulted");
		case FAILED:
			return new File(statsDir, "failed");
		// This is unreachable as all cases are covered.
		// The java compiler throws a compile error without
		// a default case as it thinks the method won't
		// always return a value.
		default:
			throw new RuntimeException("Unreachable");
		}
	}
	
	/**
	 * Append a word to a statistic file.
	 *
	 * @param file The file to add the word to.
	 * @param word The word to add into the file.
	 * @throws IOException If an I/O error occurs.
	 */
	private static void append(File file, String word) throws IOException {
		// Ensure the file is created before writing.
		createFile(file);
		
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		// Write the word to its own line.
		bw.write(word + System.lineSeparator());
		
		bw.close();
	}
	
	/**
	 * Get the occurrences of a word in a statistic file.
	 *
	 * @param file The file to search.
	 * @param word The word to search for.
	 * @return The number of occurrences.
	 */
	private static int occurences(File file, String word) {
		try {
			// Open the file if it exists.
			FileInputStream stream = new FileInputStream(file);
			Scanner scanner = new Scanner(stream);
			
			int occurences = 0;
			
			// Search line by line as each word is on its own line.
			while (scanner.hasNextLine()) {
				if (scanner.nextLine().equals(word)) {
					occurences++;
				}
			}
			
			scanner.close();
			
			return occurences;
		} catch (FileNotFoundException e) {
			// Treat a non-existent file the
			// same as empty, no occurrences.
			return 0;
		}
	}
	
	/**
	 * Update the mistakes file to only contain failed
	 * words that havn't since been mastered or faulted.
	 *
	 * @param type The type of attempt to update the mistakes file for.
	 * @param word The word that was attempted.
	 * @throws IOException If an I/O error occurred.
	 */
	private static void updateMistakes(Type type, String word) throws IOException {
		switch (type) {
		case MASTERED:
		case FAULTED:
			removeMistake(word);
			break;
		// Only keep failed words
		// in the mistakes file.
		case FAILED:
			addMistake(word);
			break;
		}
	}
	
	/**
	 * Adds a word to the mistakes file if it hasn't already.
	 *
	 * @param word Word to add to the mistakes file.
	 * @throws IOException If an I/O error occurred.
	 */
	private static void addMistake(String word) throws IOException {
		// Ensure the mistakes file has each word at most once.
		if (occurences(mistakesFile, word) == 0) {
			append(mistakesFile, word);
		}
	}
	
	/**
	 * Removes a word from the mistakes file.
	 *
	 * @param word Word to remove from the mistakes file.
	 * @throws IOException If an I/O error occurred.
	 */
	private static void removeMistake(String word) throws IOException {
		// Ensure the mistakes file exists.
		createFile(mistakesFile);
		
		// Read each line, keeping lines that aren't the specified word.
		List<String> lines = Files.lines(mistakesFile.toPath())
			.filter(line -> !line.contains(word))
			.collect(Collectors.toList());
		
		// Write the remaining lines back.
		Files.write(
			mistakesFile.toPath(),
			lines,
			StandardOpenOption.WRITE,
			StandardOpenOption.TRUNCATE_EXISTING
		);
	}
	
	/**
	 * Creates a file if it doesn't already exist.
	 * Creates the necessary directories if required.
	 *
	 * @param file The file to create.
	 * @throws IOException If an I/O error occurred.
	 */
	private static void createFile(File file) throws IOException {
		// Create parent directories if they don't exist.
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		// Create a file, if it doesn't exist.
		file.createNewFile();
	}
}
