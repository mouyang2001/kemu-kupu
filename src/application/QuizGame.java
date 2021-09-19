package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class QuizGame {

    private final ArrayList<String> words;
    private int index = 0;
    private int attempts = 0;

    public QuizGame(String topic) throws FileNotFoundException {
        // read words from file then rearrange in random order
        this.words = Words.getWords(topic);
        Collections.shuffle(this.words);
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public String getWord() {
        return this.words.get(this.index);
    }

    public void nextWord() {
        if (index == this.words.size()-1) this.index = 0;
        else this.index++;
        this.attempts = 0;
    }

    public String getHintLetterAtIndex(int index) {
        return String.valueOf(this.getWord().charAt(index));
    }

    public int checkSpelling(String spelling) {
        if (attempts == 0) {
            attempts++;

            if (spelling.trim().equalsIgnoreCase(this.getWord())) return 1;
            else return 2;
        } else {
            if (spelling.trim().equalsIgnoreCase(this.getWord())) return 1;
            else return 3;
        }
    }
}
