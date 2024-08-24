package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.NotBlank;

public record PostClientRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName
) {}
