package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkoutMapper {
    public abstract WorkoutModel map(WorkoutEntity workoutEntity);
    public abstract WorkoutEntity map(WorkoutModel workoutModel);
}
