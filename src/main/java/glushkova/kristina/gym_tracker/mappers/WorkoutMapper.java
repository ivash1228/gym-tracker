package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkoutMapper {
    @Mapping(source = "client.id", target = "clientId")
    public abstract WorkoutModel map(WorkoutEntity workoutEntity);
    @Mapping(source = "clientId", target = "client")
    public abstract WorkoutEntity map(WorkoutModel workoutModel);

    ClientEntity map(UUID value) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(value);
        return clientEntity;
    }
}
