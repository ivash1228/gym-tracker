package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.postModels.ClientRequestBody;
import glushkova.kristina.gym_tracker.services.ClientService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Duplicate email or client")
    })
    public ResponseEntity<UUID> createClient(@Valid @RequestBody ClientRequestBody.CreateClientRequest client) {
        var createdClient = clientService.createClient(client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient.id());
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @GetMapping(path = "/{clientId}")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<ClientModel> getClientByID(@PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClientById(clientId));
    }

    @PutMapping(path = "/{clientId}/email")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    public ResponseEntity<ClientModel> updateClientEmail(@Valid @PathVariable UUID clientId,
                                                         @Valid @RequestBody ClientRequestBody.UpdateClientEmail clientEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClientEmail(clientId, clientEmail.getEmail()));
    }
}
