package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.mappers.ClientMapper;
import glushkova.kristina.gym_tracker.mappers.ClientMapperImpl;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ClientMapper clientMapper = new ClientMapperImpl();
    private final ClientService clientService = new ClientService(clientRepository, clientMapper);

    @Test
    void createClient() {
        var id = UUID.randomUUID();
        ClientModel client = new ClientModel(id,
                "Sam", "White", "test@email.com");
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);

        when(clientRepository.save(any())).thenReturn(clientEntity);

        assertEquals(clientService.createClient(client.firstName(), client.lastName(), client.email()), id);
        verify(clientRepository).save(any());
    }

    @Test
    void getClients() {
        var clients = List.of(new ClientEntity()
                , new ClientEntity());

        when(clientRepository.findAll()).thenReturn(clients);

        assertEquals(clientService.getClients().size(), clients.size());
        verify(clientRepository).findAll();
    }
}