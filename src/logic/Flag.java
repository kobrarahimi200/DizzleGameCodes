/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * This class is an instance for the flag symbols. it contains array of points
 * and the position
 *
 * @author Reyhan
 */
public class Flag {

    private int[] points; // flag array
    private Position position;//position of the falg

    /**
     * gets the point array
     *
     * @return
     */
    public int[] getPoints() {
        return points;
    }

    /**
     * sets the point array
     *
     * @param points
     */
    public void setPoints(int[] points) {
        this.points = points;
    }

    /**
     * gets the position of the flag
     *
     * @return
     */
    public Position getPosition() {
        return position;
    }

    /**
     * sets the position of the flag
     *
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

}
