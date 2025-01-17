package glushkova.kristina.gym_tracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "workout_exercise")
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID workoutId;
    private UUID exerciseId;
    private Integer exerciseOrder;

    @OneToMany(mappedBy = "workoutExerciseEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SetEntity> exercise_sets;
}
