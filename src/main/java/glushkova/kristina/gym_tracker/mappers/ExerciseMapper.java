package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.ExerciseEntity;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ExerciseMapper {
    public abstract ExerciseModel map(ExerciseEntity exerciseEntity);
    public abstract ExerciseEntity map(ExerciseModel exerciseModel);
}
