package org.example.controller;

import org.example.model.Client;
import org.example.service.ClientService;

import java.util.List;

public class ClientController {

    private final ClientService service;

    public ClientController() {
        this.service = new ClientService();
    }

    public List<Client> getClients(){
        return service.getClients();
    }

    public Client getClientById(int id){
        return service.getClientById(id);
    }

    public void createClient(Client client){
        service.createClient(client);
    }

    public void updateClient(Client client){
        service.updateClient(client);
    }


    public void deleteClientById(int id) {
        service.deleteClientById(id);
    }
}
