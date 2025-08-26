package org.example.app;

import org.example.exception.ClientNotFoundException;
import org.example.util.ControlPanel;

import java.util.InputMismatchException;

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
            }catch (ClientNotFoundException | IllegalArgumentException e){
                System.out.println(e.getMessage());
                ControlPanel.returnToMenu();
            }catch (InputMismatchException e){
                System.out.println("Valor escolhido inválido");
                ControlPanel.returnToMenu();
            }
        }
    }
}
