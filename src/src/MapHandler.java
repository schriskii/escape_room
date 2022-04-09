/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.*;
import java.io.*;

/*

Important Methods

- parseRoom()
    update room data from room.txt
- writeRoom()
    write room data back to room.txt

- mapMenu(Scanner scnr)
    access the menu interface (subset of the main menu)

- boolean swapTiles(int x1, int y1, int x2, int y2)
    swap the locations of 2 tiles in memory; returns false if failed
- GenericTile getTile(int xPos, int yPos)
    returns a GenericTile object at the specified location
- boolean setTile(int xPos, int yPos, GenericTile newTile)
    replaces the GenericTile object at the specified location; returns false if
    failed
- boolean isTileValid(int xPos, int yPos)
    returns whether or not the xpos and ypos are in-bounds of the array

- int getMaxHealth(), int getMaxTurns()
    self-explanatory
- String getWholeRoom()
    returns the contents of the room layout as a string
- int getWidth(), getHeight()
    return the size of the room

*/

/**
 *
 * @author munom
 */
public class MapHandler {
    private GenericTile roomData[][];
    private FileInputStream fileByteStream = null;
    private Scanner inFS = null;
    private FileOutputStream fileStream = null;
    private PrintWriter outFS = null;
    private String roomFilename;

    private int maxHealth, maxTurns, rows = 0, cols = 0;

    public MapHandler() {
        roomFilename = "room.txt";
        maxHealth = 0;
        maxTurns = 0;
    }

    // parse from a file
    public boolean parseFile(String fileName) {
        try {
            // init
            fileByteStream = new FileInputStream(fileName);
            inFS = new Scanner(fileByteStream);
            rows = 0;
            cols = 0;

            // hp
            inFS.next();
            maxHealth = inFS.nextInt();
            inFS.nextLine();

            // turns
            inFS.next();
            maxTurns = inFS.nextInt();
            inFS.nextLine();
            inFS.nextLine();

            // get width and height
            while (inFS.hasNextLine()) {
                cols = inFS.nextLine().length();
                rows++;
            }

            // reset
            fileByteStream = new FileInputStream(fileName);
            inFS = new Scanner(fileByteStream);
            inFS.nextLine();
            inFS.nextLine();
            inFS.nextLine();

            // tiles
            roomData = new GenericTile[rows][cols];
            for (int i = 0; inFS.hasNextLine(); i++) {
                String line = inFS.nextLine();
                for (int j = 0; j < line.length() && j < roomData[0].length; j++) {
                    roomData[i][j] = new GenericTile(line.charAt(j), i, j);
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        }

        return true;
    }

    // write to a file
    public boolean writeFile(String fileName) {
        try {
            fileStream = new FileOutputStream(fileName);
            PrintWriter outFS = new PrintWriter(fileStream);
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    // parse from room.txt specifically
    public void parseRoom() {
        parseFile(roomFilename);
    }

    // write to room.txt specifically
    public void writeRoom() {
        writeFile(roomFilename);
    }

    // run the menu to manage maps
    public void mapMenu(Scanner scnr) {
        boolean finished = false;
        String selection;

        while (!finished) {
            System.out.println("----------------");
            System.out.println(" Map Management");
            System.out.println("----------------");
            System.out.println("R - Reload " + roomFilename);
            System.out.println("P - Replace " + roomFilename + "'s contents " +
                "with those of another file");
            System.out.println("S - Save " + roomFilename + "'s contents to " +
                "another file");
            System.out.println("L - List files in directory");
            System.out.println("Q - Quit");
            System.out.println();
            System.out.println("Enter an option: ");

            selection = scnr.nextLine();
            System.out.println();

            switch (selection) {
                case "R":
                case "r":
                    parseRoom();
                    System.out.println("Room reloaded.");
                    break;
                case "P":
                case "p":
                    System.out.println("Please enter a filename: ");
                    selection = scnr.nextLine();
                    if (replaceRoom(selection)) {
                        System.out.println("Contents replaced.");
                    } else {
                        System.out.println("One or more of the files could not " +
                            "be found.");
                    }
                    break;
                case "S":
                case "s":
                    System.out.println("Please enter a filename: ");
                    selection = scnr.nextLine();
                    if (saveRoom(selection)) {
                        System.out.println("Contents replaced.");
                    } else {
                        System.out.println("One or more of the files could not " +
                            "be found.");
                    }
                    break;
                case "L":
                case "l":
                    String names[];
                    File f = new File(".");
                    names = f.list();

                    System.out.println("Files in directory:");

                    for (String name: names) {
                        if (name.endsWith(".txt")) {
                            System.out.println(name);
                        }
                    }
                    break;
                case "Q":
                case "q":
                    finished = true;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
            System.out.println();
        }
    }

    // swap 2 tiles' positions    
    public boolean swapTiles(int x1, int y1, int x2, int y2) {
        if (roomData[x2][y2].getIsSolid()) {
            return false;
        } else if (roomData[x2][y2].getIsGoal()) {
            roomData[x1][y1].setHasWon(true);
            roomData[x1][y1].setHasLost(false);
            return false;
        } else if (roomData[x2][y2].getIsDamaging()) {
            roomData[x1][y1].setHasWon(false);
            roomData[x1][y1].setHasLost(true);
            return false;
        }

        GenericTile tempStore = getTile(x1, y1);

        try {
            roomData[x1][y1] = roomData[x2][y2];

            roomData[x1][y1].setPosX(x1); //updating member variable of GenericTile
            roomData[x1][y1].setPosY(y1); //updating member variable of GenericTile  

            roomData[x2][y2] = tempStore;

            roomData[x2][y2].setPosX(x2); //updating member variable of GenericTile
            roomData[x2][y2].setPosY(y2); //updating member variable of GenericTile

            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // return the entire room contents as a string (to be printed)
    public String getWholeRoom() {
        String msg = "";
        for (int i = 0; i < roomData.length; i++) {
            for (int j = 0; j < roomData[i].length; j++) {
                msg += roomData[i][j].getDisplayChar();
            }
            msg += "\n";
        }
        return msg;
    }

    // return a specified tile from the room
    public GenericTile getTile(int xPos, int yPos) {
        return roomData[xPos][yPos];
    }

    // set a specified tile
    public boolean setTile(int xPos, int yPos, GenericTile newTile) {
        try {
            roomData[xPos][yPos] = newTile;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // set a specified tile
    public boolean isTileValid(int xPos, int yPos) {
        GenericTile test;

        try {
            test = roomData[xPos][yPos];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // replace room.txt's contents with those of a specified file
    public boolean replaceRoom(String fileName) {
        try {
            fileByteStream = new FileInputStream(fileName);
            inFS = new Scanner(fileByteStream);
            fileStream = new FileOutputStream(roomFilename);
            outFS = new PrintWriter(fileStream);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (inFS.hasNextLine()) {
            outFS.print(inFS.nextLine());
            if (inFS.hasNextLine()) {
                outFS.println();
            }
        }

        outFS.close();

        return true;
    }

    // get health
    public int getMaxHealth() {
        return maxHealth;
    }

    // get turns
    public int getMaxTurns() {
        return maxTurns;
    }

    // save current data to an arbitrary file
    public boolean saveRoom(String fileName) {
        File write = new File(fileName);
        //	write.createNewFile();

        try {
            fileStream = new FileOutputStream(fileName);
            outFS = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        outFS.println("Life: " + maxHealth);
        outFS.println("Time: " + maxTurns);

        outFS.print(getWholeRoom());

        return true;
    }

    // get width
    public int getWidth() {
        return rows;
    }

    // get height
    public int getHeight() {
        return cols;
    }


}