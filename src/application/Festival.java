package application;

import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Platform;

/**
 * Controls the festival text to speech command.
 */
public class Festival {
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
		BackgroundExecutor.execute(() -> {
			try {
				speakInternal(text);
				Platform.runLater(callback);
			} catch (IOException | InterruptedException e) {
				// TODO: Error handling.
			}
		});
	}
	
	/**
	 * Speaks text to the user.
	 * Synchronized so festival doesn't speak over itself.
	 * 
	 * @param text Text to speak.
	 * @throws IOException If an I/O error occurs.
	 * @throws InterruptedException If festival is interrupted while blocking.
	 */
	public synchronized static void speakInternal(String text) throws IOException, InterruptedException {
		// Start festival.
		ProcessBuilder builder = new ProcessBuilder("festival", "--pipe");
		Process process = builder.start();
		PrintWriter stdin = new PrintWriter(process.getOutputStream());
		
		// Run festival commands.
		stdin.print(
			"(voice_akl_mi_pk06_cg)" +
			"(SayText \"" + text + "\")"
		);
		
		stdin.close();
		
		process.waitFor();
	}
}
