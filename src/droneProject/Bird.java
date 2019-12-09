package droneProject;

/**
 * @author ronniebarker
 * Birds in the arena (like drone but can't get as close to things)
 */
public class Bird extends Drone {

    /**
     * instantiate bird
     * @param X int
     * @param Y int
     */
    public Bird(double X, double Y) {
        super(X, Y);
        speed = 4;
        size = 6;
        colour = 'o';

    }

    @Override
    /**
     * print location of the instantiated bird
     */
    public String toString() {
        return "Bird " + ID + " is at " + String.format("%.2f",xPosition) + ", " + String.format("%.2f",yPosition) + " moving at angle " + String.format("%.2f",angle);
    }

    @Override
    /**
     * confirm/ deny bird is at that location (extra space to account for drones can't go near bird
     */
    public boolean isHere(double x, double y) {
        //include size of piece + extra radius
        double top = y+size+size*2;
        double bottom = y-size-size*2;
        double left = x-size-size*2;
        double right = x+size+size*2;
        //check if there
        if((xPosition <= right && xPosition >= left) && (yPosition <= top && yPosition >= bottom)) {
            return true;
        }

        return false;
    }
}
