package application;

import application.scene.SceneManager;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.application.Platform;

/** Controls the festival text to speech command. */
public class Festival {
  private static float duration = 1;

  /**
   * Set the speed the text will be spoken to the user at.
   *
   * @param speed The speed to speak the text at.
   */
  public static void setSpeed(float speed) {
    duration = 1 / speed;
  }

  /**
   * Speaks text to the user in a background thread.
   *
   * @param text The text to speak.
   */
  public static void speak(String text) {
    Festival.speak(text, () -> {});
  }

  /**
   * Speaks text to the user in a background thread.
   *
   * @param text The text to speak.
   * @param callback Callback after the word is spoken.
   */
  public static void speak(String text, Runnable callback) {
    BackgroundExecutor.execute(
        () -> {
          try {
            speakInternal(text);

            // Run callback on GUI thread.
            Platform.runLater(callback);
          } catch (IOException | InterruptedException e) {
            // Alert must be called from the GUI thread.
            Platform.runLater(() -> SceneManager.alert("Could not launch festival."));
          }
        });
  }

  /**
   * Speaks text to the user. Synchronized so festival doesn't speak over itself.
   *
   * @param text Text to speak.
   * @throws IOException If an I/O error occurs.
   * @throws InterruptedException If festival is interrupted while blocking.
   */
  public static synchronized void speakInternal(String text)
      throws IOException, InterruptedException {
    // Festival has issues with hyphens and upper case.
    text = text.replaceAll("-", " ").toLowerCase();

    // Start festival.
    ProcessBuilder builder = new ProcessBuilder("festival", "--pipe");
    Process process = builder.start();
    PrintWriter stdin = new PrintWriter(process.getOutputStream());

    // Run festival commands.
    stdin.print("(voice_akl_mi_pk06_cg)");
    stdin.print("(Parameter.set 'Duration_Stretch " + duration + ")");
    stdin.print("(SayText \"" + text + "\")");
    stdin.close();

    process.waitFor();
  }
}
