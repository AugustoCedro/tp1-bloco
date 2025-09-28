package org.example;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeTry;
import org.example.controller.ClientController;
import org.example.exception.ClientNotFoundException;
import org.example.model.Client;
import org.example.service.ClientService;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientControllerTest {

    private ClientController controller;
    private ClientService service;

    @BeforeTry
    void setup() {
        Client.resetSequence();
        service = new ClientService();
        controller = new ClientController(service);
    }

    // Arbitraries
    @Provide
    Arbitrary<Client> validClients() {
        Arbitrary<String> names = Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3)
                .ofMaxLength(20);

        Arbitrary<String> emails = Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3)
                .map(s -> s + "@test.com");

        return Combinators.combine(names, emails).as(Client::new);
    }

    @Provide
    Arbitrary<Client> invalidClients() {
        Arbitrary<String> names = Arbitraries.of("", null);
        Arbitrary<String> emails = Arbitraries.of("", null);
        return Combinators.combine(names, emails).as(Client::new);
    }

    @Provide
    Arbitrary<String> validNames() {
        return Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(20);
    }

    @Provide
    Arbitrary<String> validEmails() {
        return Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).map(s -> s + "@test.com");
    }

    @Provide
    Arbitrary<String> invalidData() {
        return Arbitraries.of("", null);
    }

    @Provide
    Arbitrary<Integer> mockedClientIds() {
        return Arbitraries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    // Testes
    @Property
    void getNotExistingClientById() {
        assertThrows(ClientNotFoundException.class, () -> controller.getClientById(-10));
    }

    @Property
    boolean createClientAndFindByIdWorks(@ForAll("validClients") Client client) {
        controller.createClient(client);
        return controller.getClientById(client.getId()) != null;
    }

    @Property
    void createClientFailed(@ForAll("invalidClients") Client client) {
        assertThrows(IllegalArgumentException.class, () -> controller.createClient(client));
    }

    @Property
    void updateClientWithValidData(@ForAll("validNames") String name,
                                   @ForAll("validEmails") String email,
                                   @ForAll("mockedClientIds") int id) {
        // Cria cliente antes de atualizar, para garantir que existe
        controller.createClient(new Client(id, name, email));
        assertDoesNotThrow(() -> controller.updateClient(new Client(id, name, email)));
    }

    @Property
    void updateClientWithInvalidData(@ForAll("invalidData") String data,
                                     @ForAll("mockedClientIds") int id) {
        controller.createClient(new Client(id, "validName", "valid@test.com")); // garante existência
        assertThrows(IllegalArgumentException.class, () -> controller.updateClient(new Client(id, data, data)));
    }

    @Property
    void deleteExistingClient() {
        Client client = new Client("teste", "teste@test.com");
        controller.createClient(client);
        int id = client.getId();

        assertDoesNotThrow(() -> controller.deleteClientById(id));
        assertThrows(ClientNotFoundException.class, () -> service.getClientById(id));
    }

    @Property
    void deleteNotExistingClient() {
        int id = 99999;
        assertThrows(ClientNotFoundException.class, () -> controller.deleteClientById(id));
    }

    @Property
    void listClientsWorks(@ForAll("validClients") Client client) {
        controller.createClient(client);
        List<Client> clients = controller.getClients();
        assertTrue(clients.contains(client));
    }
}
