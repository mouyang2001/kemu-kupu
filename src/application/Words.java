package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/** Controls interactions with word/topic files in the words folder. */
public class Words {
  private static final Path WORDS_DIR = Paths.get("words");

  /**
   * Grabs string ArrayList of all the topics.
   *
   * @return List of topic filenames.
   * @throws IOException If topics files could not be found.
   */
  public static List<String> getTopics() throws IOException {
    return Files.list(WORDS_DIR)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
  }

  /**
   * Grabs string ArrayList of all the topics.
   *
   * @param topic filename of the specific topic file.
   * @return List of words in the specified topic file.
   * @throws IOException If the topic file could not be loaded.
   */
  public static List<String> getWords(String topic) throws IOException {
    return Files.lines(WORDS_DIR.resolve(topic)).collect(Collectors.toList());
  }
}
