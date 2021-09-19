package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Controls the festival text to speech command.
 */
public class Festival {
	private static final Path festivalScheme = Paths.get(".festival/output.scm");
	
	/**
	 * Speaks text to the user in a background thread.
	 *
	 * @param text The text to speak.
	 */
	public static void speak(String text) {
		BackgroundExecutor.execute(() -> {
			try {
				speakInternal(text);
			} catch (IOException | InterruptedException e) {
				// TODO: Error handling.
			}
		});
	}
	
	/**
	 * Speaks text to the user.
	 * Synchronized to stop file conflicts and so festival doesn't speak over itself.
	 * 
	 * @param text Text to speak.
	 * @throws IOException If an I/O error occurs.
	 * @throws InterruptedException If festival is interrupted while blocking.
	 */
	private synchronized static void speakInternal(String text) throws IOException, InterruptedException {
		// Create scheme file parent directory (if needed).
		Files.createDirectories(festivalScheme.getParent());
		
		// Create the festival scheme file.
		List<String> lines = Arrays.asList(
			"(voice_cmu_us_awb_cg)",
			"(SayText \"" + text + "\")"
		);
		
		Files.write(festivalScheme, lines);
		
		// Run festival.
		ProcessBuilder builder = new ProcessBuilder("festival", "-b", festivalScheme.toAbsolutePath().toString());
		Process process = builder.start();
		
		process.waitFor();
	}
}
