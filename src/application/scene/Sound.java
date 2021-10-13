package application.scene;

import java.net.URL;
import javafx.scene.media.AudioClip;

/** Plays sound effects in the background. */
public enum Sound {
  Correct,
  Incorrect,
  Complete;

  /**
   * Plays the sound effect.
   *
   * @param sound The sound effect to play.
   */
  public static void play(Sound sound) {
    String fileName = "assets/" + sound.toString().toLowerCase() + ".wav";
    URL file = Sound.class.getResource(fileName);
    new AudioClip(file.toString()).play();
  }
}
