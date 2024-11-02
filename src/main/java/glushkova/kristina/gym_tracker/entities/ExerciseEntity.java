package glushkova.kristina.gym_tracker.entities;

import glushkova.kristina.gym_tracker.models.ExerciseType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "exercise")
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private ExerciseType type;
}
