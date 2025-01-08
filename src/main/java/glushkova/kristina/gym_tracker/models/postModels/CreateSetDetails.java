package glushkova.kristina.gym_tracker.models.postModels;

import jakarta.validation.constraints.NotNull;

public record CreateSetDetails(
        @NotNull(message = "weights cannot be null")
        Integer weights,
        @NotNull(message = "reps cannot be null")
        Integer reps
) {}
