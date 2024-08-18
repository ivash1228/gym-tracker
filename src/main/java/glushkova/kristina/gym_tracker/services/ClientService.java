package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.mappers.ClientMapper;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public void createClient(ClientModel client) {
        clientRepository.save(clientMapper.map(client));
    }

    public List<ClientModel> getClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::map)
                .toList();
    }
}
