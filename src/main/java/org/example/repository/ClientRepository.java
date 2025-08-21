package org.example.repository;

import org.example.exception.ClientNotFoundException;
import org.example.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {

    private List<Client> repository = new ArrayList<>();

    public ClientRepository() {
        Client c1 = new Client("Alice", "alice@example.com");
        Client c2 = new Client( "Bob", "bob@example.com");
        Client c3 = new Client( "Carol", "carol@example.com");
        Client c4 = new Client( "David", "david@example.com");
        Client c5 = new Client("Eve", "eve@example.com");
        Client c6 = new Client( "Frank", "frank@example.com");
        Client c7 = new Client( "Grace", "grace@example.com");
        Client c8 = new Client( "Heidi", "heidi@example.com");
        Client c9 = new Client( "Ivan", "ivan@example.com");
        Client c10 = new Client( "Judy", "judy@example.com");
        repository.addAll(List.of(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10));
    }

    public List<Client> getRepository() {
        return repository;
    }

    public Client findClientById(int id) {
        for(Client client : repository){
            if(id == client.getId()){
                return client;
            }
        }
       return null;
    }

    public void addClient(Client client) {
        repository.add(client);
    }
}
