/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author Minty
 */
public class GenericTile 
{
    private char displayChar; // character associated with object
    
    // bools for deciding objects
    private boolean isSolid = false;
    private boolean isDamaging = false;
    private boolean isGoal = false;
    
    //bools for player object
    private boolean hasWon = false;
    private boolean hasLost = false;
    
    //x and y positions
    private int posX;
    private int posY;

    
    public GenericTile(char display, int posX, int posY) // constructor
    {
        this.posX = posX;
        this.posY = posY;
        
	displayChar = display;
	
	switch(displayChar)
        {
	    case 'I':           // wall
                isSolid = true;
		break;
	    case 'a':           // player
		break;
            case 'X':           // enemy
                isDamaging = true;
                break;
            case '&':           // goal
                isGoal = true;
                break;
	}
        
        
    }
    
    
    // deciding object bools
    public char getDisplayChar() // gets the character assigned to the object
    {
	return displayChar;
    }
    
    public boolean getIsSolid() // checks if object is solid
    {
        return isSolid;
    }
    
    public boolean getIsDamaging() // checks if object is solid
    {
        return isDamaging;
    }
    
    public boolean getIsGoal() // checks if object is the goal
    {
        return isGoal;
    }
    
    
    //has won has lost
    public boolean getHasWon() // checks if player object has won. 
    {
        if(displayChar == 'a')
        {
            return hasWon;
        }
        else                   // if not player, return false
        {
            return false;
        }
    }
    
    public boolean getHasLost() // checks if player object has lost. 
    {
        if(displayChar == 'a')
        {
            return hasLost;
        }
        else                   // if not player, return false
        {
            return false;
        }
    }
    
    
    // Positions
    public int getPosX() // gets x position of object
    {
        return posX;
    }
    
    public int getPosY() // gets y position of the object
    {
        return posY;
    }
    
    
    // set functions
    public void setHasWon(boolean input) // sets hasWon for player object
    {
        
        if(displayChar == 'a')
        {
            hasWon = input;
        }
        else                             // if not player, set to false
        {
            hasWon = false;
        }
    }
    
    public void setHasLost(boolean input) // sets hasLost for player object
    {
        if(displayChar == 'a')
        {
            hasLost = input;
        }
        else                             // if not player, set to false
        {
            hasLost = false;
        }
    }    
    
    public void setPosX(int posX) // sets the x position of the object
    {
        this.posX = posX;
    }
    
    public void setPosY(int posY) // sets the y position of the object
    {
        this.posY = posY;
    }
}
