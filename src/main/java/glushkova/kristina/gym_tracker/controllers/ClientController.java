package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.PostClientRequest;
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
    public ResponseEntity<UUID> createClient(@Valid @RequestBody PostClientRequest client) {
        //check if exists
        var uuid = clientService.createClient(client.firstName(), client.lastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientModel> getClientByID(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(id));
    }


}
