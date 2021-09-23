package application;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Executes tasks in the background on a thread pool.
 */
public class BackgroundExecutor {
	private static int threads = Runtime.getRuntime().availableProcessors();
	
	private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(threads, runnable -> {
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
	
	/**
	 * Execute a runnable task in the background after a delay.
	 * 
	 * @param duration The time to delay the execution by.
	 * @param runnable The task to execute.
	 */
	public static void executeDelayed(Duration duration, Runnable runnable) {
		// Convert duration to nanoseconds.
		long nanoseconds = duration.getSeconds() * 1000000000 + duration.getNano();
		
		threadPool.schedule(runnable, nanoseconds, TimeUnit.NANOSECONDS);
	}
}
