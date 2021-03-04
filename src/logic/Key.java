/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * This class is used for the key symbol. It has two parameters positions and
 * array of holes. we have two different kind of keys and holes. Every key has a
 * hole
 *
 * @author Reyhan
 */
public class Key {

    private Position position;//the position of the key
    private Position[] holes;//the positions of the holes

    /**
     * gets the position of the key
     *
     * @return
     */
    public Position getPosition() {
        return position;
    }

    /**
     * set the position of the key
     *
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * gets the hole array positions.
     *
     * @return the array of hole positions
     */
    public Position[] getHoles() {
        return holes;
    }

    /**
     * sets the array positions of holes
     *
     * @param holes
     */
    public void setHoles(Position[] holes) {
        this.holes = holes;
    }

}
