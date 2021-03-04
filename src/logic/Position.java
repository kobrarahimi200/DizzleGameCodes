/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * This class defines the position in the game field. A position is defined by a
 * row and a column integer number
 *
 * @author Reyhan
 */
public class Position {

    private int x;//x value of the position
    private int y;//y value of the position 

    /**
     * gets the x value
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * sets the x value
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * gets the y value
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * sets the y value
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * default constructor
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * gets the four neighboured positions even if they are not on board.
     *
     * @return the four neighboured positions
     */
    public Position[] getNeighbours() {
        Position[] neighbours = new Position[4];
        neighbours[0] = new Position(this.x - 1, this.y);
        neighbours[1] = new Position(this.x, this.y - 1);
        neighbours[2] = new Position(this.x + 1, this.y);
        neighbours[3] = new Position(this.x, this.y + 1);
        return neighbours;
    }

    /**
     * two positions are equal if the x-values and y-values are equal
     *
     * @param obj
     * @return true, if the x-values and y-values are equal
     */
    public boolean equals(Position obj) {
        return obj != null
                && obj.getX() == this.getX()
                && obj.getY() == this.getY();
    }

    /**
     * checks if this position is next to given position.
     *
     * @param p position to be near to
     * @return if this position is next to given position
     */
    public boolean isNextTo(Position p) {
        int xDiff = Math.abs(x - p.getX());
        int yDiff = Math.abs(y - p.getY());
        return (xDiff == 1 && yDiff == 0
                || xDiff == 0 && yDiff == 1);
    }

    /**
     * x/y
     *
     * @return x/y
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
