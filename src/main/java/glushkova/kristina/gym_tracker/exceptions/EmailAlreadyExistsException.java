package glushkova.kristina.gym_tracker.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("Email already exists: " + message );
    }
}
