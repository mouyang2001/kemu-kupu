package application;

/**
 * A statistic of the number of mastered,
 * faulted, and failed attempts at at word.
 */
public class Statistic {
	private String word;
	
	private int mastered;
	
	private int faulted;
	
	private int failed;
	
	/**
	 * Creates a statistic of a word with the specified attempts.
	 *
	 * @param word Word to create the statistic for.
	 * @param mastered Number of mastered attempts at the word.
	 * @param faulted Number of faulted attempts at the word.
	 * @param failed Number of failed attempts at the word.
	 */
	public Statistic(String word, int mastered, int faulted, int failed) {
		this.word = word;
		this.mastered = mastered;
		this.faulted = faulted;
		this.failed = failed;
	}
	
	/**
	 * Gets the word of the statistic.
	 *
	 * @return The word of the statistic.
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Gets the number of mastered attempts.
	 *
	 * @return The number of mastered attempts.
	 */
	public int getMastered() {
		return mastered;
	}
	
	/**
	 * Gets the number of faulted attempts.
	 *
	 * @return The number of faulted attempts.
	 */
	public int getFaulted() {
		return faulted;
	}
	
	/**
	 * Gets the number of failed attempts.
	 *
	 * @return The number of failed attempts.
	 */
	public int getFailed() {
		return failed;
	}
}
