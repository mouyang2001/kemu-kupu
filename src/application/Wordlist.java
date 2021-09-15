package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * List of words for quizzing a user the spelling of.
 * Allows up to 3 words to be randomly accessed for quizzing.
 */
public class Wordlist implements Iterable<String> {
	private int size;
	
	private ArrayList<String> words;
	
	private ArrayList<Integer> usedIndicies = new ArrayList<>();
	
	/**
	 * Creats a wordlist from the given words.
	 *
	 * @param words Words to include in the wordlist.
	 */
	public Wordlist(ArrayList<String> words) {
		this.size = Math.min(3, words.size());
		this.words = words;
	}
	
	/**
	 * Loads a wordlist from a file.
	 *
	 * @param file File path to load the wordlist from.
	 * @throws FileNotFoundException If the file cannot be opened.
	 */
	public Wordlist(String file) throws FileNotFoundException {
		// Open the file if it exists.
		FileInputStream stream = new FileInputStream(file);
		Scanner scanner = new Scanner(stream);
		
		words = new ArrayList<>();
		
		while (scanner.hasNextLine()) {
			// Read one word per line.
			words.add(scanner.nextLine());
		}
		
		size = Math.min(3, words.size());
		
		scanner.close();
	}
	
	/**
	 * Gets a unique random word from the wordlist.
	 *
	 * @return The unique random word from the wordlist.
	 * @throws NoWordsException If there are no more unique words.
	 */
	public String getRandomWord() throws NoWordsException {
		// Throw if the maximum number of words has been reached.
		if (getUsed() == size) {
			throw new NoWordsException();
		}

		while (true) {
			int index = ThreadLocalRandom.current().nextInt(0, words.size());
			
			// Ensure the random index is unique.
			if (!usedIndicies.contains(index)) {
				usedIndicies.add(index);
				
				return words.get(index);
			}
		}
	}
	
	/**
	 * Gets the size of the wordlist.
	 *
	 * @return The size of the wordlist.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gets the number of randomly quizzed words.
	 *
	 * @return The number of randomly quizzed words.
	 */
	public int getUsed() {
		return usedIndicies.size();
	}

	/**
	 * Gets an iterator to all words in the wordlist.
	 *
	 * @return Iterator to all words in the wordlist.
	 */
	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}
}
