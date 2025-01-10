package glushkova.kristina.gym_tracker.models.postModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ClientRequestBody {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateClientEmail {
            @Email
            @NotBlank(message = "Email is required")
            String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateClientRequest {
        @NotBlank(message = "First name is required")
        String firstName;
        @NotBlank(message = "Last name is required")
        String lastName;
        @NotBlank(message = "Email is required")
        @Email
        String email;
        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\+1-\\d{3}-\\d{3}-\\d{4}", message = "Phone number format is +1-###-###-####")
        String phoneNumber;
    }
}
