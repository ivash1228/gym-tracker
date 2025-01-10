package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.exceptions.ClientAlreadyExistsException;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.exceptions.EmailAlreadyExistsException;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.postModels.ClientRequestBody;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    ClientRequestBody.CreateClientRequest
            createClientRequest = new ClientRequestBody.CreateClientRequest
            ("First", "Last", "test@email.com", "+1-222-222-2222");
    UUID uuid = UUID.randomUUID();
    ClientModel clientModel = new ClientModel(uuid,"First", "Last", "test@email.com", "+1-222-222-2222", null);

    @Test
    void createClient_WhenValidRequestBodyProvided_ShouldCreateClient() throws Exception {

        when(clientService.createClient(createClientRequest.getFirstName(), createClientRequest.getLastName(), createClientRequest.getEmail(), createClientRequest.getPhoneNumber()))
                .thenReturn(clientModel);

        mockMvc.perform(post("/clients")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(uuid)));
    }

    @ParameterizedTest
    @MethodSource
    void createClient_WhenInvalidRequestBodyProvided_ShouldThrowBadRequest(ClientRequestBody.CreateClientRequest createClientRequest) throws Exception {
        mockMvc.perform(post("/clients")
                .with(jwt())
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createClient_WhenClientWithProvidedEmailExists_ShouldReturn509Conflict() throws Exception {
        when(clientService.createClient(createClientRequest.getFirstName(), createClientRequest.getLastName(), createClientRequest.getEmail(), createClientRequest.getPhoneNumber()))
                .thenThrow(new ClientAlreadyExistsException(createClientRequest.getEmail()));

        mockMvc.perform(post("/clients")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Client with email %s already exists!".formatted(createClientRequest.getEmail())));
    }

    @Test
    void getClients_WhenRequestSent_ShouldReturnAllClients() throws Exception {
        var listClients = List.of(clientModel);

        when(clientService.getClients()).thenReturn(listClients);

        mockMvc.perform(get("/clients")
                .with(jwt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listClients)));
    }

    @Test
    void getClientById_WhenValidIdIsProvided_ShouldReturnClientById() throws Exception {
        when(clientService.getClientById(uuid)).thenReturn(clientModel);

        mockMvc.perform(get("/clients/" + uuid)
                .with(jwt())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(clientModel)));
    }

    @Test
    void getClientById_WhenThereIsNoClientFoundByProvidedId_ShouldReturn404NotFoundStatus() throws Exception {
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

    @Test
    void createClient_401() throws Exception {

        mockMvc.perform(post("/clients").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getClientById_401() throws Exception {
        mockMvc.perform(get("/clients/12").with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getClients_401() throws Exception {
        mockMvc.perform(get("/clients").with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateClientEmail_WhenValidIdAndEmailIsProvided_ShouldUpdateClientEmail() throws Exception {
        var uuid = UUID.randomUUID();
        var newEmail = "newEmail@test.com";
        var emailUpdateRequest = new ClientRequestBody.UpdateClientEmail(newEmail);
        var expected = new ClientModel(uuid, clientModel.firstName(), clientModel.lastName(), emailUpdateRequest.getEmail(), clientModel.phoneNumber(), null);

        when(clientService.updateClientEmail(uuid, newEmail)).thenReturn(expected);

        mockMvc.perform(put("/clients/" + uuid)
                .with(jwt())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmail)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void updateClientEmail_WhenExistingEmailIsProvided_ShouldThrow409Conflict() throws Exception {
        var emailUpdateRequest = new ClientRequestBody.UpdateClientEmail("alreadyExistingEmail@test.com");

        when(clientService.updateClientEmail(uuid, emailUpdateRequest.getEmail())).thenThrow(new EmailAlreadyExistsException(emailUpdateRequest.getEmail()));

        mockMvc.perform(put("/clients/" + uuid)
                .with(jwt())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailUpdateRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(
                        "Email already exists: %s"
                                .formatted(emailUpdateRequest.getEmail())));
    }

    private static Stream<ClientRequestBody.CreateClientRequest> createClient_WhenInvalidRequestBodyProvided_ShouldThrowBadRequest() {
        return Stream.of(
                new ClientRequestBody.CreateClientRequest
                        (null, "Last", "test@mail.com", "+1-222-222-2222"),
                new ClientRequestBody.CreateClientRequest
                        ("First", null, "test@mail.com", "+1-222-222-2222"),
                new ClientRequestBody.CreateClientRequest
                        (null, "Last", null, "+1-222-222-2222"),
                new ClientRequestBody.CreateClientRequest
                        ("", "Last", "test@mail.com", "+1-222-222-2222"),
                new ClientRequestBody.CreateClientRequest
                        ("First", "", "test@mail.com", "+1-222-222-2222"),
                new ClientRequestBody.CreateClientRequest
                        ("First", "Last", "", "+1-222-222-2222"),
                //new ClientRequestBody.CreateClientRequest
                // ("First", "Last", "test@mail")//,
                new ClientRequestBody.CreateClientRequest
                        ("First", "Last", "test@mail.com", "+12222222222"),
                new ClientRequestBody.CreateClientRequest
                        ("First", "Last", "test@mail.com", "+1-222-222-22"),
                new ClientRequestBody.CreateClientRequest
                        ("First", "Last", "test@mail.com", null),
                new ClientRequestBody.CreateClientRequest
                        ("First", "Last", "test@mail.com", "")
        );
    }
}