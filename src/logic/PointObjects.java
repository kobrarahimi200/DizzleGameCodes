package logic;

/**
 * This class is used by LEvelMaker class for building the symbols of the main
 * board like puzzles, jewels and so on. Every object of this class has point
 * and array of positions
 *
 * @author Reyhan
 */
public class PointObjects {

    int points;//the point of the symbol
    Position[] positions;//the array of positions of the symbol

    /**
     * gets the point
     *
     * @return a number
     */
    public int getPoints() {
        return points;
    }

    /**
     * sets the point of the symbol
     *
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * gets the array of positions of symbol
     *
     * @return an array of symbol positions
     */
    public Position[] getPositions() {
        return positions;
    }

    /**
     * sets the array of positions
     *
     * @param positions
     */
    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

}
