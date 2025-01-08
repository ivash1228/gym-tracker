package glushkova.kristina.gym_tracker.repositories;

import glushkova.kristina.gym_tracker.entities.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SetRepository extends JpaRepository<SetEntity, UUID> {
    List<SetEntity> findByWorkoutExerciseEntity_Id(UUID workoutExerciseEntityId);
}
