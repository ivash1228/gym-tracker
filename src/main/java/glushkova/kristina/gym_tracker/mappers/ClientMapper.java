package glushkova.kristina.gym_tracker.mappers;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.models.ClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ClientMapper {
    public abstract ClientModel map(ClientEntity clientEntity);
    public abstract ClientEntity map(ClientModel clientModel);
}
