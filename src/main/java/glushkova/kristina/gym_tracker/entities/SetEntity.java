package glushkova.kristina.gym_tracker.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "exercise_set")
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    Integer weights;
    Integer reps;
    Integer setOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExerciseEntity workoutExerciseEntity;
}
