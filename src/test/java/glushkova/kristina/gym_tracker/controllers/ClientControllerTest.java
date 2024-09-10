package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.exceptions.ClientAlreadyExistsException;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.CreateClientRequest;
import glushkova.kristina.gym_tracker.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ClientService clientService;

    @Test
    void createClient_WhenValidRequestBodyProvided_ShouldCreateClient() throws Exception {
        var uuid = UUID.randomUUID();
        var requestBody = new CreateClientRequest("First", "Last", "test@email.com");

        when(clientService.createClient(requestBody.firstName(), requestBody.lastName(), requestBody.email()))
                .thenReturn(uuid);

        mockMvc.perform(post("/clients")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(uuid)));
    }

    @ParameterizedTest
    @MethodSource
    void createClient_WhenInvalidRequestBodyProvided_ShouldThrowBadRequest(CreateClientRequest createClientRequest) throws Exception {
        mockMvc.perform(post("/clients")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createClient_WhenClientWithProvidedEmailExists_ShouldReturn509Conflict() throws Exception {
        var requestBody = new CreateClientRequest("First", "Last", "test@email.com");

        when(clientService.createClient(requestBody.firstName(), requestBody.lastName(), requestBody.email()))
                .thenThrow(new ClientAlreadyExistsException(requestBody.email()));

        mockMvc.perform(post("/clients")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Client with email %s already exists!".formatted(requestBody.email())));
    }

    @Test
    void getClients_WhenRequestSent_ShouldReturnAllClients() throws Exception {
        var listClients = List.of(new ClientModel(UUID.randomUUID(), "Sam", "White", "test@email.com"));

        when(clientService.getClients()).thenReturn(listClients);

        mockMvc.perform(get("/clients")
                .with(jwt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listClients)));
    }

    @Test
    void getClientById_WhenValidIdIsProvided_ShouldReturnClientById() throws Exception {
        var uuid = UUID.randomUUID();
        var client = new ClientModel(uuid,"First", "Last", "test@email.com");

        when(clientService.getClientById(uuid)).thenReturn(client);

        mockMvc.perform(get("/clients/" + uuid)
                .with(jwt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(client)));
    }

    @Test
    void getClientById_WhenThereIsNoClientFoundByProvidedId_ShouldReturn404NotFoundStatus() throws Exception {
        var uuid = UUID.randomUUID();
        when(clientService.getClientById(uuid)).thenThrow(new ClientNotFoundException(uuid));

        mockMvc.perform(get("/clients/" + uuid)
                .with(jwt()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client %s not found!".formatted(uuid)));
    }

    @Test
    void getClientById_WhenInvalidUuidValueProvided_ShouldReturn400BadRequest() throws Exception {
        var invalidUuid = "1234";

        mockMvc.perform(get("/clients/" + invalidUuid).with(jwt()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; Invalid UUID string: %s"
                                .formatted(invalidUuid)));
    }

    private static Stream<CreateClientRequest> createClient_WhenInvalidRequestBodyProvided_ShouldThrowBadRequest() {
        return Stream.of(
                new CreateClientRequest(null, "Last", "test@mail.com"),
                new CreateClientRequest("First", null, "test@mail.com"),
                new CreateClientRequest(null, "Last", null),
                new CreateClientRequest("", "Last", "test@mail.com"),
                new CreateClientRequest("First", "", "test@mail.com"),
                new CreateClientRequest("First", "Last", ""),
                //new CreateClientRequest("First", "Last", "test@mail")//,
                new CreateClientRequest("First", "Last", "testmail.com")
        );
    }
}