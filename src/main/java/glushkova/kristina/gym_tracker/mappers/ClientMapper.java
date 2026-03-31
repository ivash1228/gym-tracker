package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.models.ClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = WorkoutMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ClientMapper {

    @Mapping(source = "workouts", target = "workoutModelList")
    public abstract ClientModel map(ClientEntity clientEntity);
    @Mapping(source = "workoutModelList", target = "workouts")
    public abstract ClientEntity map(ClientModel clientModel);
}
