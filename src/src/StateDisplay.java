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

/**
 *
 * @author Alex Rodriguez
 */
public class StateDisplay {
    public static void printWin()
    {
        System.out.println("CONGRATULATIONS!!! YOU WIN!\n\n");
    }
    
    public static void printLose() 
    {
        System.out.println("YOU DIED\n\n");
    }
    
    public static void printReset() 
    {
        System.out.println("Play Again?\n\ny/n:");
    }
    
    public static void printInstructions() 
    {
        
        System.out.println("Menu Instructions:");
        System.out.println("Change a map by hitting 'm' and then follow the on screen prompts.");
        System.out.println("Switching a map works by replacing room.txt with a different .txt file.");
        System.out.println("After replacing room.txt, please press 'r' to reload room.txt and then quit.\n");
        
        System.out.println("Gameplay Instructions:");
        System.out.println("Avoid the enemies and reach the goal!\n");
        
        System.out.println("Tile Types:");
        System.out.println("I = Wall\na = Player\nX = Enemy\n& = Goal\n");
        System.out.println("Controls:");
        System.out.println("w = move up\ns = move down\na = move left\nd = move right\n");
    }
}
