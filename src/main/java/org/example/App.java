package org.example;

import org.example.controller.ClientController;
import org.example.exception.ClientNotFoundException;
import org.example.util.ControlPanel;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        while(true){
            try{
                ControlPanel.showMenu();
                int choice = ControlPanel.captureChoice();
                ControlPanel.handleChoice(choice);
            }catch (ClientNotFoundException e){
                System.out.println(e.getMessage());
                ControlPanel.returnToMenu();
            }
        }






    }
}
