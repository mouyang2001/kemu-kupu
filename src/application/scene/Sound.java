package application.scene;

import java.net.URL;

import javafx.scene.media.AudioClip;

public enum Sound {
  Correct,
  Incorrect,
  Complete;
	
  /**
   * Plays in/correct sound
   *
   * @param fileName of sound to play
   */
  public static void play(Sound sound) {
	String fileName = "assets/" + sound.toString().toLowerCase() + ".wav";
	URL file = Sound.class.getResource(fileName);
    new AudioClip(file.toString()).play();
  }
}
