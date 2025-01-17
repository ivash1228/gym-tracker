package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.postModels.ClientRequestBody;
import glushkova.kristina.gym_tracker.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<UUID> createClient(@Valid @RequestBody ClientRequestBody.CreateClientRequest client) {
        var createdClient = clientService.createClient(client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient.id());
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @GetMapping(path = "/{clientId}")
    //do we need valid here to check if it is uuid
    public ResponseEntity<ClientModel> getClientByID(@Valid @PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(clientId));
    }

    @PutMapping(path = "/{clientId}/email")
    public ResponseEntity<ClientModel> updateClientEmail(@Valid @PathVariable UUID clientId,
                                                         @Valid @RequestBody ClientRequestBody.UpdateClientEmail clientEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClientEmail(clientId, clientEmail.getEmail()));
    }
}
