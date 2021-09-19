package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Controls interactions with word/topic files in the words folder.
 */
public class Words {

    public static final String wordsPath = "words/";

    /**
     * Grabs string ArrayList of all the topics.
     *
     * @return String ArrayList of topic filenames.
     */
    public static ArrayList<String> getTopics() {
        ArrayList<String> topics = new ArrayList<>();

        File folder = new File(wordsPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                topics.add(listOfFiles[i].getName());
            }
        }

        return topics;
    }

    /**
     * Grabs string ArrayList of all the topics.
     *
     * @param topic filename of the specific topic file.
     * @return String ArrayList of words in the specified topic file.
     */
    public static ArrayList<String> getWords(String topic) throws FileNotFoundException {
        // array to keep track of the words read in the file
        ArrayList<String> words = new ArrayList<>();

        // scan through each line storing the word in that line to an array
        Scanner scanner = new Scanner(new File(wordsPath + topic));
        while(scanner.hasNextLine()) {
            words.add(scanner.nextLine());
        }

        return words;
    }
}
