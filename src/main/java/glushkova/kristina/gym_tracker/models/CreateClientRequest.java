package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @NotBlank(message = "Email is required") @Email String email
) {}
