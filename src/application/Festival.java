package application;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controls the festival text to speech command.
 */
public class Festival {
	/**
	 * Speaks text to the user.
	 *
	 * @param text Text to speak.
	 * @param block If the operation should block until complete.
	 * @throws IOException If an I/O error occurs.
	 * @throws InterruptedException If the current thread is interrupted while blocking.
	 */
	public static void speak(String text, boolean block) throws IOException, InterruptedException {
		// Start festival.
		Process process = new ProcessBuilder("festival", "--tts").start();

		// Send the text to standard input.
		OutputStream in = process.getOutputStream();
		PrintWriter stdin = new PrintWriter(in);
		stdin.print(text);
		stdin.close();
		
		// Block until festival has stopped speaking.
		if (block) {
			process.waitFor();
		}
	}
}
