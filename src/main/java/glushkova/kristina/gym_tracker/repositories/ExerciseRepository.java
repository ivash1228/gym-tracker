package glushkova.kristina.gym_tracker.repositories;

import glushkova.kristina.gym_tracker.entities.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> {
}
