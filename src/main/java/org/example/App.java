package org.example;

import io.javalin.Javalin;
import org.example.controller.ClientController;

import java.io.IOException;

public class App {
    public static void main( String[] args ) {

        Javalin app = Javalin.create(config -> {

        }).start(7000);

        new ClientController(app);
    }
}
