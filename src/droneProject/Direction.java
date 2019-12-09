package droneProject;

import java.util.Random;

/**
 * @author ronniebarker
 * Direction drones can move
 */

public enum Direction{
    NORTH,EAST,SOUTH,WEST;


    /**
     * returns a random direction
     */
    public static  Direction randomDirection(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    /**
     * give next direction
     * @param dir given direction
     * @return following direction
     */
    public static Direction next(Direction dir) {
        return values()[(dir.ordinal() + 1) % values().length];
    }
	/*
	public String toString() {
		return dir.toString();
	}*/

    /**
     * Test methods work
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Direction d = randomDirection();
        System.out.println(d);
        Direction n = next(d);
        System.out.println(n);
    }

}
