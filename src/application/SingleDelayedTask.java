package application;

import java.time.Duration;
import javafx.application.Platform;
import javafx.concurrent.Task;

/** Schedule tasks to be executed after a delay. Only one task can be scheduled at a time. */
public class SingleDelayedTask {
  private static final Duration DELAY = Duration.ofSeconds(2);

  private static Task<Void> delayedTask;

  /**
   * Runs a task after the specified delay. If a task is already scheduled it will be canceled.
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

  /**
   * Cancel the delayed task.
   *
   * @return If the task was canceled.
   */
  public static boolean cancel() {
    if (delayedTask == null) {
      return false;
    }

    return delayedTask.cancel();
  }
}
