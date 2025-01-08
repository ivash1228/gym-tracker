package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.SetEntity;
import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.models.SetModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SetMapper {
    @Mapping(source = "workoutExerciseEntity.id", target = "workoutExerciseId")
    public abstract SetModel map(SetEntity setEntity);
    @Mapping(source = "workoutExerciseId", target = "workoutExerciseEntity.id")
    public abstract SetEntity map(SetModel setModel);

    WorkoutExerciseEntity map(UUID value) {
        WorkoutExerciseEntity workoutExerciseEntity = new WorkoutExerciseEntity();
        workoutExerciseEntity.setId(value);
        return workoutExerciseEntity;
    }


}
