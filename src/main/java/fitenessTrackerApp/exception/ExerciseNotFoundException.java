package fitenessTrackerApp.exception;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(long id) {
        super("Exercise with id '" + id + "' not found.");
    }
}
