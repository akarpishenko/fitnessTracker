package fitenessTrackerApp.exception;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException(long id) {
        super("Workout with id '" + id + "' not found.");
    }
}
