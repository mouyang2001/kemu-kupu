package application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executes tasks in the background on a thread pool.
 */
public class BackgroundExecutor {
	private static int threads = Runtime.getRuntime().availableProcessors();
	
	private static Executor threadPool = Executors.newFixedThreadPool(threads, runnable -> {
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		return thread;
	});
	
	/**
	 * Execute a runnable task in the background.
	 * 
	 * @param runnable The task to execute.
	 */
	public static void execute(Runnable runnable) {
		threadPool.execute(runnable);
	}
}
