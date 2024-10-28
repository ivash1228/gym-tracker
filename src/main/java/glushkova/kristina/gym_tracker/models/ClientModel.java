package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record ClientModel (
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {}
