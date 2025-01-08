package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.exceptions.ClientAlreadyExistsException;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
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
        var client = new ClientModel(null, firstName, lastName, email, phoneNumber, null);
        if (clientRepository.findByEmail(email).isPresent()) throw new ClientAlreadyExistsException(email);
        var result = clientRepository.save(clientMapper.map(client));
        return clientMapper.map(result);
    }

    public List<ClientModel> getClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::map)
                .toList();
    }

    public ClientModel getClientById(UUID id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return clientMapper.map(client);
    }
}
