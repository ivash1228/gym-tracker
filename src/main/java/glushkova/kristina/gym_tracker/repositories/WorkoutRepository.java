package glushkova.kristina.gym_tracker.repositories;

import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
}
