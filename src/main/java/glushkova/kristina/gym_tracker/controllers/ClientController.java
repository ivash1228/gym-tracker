package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public void createClient(@RequestBody ClientModel client) {
        //check if exists
        clientService.createClient(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

}
