package glushkova.kristina.gym_tracker.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String email) {
        super("Client with email " + email + " already exists!");
    }
}
