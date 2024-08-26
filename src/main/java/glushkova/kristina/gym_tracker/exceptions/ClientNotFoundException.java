package glushkova.kristina.gym_tracker.exceptions;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(UUID id) {
        super("Client " + id + "not found!");
    }
}
