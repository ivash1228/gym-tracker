package glushkova.kristina.gym_tracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "workout_exercise")
public class WorkoutExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID workoutId;
    private UUID exerciseId;
    private Integer exerciseOrder;
    private Integer sets;
    private Integer weights;
    private Integer repsCount;
}
