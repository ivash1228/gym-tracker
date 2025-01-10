package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.ClientEntity;
import glushkova.kristina.gym_tracker.exceptions.ClientAlreadyExistsException;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.exceptions.EmailAlreadyExistsException;
import glushkova.kristina.gym_tracker.mappers.ClientMapper;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientModel createClient(String firstName, String lastName, String email, String phoneNumber) {
        var client = new ClientEntity(null, firstName, lastName, email, phoneNumber, null);
        if (clientRepository.findByEmail(email).isPresent()) throw new ClientAlreadyExistsException(email);
        return clientMapper.map(clientRepository.save(client));
    }

    public List<ClientModel> getClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::map)
                .toList();
    }

    public ClientModel getClientById(UUID uuid) {
        var client = clientRepository.findById(uuid).orElseThrow(() -> new ClientNotFoundException(uuid));
        return clientMapper.map(client);
    }

    public ClientModel updateClientEmail(UUID clientId, String email) {
        var client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        if(client.getEmail().equals(email)) throw new EmailAlreadyExistsException(email);
        return clientMapper.map(clientRepository.save(client.setEmail(email)));
    }
}
