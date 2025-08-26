package org.example;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeTry;
import org.example.controller.ClientController;
import org.example.exception.ClientNotFoundException;
import org.example.model.Client;
import org.example.service.ClientService;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientControllerTest {

    ClientController controller;

    @BeforeTry
    void setup() {
        Client.resetSequence();
        controller = new ClientController();
    }
    @Provide
    Arbitrary<Client> validClients(){
        Arbitrary<String> names = Arbitraries.strings()
                .withCharRange('a','z')
                .ofMinLength(3)
                .ofMaxLength(20);

        Arbitrary<String> emails = Arbitraries.strings()
                .withCharRange('a','z')
                .ofMinLength(3)
                .map(s -> s + "@test.com");

        return Combinators.combine(names,emails).as(Client::new);
    }

    @Provide
    Arbitrary<Client> invalidClients() {
        Arbitrary<String> names = Arbitraries.of("", null);
        Arbitrary<String> emails = Arbitraries.of("", null);
        return Combinators.combine(names, emails).as(Client::new);
    }

    @Provide
    Arbitrary<String> validNames() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(3)
                .ofMaxLength(20);
    };

    @Provide
    Arbitrary<String> validEmails() {
        return Arbitraries.strings()
                .withCharRange('a','z')
                .ofMinLength(3)
                .map(s -> s + "@test.com");
    };

    @Provide
    Arbitrary<String> invalidData() {
        return Arbitraries.of("", null);
    };

    @Provide
    Arbitrary<String> invalidEmails() {
        return Arbitraries.strings()
                .withCharRange('a','z')
                .ofMinLength(3)
                .map(s -> s + "@test.com");
    };
    @Provide Arbitrary<Integer> mockedClientIds() { return Arbitraries.of(1,2,3,4,5,6,7,8,9,10); }

    @Example
    void getNotExistingClientById(){
        assertThrows(ClientNotFoundException.class,() -> {
            controller.getClientById(-10);
        });
    }

    @Property
    boolean createClientAndFindByIdWorks(@ForAll("validClients") Client client){
        controller.createClient(client);

        return controller.getClientById(client.getId()) != null;
    }

    @Property
    void createClientFailed(@ForAll("invalidClients") Client client){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createClient(client);
        });
    }

    @Example
    void updateClientWithValidData(@ForAll("validNames") String name,@ForAll("validEmails") String email,@ForAll("mockedClientIds") int id){
        assertDoesNotThrow(() -> {
            controller.updateClient(new Client(id,name,email));
        });
    }


    @Example
    void updateClientWithInvalidData(@ForAll("invalidData") String data,@ForAll("mockedClientIds") int id){
        assertThrows(IllegalArgumentException.class,() -> {
            controller.updateClient(new Client(id,data,data));
        });

    }



    @Example
    void deleteExistingClient() {
        ClientService service = new ClientService();
        Client client = controller.getClients().get(0);
        int id = client.getId();
        assertDoesNotThrow(() -> controller.deleteClientById(id));
        assertThrows(ClientNotFoundException.class,() -> {
            service.isClientFound(id);
        });
    }
    @Example
    void deleteNotExistingClient() {
        int id = 1024091240;
        assertThrows(ClientNotFoundException.class,() -> {
            controller.deleteClientById(id);
        });
    }




}
