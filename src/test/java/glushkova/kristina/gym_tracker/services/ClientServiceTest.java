package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.exceptions.ClientAlreadyExistsException;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.exceptions.EmailAlreadyExistsException;
import glushkova.kristina.gym_tracker.mappers.ClientMapper;
import glushkova.kristina.gym_tracker.mappers.ClientMapperImpl;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ClientMapper clientMapper = new ClientMapperImpl();
    private final ClientService clientService = new ClientService(clientRepository, clientMapper);

    UUID uuid = UUID.randomUUID();
    ClientModel clientModel = new ClientModel(uuid,
            "Sam", "White", "test@email.com", "+1-333-333-4444", null);

    @Test
    void createClient_WhenHappyPath_ShouldReturnUuidOfNewRecord() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(uuid);
        clientEntity.setFirstName("Sam");
        clientEntity.setLastName("White");
        clientEntity.setEmail("test@email.com");
        clientEntity.setPhoneNumber("+1-333-333-4444");

        when(clientRepository.save(any())).thenReturn(clientEntity);

        assertEquals(clientService.createClient(clientModel.firstName(), clientModel.lastName(), clientModel.email(), clientModel.phoneNumber()), clientModel);
        verify(clientRepository).save(any());
    }

    @Test
    void createClient_WhenClientAlreadyExists_ThrowClientAlreadyExistsException() {
        var email = "test@email.com";
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(new ClientEntity()));
        var exception = assertThrows(ClientAlreadyExistsException.class, () -> clientService.createClient("First", "Last", email, "+1-333-333-4444"));

        assertEquals("Client with email %s already exists!".formatted(email), exception.getMessage());
        verify(clientRepository).findByEmail(email);
    }

    @Test
    void getAllClients_WhenHappyPath_ShouldGetAllClients() {
        var clients = List.of(new ClientEntity()
                , new ClientEntity());

        when(clientRepository.findAll()).thenReturn(clients);

        assertEquals(clientService.getClients().size(), clients.size());
        verify(clientRepository).findAll();
    }

    @Test
    void getClientById_WhenHappyPath_ShouldGetClientById() {
        var repositoryResponse = Optional.of(new ClientEntity());
        when(clientRepository.findById(uuid)).thenReturn(repositoryResponse);

        assertEquals(clientService.getClientById(uuid), clientMapper.map(repositoryResponse.get()));
        verify(clientRepository).findById(uuid);
    }

    @Test
    void getClientById_WhenNoClientExistsWithProvidedUuid_shouldThrowClientNotFoundException() {
        when(clientRepository.findById(uuid)).thenReturn(Optional.empty());

        var exception = assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(uuid));
        assertEquals("Client %s not found!".formatted(uuid), exception.getMessage());
        verify(clientRepository).findById(uuid);
    }

    @Test
    void updateClientEmail_WithValidClientIdAndValidEmail_shouldUpdateClientEmail() {
        String email = "expected@email.com";
        ClientEntity expected = new ClientEntity();
        expected.setId(uuid);
        expected.setFirstName("Sam");
        expected.setLastName("White");
        expected.setEmail(email);

        var savedClient = new ClientEntity();
        savedClient.setId(uuid);
        savedClient.setFirstName("Sam");
        savedClient.setLastName("White");
        savedClient.setEmail("savedEmail@email.com");

        when(clientRepository.findById(uuid)).thenReturn(Optional.of(savedClient));

        clientService.updateClientEmail(uuid, email);
        verify(clientRepository).save(expected);
    }

    @Test
    void updateClientEmail_WithValidClientIdAndValidEmail_shouldReturnUpdatedClient() {
        String email = "expected@email.com";
        ClientEntity expected = new ClientEntity();
        expected.setId(uuid);
        expected.setFirstName("Sam");
        expected.setLastName("White");
        expected.setEmail(email);

        var savedClient = new ClientEntity();
        savedClient.setId(uuid);
        savedClient.setFirstName("Sam");
        savedClient.setLastName("White");
        savedClient.setEmail("savedEmail@email.com");

        when(clientRepository.findById(uuid)).thenReturn(Optional.of(savedClient));
        when(clientRepository.save(expected)).thenReturn(expected);

        var result = clientService.updateClientEmail(uuid, email);
        assertEquals(clientMapper.map(expected), result);
    }

    @Test
    void updateClientEmail_whenClientNotFound_shouldThrowClientNotFoundException() {

        when(clientRepository.findById(uuid)).thenReturn(Optional.empty());
        var exception = assertThrows(ClientNotFoundException.class, () -> clientService.updateClientEmail(uuid, "notfound@mail.com"));
        assertEquals("Client %s not found!".formatted(uuid), exception.getMessage());
    }

    @Test
    void updateClientEmail_whenEmailAlreadyExists_shouldThrowEmailExistsException() {
        var savedClient = new ClientEntity(uuid, "Sam", "White", "existingEmail@test.com", "+1-333-333-4444", null);
        when(clientRepository.findById(uuid)).thenReturn(Optional.of(savedClient));

        assertThrows(EmailAlreadyExistsException.class, () -> clientService.updateClientEmail(uuid, "existingEmail@test.com"));
    }
}