package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controls interactions with word/topic files in the words folder.
 */
public class Words {

    /**
     * Grabs string ArrayList of all the topics.
     *
     * @param folderPath Specifies path to words folder.
     */
    public static ArrayList<String> getTopics(String folderPath) {
        ArrayList<String> topics = new ArrayList<>();

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                topics.add(listOfFiles[i].getName());
            }
        }

        return topics;
    }
}
