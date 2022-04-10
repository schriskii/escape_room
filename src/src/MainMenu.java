/*
Student Name: Chris Koenig, Carter Hinkle, Alexander Rodriguez, Yasmine
Program Name: Escape Room
Creation Date: Fall 2020 (date unknown)
Last Modified Date: Fall 2020 (date unknown)
CSCI Course: CSCI 325
Grade Received: (unknown)
Design Comments: (n/a)
*/

package src;

import java.util.Scanner;
import java.io.*;


/**
 *
 * @author Carter Hinkle
 */
public class MainMenu 
{
    public static void mainMenu() 
    {
        Scanner scnr = new Scanner(System.in);

        System.out.print("Welcome to the Escape Room\n\n");
        
        
	MapHandler test = new MapHandler();      
        test.parseRoom();
        
        //conditionals 
        String userInput = "";
        boolean finished = false;
        
        while(!finished)
        {
            // print main menu
	    System.out.println("----------------");
	    System.out.println("   Main Menu");
	    System.out.println("----------------");
            
            System.out.println("P - Play");
	    System.out.println("I - Instructions");
	    System.out.println("M - Change Map");
	    System.out.println("Q - Quit");
	    System.out.println();
	    System.out.println("Enter an option: ");
            
            // get user input and run methods based on input
            userInput = scnr.nextLine();
            switch(userInput)
            {
                case "P":
                case "p":
                    GameEngine game = new GameEngine(test);
                    finished = game.play();                    
                    break;
                case "I":
                case "i":
                    StateDisplay.printInstructions();   // view instructions
                    break;                         
                case "M":
                case "m":
                    test.parseRoom();
                    test.mapMenu(scnr);      // map manager
                    break;
                case "Q":
                case "q":
                    System.out.println("Thanks for playing!"); // quit
                    finished = true;
                    break;
            }
        }
    }
    
    public static void main(String[] args)
    {
        mainMenu();
    }
}
