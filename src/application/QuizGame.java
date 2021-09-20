package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class QuizGame {
    private final ArrayList<String> words;

    private int index = 0;
    
    private int attempts = 0;

    /**
     * QuizGame constructor.
     * Calls Words class to retrieve words ArrayList and shuffles them.
     *
     * @param topic filename of the specific topic file.
     */
    public QuizGame(String topic) throws FileNotFoundException {
        // read words from file then rearrange in random order
    	words = Words.getWords(topic);
        Collections.shuffle(words);
    }

    /**
     * Getter method for list of words.
     *
     * @return all words as ArrayList.
     */
    public ArrayList<String> getWords() {
        return words;
    }

    /**
     * Getter method for current word.
     *
     * @return current word being tested as string.
     */
    public String getWord() {
        return words.get(index);
    }

    /**
     * Method gets the next word in words
     */
    public void nextWord() {
        if (index == words.size() - 1) {
        	index = 0;
        } else {
        	index++;
        }

        attempts = 0;
    }

    /**
     * Method gets the letter at specified index of the current word.
     *
     * @param index integer position of letter.
     * @return hint letter.
     */
    public String getHintLetterAtIndex(int index) {
        return String.valueOf(getWord().charAt(index));
    }

    /**
     * Method checks spelling of input word.
     * Status codes are as follows:
     * 1: Correct.
     * 2: Incorrect, on second attempt.
     * 3: Incorrect, on third or more attempts.
     *
     * @param spelling user inputted word.
     * @return status code number
     */
    public int checkSpelling(String spelling) {
        // Check if it is the first attempt
        if (attempts == 0) {
            // First attempt taken
            attempts++;

            // Check spelling with current word, trimming white spaces on ends and ignoring case.
            if (spelling.trim().equalsIgnoreCase(getWord())) {
            	return 1;
            } else {
            	return 2;
            }
        } else {
            if (spelling.trim().equalsIgnoreCase(getWord())) {
            	return 1;
            } else {
            	return 3;
            }
        }
    }
}
