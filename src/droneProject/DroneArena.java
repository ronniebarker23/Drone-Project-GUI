package droneProject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author ronniebarker
 * where drones are
 */
public class DroneArena {

    private int xSize;
    private int ySize;
    private ArrayList<Pieces> drones = new ArrayList<Pieces>();
    Random randomGenerator;

    /**
     * Initialise arena with given size
     * @param X int width
     * @param Y int length
     */
    public DroneArena(int X, int Y) {
        //set size
        xSize = X;
        ySize = Y;
        randomGenerator = new Random();			//create random

    }

    /**
     * show all the drones in the interface
     * @param c	the canvas in which drones are shown
     */
    public void showDrones(MyCanvas c) {
        for (Pieces d : drones) {       //loop through drones and display
            d.displayDrone(c);
        }

    }


    /**
     * add drone to arena
     * @param type
     */
    public void addDrone(char type) {
        boolean clash = true;		//initialise clash to true
        double xVal = randomGenerator.nextInt(xSize);       //find random location
        double yVal = randomGenerator.nextInt(ySize);

        while(clash) {
            if(getDroneAt(xVal, yVal) == null) {        //keep trying till nothing in that space
                clash = false;
            }
            xVal = randomGenerator.nextInt(xSize);		//random x less than width
            yVal = randomGenerator.nextInt(ySize);		//random y less than height


        }

        if(type == 'd'){            //add piece of type drone to list
            Drone newPiece = new Drone(xVal, yVal);
            drones.add(newPiece);
        }
        else if(type == 'o'){       //add piece of type obstacle to list
            Obstacle newPiece = new Obstacle(xVal, yVal);
            drones.add(newPiece);
        }
        else if(type == 'b'){       //add piece of type bird to list
            Bird newPiece = new Bird(xVal, yVal);
            drones.add(newPiece);
        }
    }

    /**
     * Add piece from loaded arena
     * @param col colour
     * @param x x co ord
     * @param y y co ord
     * @param spe speed
     * @param ang angle
     */
    public void addPiece(char col, double x,double y, int ID, double spe, double ang){
        if (col == 'w'){        //if drone recreate that drone
            Drone newPiece = new Drone(x,y);
            newPiece.setDirection(spe,ang);
            newPiece.setID(ID);
            drones.add(newPiece);
        }
        else if(col == 'g'){        //if obstacle recreate that obstacle
            Obstacle newPiece = new Obstacle(x, y);
            newPiece.setID(ID);
            drones.add(newPiece);
        }
        else if (col == 'o'){     //if bird recreate that bird
            Bird newPiece = new Bird(x,y);
            newPiece.setDirection(spe,ang);
            newPiece.setID(ID);
            drones.add(newPiece);
        }
    }


    /**
     * return size of arena and location of drones
     */
    public String toString() {
        //arena info
        String locations = "The arena is " + xSize + " x " + ySize;
        //pieces info
        for (Pieces d : drones) {
            locations += "\n";
            locations += d.toString();
        }

        return locations;
    }

    /**
     * search arraylist of drones to see if there is a drone at x,y
     * @param x
     * @param y
     * @return null if no Drone there, otherwise return drone
     */
    public Pieces getDroneAt(double x, double y) {
        //first look for obstacle
        for (Pieces d : drones) {
            if(d instanceof Obstacle) {
                if (d.isHere(x, y)) {
                    return d;        //if drone there, return that drone

                }
            }
        }
        //then drones
        for (Pieces d : drones) {
            if(d instanceof Bird) {
                if (d.isHere(x, y)) {
                    return d;        //if drone there, return that drone

                }
            }
        }
        //then birds
        for (Pieces d : drones) {
            if(d instanceof Drone && !(d instanceof Bird)) {
                if (d.isHere(x, y)) {
                    return d;        //if drone there, return that drone

                }
            }
        }

        return null;		//if not found, return null
    }


    /**
     * Checks if space valid/ free
     * @param xCo location x
     * @param yCo location y
     * @return true if can move, false if can't
     */
    public boolean canMoveHere(double xCo, double yCo, Pieces current) {
        //if in arena
        if(xCo < xSize && xCo > 0 && yCo < ySize && yCo > 0) {
            //if space free
            if(getDroneAt(xCo, yCo) == null || getDroneAt(xCo, yCo) == current) {
                return true;
            }
        }

        return false;
    }

    /**
     * try to move all drones
     */
    public void moveAllDrones() {
        for (Pieces d : drones) {
            d.tryToMove(this);
        }
    }

    /**
     * Provide data to save arena
     * @return string with arena info on each line
     */
    public String saveArenaData() {
        String save = "";
        save += xSize + "\n";
        save += ySize;
        for (Pieces d : drones) {
            save += "\n";
            save += d.saveDrone();
        }
        return save;

    }

    /**
     * save arena to file
     */
    public void saveArena() {
        //pick name and location
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                JFileChooser chooser = new JFileChooser();
                int approve = chooser.showSaveDialog(null);
                if (approve == JFileChooser.APPROVE_OPTION) {			//if possible
                    File selFile = chooser.getSelectedFile();			//get output file
                    File currDir = chooser.getCurrentDirectory();		//get directory to save to

                    //save
                    try {
                        FileWriter outFileWriter = new FileWriter(selFile);		//create filewriter for selected file
                        PrintWriter writer = new PrintWriter(outFileWriter);
                        writer.println(saveArenaData());						//save arena info to file
                        writer.close();
                    }
                    catch (FileNotFoundException e) {								//if can't find file
                        e.printStackTrace();
                    }
                    catch (IOException e) {										//if input/output error
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();


                }







    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        DroneArena a = new DroneArena(20, 10);	// create drone arena
        //a.addDrone();
        //a.addDrone();
        //System.out.println(a.toString());	// print where is


    }


}
