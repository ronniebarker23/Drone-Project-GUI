package droneProject;

/**
 * @author ronniebarker
 *  obstacles in arena
 */
public class Obstacle extends Pieces {

    /**
     * instantiate piece
     *
     * @param X   int
     * @param Y   int
     */
    public Obstacle(double X, double Y) {
        super(X, Y);
        //all obstacles have set size and colour
        size = 10;
        colour = 'g'; //green for tree
    }

    @Override
    public String toString() {
        return "Obstacle " + ID + " is at " + xPosition + ", " + yPosition;
    }


    @Override
    public void tryToMove(DroneArena a) {

    }
}
