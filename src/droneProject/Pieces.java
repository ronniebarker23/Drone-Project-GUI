package droneProject;

/**
 * @author ronniebarker
 * Pieces in the arena
 */
public abstract class Pieces {
    protected double xPosition;
    protected double yPosition;
    protected int ID;
    protected int size;
    protected char colour;

    /**
     * instantiate piece
     * @param X int
     * @param Y int
     */
    public Pieces(double X, double Y) {
        // x and y location
        xPosition = X;
        yPosition = Y;

    }


    /**
     * print location of the instantiated drone
     */
    public abstract String toString();

    /**
     * confirm/ deny is drone is at that location
     */
    public boolean isHere(double x, double y) {
        //include size of piece
        double top = y+size;
        double bottom = y-size;
        double left = x-size;
        double right = x+size;
        //check if there
        if((xPosition < right && xPosition > left) && (yPosition < top && yPosition > bottom)) {
            return true;
        }

        return false;
    }

    /**
     * display the piece in the canvas
     * @param c	the canvas used
     */
    public void displayDrone(MyCanvas c){
        c.showCircle(xPosition, yPosition, size, colour);
    }

    /**
     * get size of piece
     * @return int piece size
     */
    public int getSize(){return size;}

    /**
     * get colour of piece
     * @return char colour of piece
     */
    public char getColour(){return colour;}

    /**
     * check if can move
     * @param a arena
     */
    public abstract void tryToMove(DroneArena a);

    /**
     * save piece info
     * @return piece info on different lines
     */
    public String saveDrone() {
        String save = "";
        save += colour + "\n";
        save += ID + "\n";
        save += xPosition + "\n";
        save += yPosition + "\n";
        //add extra lines where speed and angle will be added for drone
        save+= "\n";

        return save;

    }

    /**
     * alter ID
     * @param givenID
     */
    public void setID(int givenID){
        ID = givenID;
    }




}
