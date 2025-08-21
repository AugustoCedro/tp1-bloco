package org.example.service;

import org.example.exception.ClientNotFoundException;
import org.example.model.Client;
import org.example.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {

    private final ClientRepository repository;

    public ClientService() {
        this.repository = new ClientRepository();
    }

    public List<Client> getClients(){
        return repository.getRepository();
    }

    public Client getClientById(int id) {
        Client client = repository.findClientById(id);
        if (client == null) {
            throw new ClientNotFoundException("Cliente com ID: " + id + " não encontrado");
        }
        return client;
    }

    public void createClient(Client client) {
        repository.addClient(client);

    }
}
