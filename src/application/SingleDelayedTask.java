package application;

import java.time.Duration;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class SingleDelayedTask {
  private static final Duration DELAY = Duration.ofSeconds(2);
	
  private static Task<Void> delayedTask;
	
  /**
   * Runs a task after the specified delay. Only one task will be in-flight, prioritising newer
   * tasks.
   *
   * @param duration The duration to delay by.
   * @param task The task to run after the delay.
   */
  public static void scheduleDelayedTask(Runnable task) {
    // Cancel the in-flight task.
    if (delayedTask != null && !delayedTask.isDone()) {
      delayedTask.cancel();
    }

    // Create the new task.
    delayedTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            if (!isCancelled()) {
              // Run on the GUI thread.
              Platform.runLater(task);
            }

            return null;
          }
        };

    // Schedule the task for execution.
    BackgroundExecutor.executeDelayed(DELAY, delayedTask);
  }
  
  public static boolean cancel() {
	  return delayedTask.cancel();
  }
}
