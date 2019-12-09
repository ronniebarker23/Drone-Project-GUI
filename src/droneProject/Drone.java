package droneProject;
import java.util.Random;

/**
 * @author ronniebarker
 * Drones in the arena
 */
public class Drone extends Pieces{

    protected double speed = 2;           //speed of all drones is 2
    protected double angle;
    private static int begin = 0;

    /**
     * instantiate drone
     * @param X int
     * @param Y int
     */
    public Drone(double X, double Y) {
        super(X, Y);
        size = 3;
        colour = 'w';
        //unique ID
        if (this.getClass() != Bird.class){     //don't increase if new bird
            ID = begin++;
        }
        //add a random direction
        Random rand = new Random(System.currentTimeMillis());
        angle = rand.nextFloat()*2*Math.PI;

    }


    /**
     * print location of the instantiated drone
     */
    public String toString() {
        return "Drone " + ID + " is at " + String.format("%.2f",xPosition) + ", " + String.format("%.2f",yPosition) + " moving at angle " + String.format("%.2f",angle);
    }

    /**
     * move to new location
     * @param a arena
     */
    public void tryToMove(DroneArena a) {
        //calculate new co-ord
        //calculate new co-ord
        double newX = xPosition + speed*Math.cos(angle);
        double newY = yPosition + speed*Math.sin(angle);


        //if can move there
        if(a.canMoveHere(newX, newY, this)) {
            xPosition = newX;
            yPosition = newY;
        }
        else {
            //bounce back opposite
            angle += Math.toRadians(180);
            //move in that direction
            newX = xPosition + speed*Math.cos(angle);
            newY = yPosition + speed*Math.sin(angle);

            //update positions
            xPosition = newX;
            yPosition = newY;
        }
    }

    @Override
    /**
     * save info of drone on each line of string
     */
    public String saveDrone() {
        String save = "";
        save += colour + "\n";
        save += ID + "\n";
        save += xPosition + "\n";
        save += yPosition + "\n";
        //extra lines where speed and angle added for drone
        save += speed + "\n";
        save+= angle;

        return save;

    }

    /**
     * when loading be able to control direction
     * @param spe speed
     * @param ang angle
     */
    public void setDirection(double spe, double ang){
        speed = spe;
        angle = ang;
    }



}
