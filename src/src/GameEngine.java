/*
Student Name: Chris Koenig, Carter Hinkle, Alexander Rodriguez, Yasmine
Program Name: Escape Room
Creation Date: Fall 2020 (date unknown)
Last Modified Date: Fall 2020 (date unknown)
CSCI Course: CSCI 325
Grade Received: (unknown)
Design Comments: (n/a)
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import java.util.Scanner;

/**
 *
 * @author Minty
 */
public class GameEngine 
{
    Scanner scnr = new Scanner(System.in);
    MapHandler map;
    
    public GameEngine(MapHandler map)
    {
        this.map = map;
    }
    
    public boolean play()
    {
        
        GenericTile player = map.getPlayerTile(); // create the reference to player tile
        
        String input = "";
        int turns = map.getMaxTurns();    
        
        
        while (!player.getHasWon())
        {
            player = map.getPlayerTile(); // updates player tile
            
            System.out.println("Turns: " + turns);
            System.out.println(map.getWholeRoom()); // builds map

            input = scnr.next();
            
            switch(input)
            {
                case "w":
                    map.swapTiles(player.getPosX(), player.getPosY(), player.getPosX() - 1, player.getPosY());
                    break;
                case "a":
                    map.swapTiles(player.getPosX(), player.getPosY(), player.getPosX(), player.getPosY() - 1);
                    break;
                case "s": 
                    map.swapTiles(player.getPosX(), player.getPosY(), player.getPosX() + 1, player.getPosY());
                    break;            
                case "d": 
                    map.swapTiles(player.getPosX(), player.getPosY(), player.getPosX(), player.getPosY() + 1);
                    break;
            }
            
            --turns;
            
            // if you run out of turns or you have lost, break
            if (turns == 0 || player.getHasLost())
            {
                player.setHasLost(true);
                break;
            }
        }
        
        if(player.getHasWon() == true)
        {
            StateDisplay.printWin();
        }
        else
        {
            StateDisplay.printLose();
        }
        StateDisplay.printReset();
        
        
        boolean valid = false;
        while (!valid)
        {
            input = scnr.next();
            switch(input)
            {
                case "Y":
                case "y":
                    map.replaceRoom("default.txt"); // reset variables and room
                    map.parseRoom();
                    return false;
                case "N":
                case "n":
                    System.out.println("Thanks for playing!"); // quit
                    return true;
                default :
                    valid = false;
                    System.out.println("Invalid option.");
                    break;
            }            
        }
        return false;
    }
}
