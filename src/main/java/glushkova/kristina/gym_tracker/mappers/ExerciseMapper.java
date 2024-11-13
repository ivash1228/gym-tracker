package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.ExerciseEntity;
import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ExerciseMapper {
    //@Mapping(source = "workout.id", target = "workoutId")
    public abstract ExerciseModel map(ExerciseEntity exerciseEntity);
    //@Mapping(source = "workoutId", target = "workout.id")
    public abstract ExerciseEntity map(ExerciseModel exerciseModel);

//    WorkoutEntity map(UUID value) {
//        WorkoutEntity workoutEntity = new WorkoutEntity();
//        workoutEntity.setId(value);
//        return workoutEntity;
//    }
}
