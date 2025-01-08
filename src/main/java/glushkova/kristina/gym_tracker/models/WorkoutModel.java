package glushkova.kristina.gym_tracker.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutModel {
    private UUID id;
    private UUID clientId;
    private LocalDate workoutDate;
    private String workoutName;
}


