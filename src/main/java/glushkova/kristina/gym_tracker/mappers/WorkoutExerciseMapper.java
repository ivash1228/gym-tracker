package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkoutExerciseMapper {
    public abstract WorkoutExerciseModel map(WorkoutExerciseEntity workoutExerciseEntity);
    public abstract WorkoutExerciseEntity map(WorkoutExerciseModel workoutExerciseModel);
}