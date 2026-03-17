package fitenessTrackerApp.exception;

public class RunNotFoundException extends RuntimeException {
    public RunNotFoundException(long id) {
        super("Run with id '" + id + "' not found.");
    }
}
