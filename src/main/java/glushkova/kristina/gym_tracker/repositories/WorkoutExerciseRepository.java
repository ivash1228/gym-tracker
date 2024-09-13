package glushkova.kristina.gym_tracker.repositories;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseEntity, UUID> {
    List<WorkoutExerciseEntity> findByWorkoutId(UUID workoutId);
}
