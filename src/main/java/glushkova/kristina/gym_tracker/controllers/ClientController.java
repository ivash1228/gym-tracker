package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.CreateClientRequest;
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
    public ResponseEntity<UUID> createClient(@Valid @RequestBody CreateClientRequest client) {
        var uuid = clientService.createClient(client.firstName(), client.lastName(), client.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @GetMapping(path = "/{id}")
    //do we need valid here to check if it is uuid
    public ResponseEntity<ClientModel> getClientByID(@Valid @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(id));
    }
}
