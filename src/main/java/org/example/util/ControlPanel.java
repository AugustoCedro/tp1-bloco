package org.example.util;

import org.example.controller.ClientController;
import org.example.model.Client;

import java.util.List;
import java.util.Scanner;

public class ControlPanel {

    private static Scanner scanner = new Scanner(System.in);
    private static ClientController controller = new ClientController();

    public static void showMenu(){
        System.out.println("=============================");
        System.out.println("PAINEL DE CONTROLE DO CLIENTE");
        System.out.println("=============================");
        System.out.println("Qual operação você deseja realizar?");
        System.out.println("1.Buscar Cliente(s).");
        System.out.println("2.Adicionar um Cliente Novo.");
        System.out.println("3.Alterar Dados de um Cliente.");
        System.out.println("4.Apagar Dados de um Cliente.");
        System.out.println("=============================");
    }

    public static int captureChoice(){
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void returnToMenu(){
        System.out.println("Aperte Enter para voltar ao menu");
        scanner.nextLine();
    }


    public static void handleChoice(int choice){
        switch (choice){
            case 1:
                showListMenu();
            case 2:

        }
    }

    public static void showListMenu(){
        System.out.println("1.Listar Todos os Clientes");
        System.out.println("2.Buscar Cliente pelo ID");
        int choice = captureChoice();
        handleListChoice(choice);
    }

    public static void handleListChoice(int choice){
        switch (choice){
            case 1:
                System.out.println("=============================");
                System.out.println("Todos os Clientes:");
                List<Client> clientList = controller.getClients();
                for(Client client : clientList){
                    System.out.printf("ID - %s, Nome - %s, Email - %s%n",client.getId(),client.getName(),client.getEmail());
                }
                System.out.println("=============================");
                returnToMenu();
                break;

            case 2:
                System.out.println("=============================");
                System.out.println("Digite o ID do Cliente:");
                int id = scanner.nextInt();
                scanner.nextLine();
                Client client = controller.getClientById(id);
                System.out.printf("ID - %s, Nome - %s, Email - %s%n",client.getId(),client.getName(),client.getEmail());
                System.out.println("=============================");
                returnToMenu();
                break;
        }
    }








}
