/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * this class is responsible for storing the symbols with their types and
 * points. This class is an object that can be used as units of the main board.
 *
 * @author Reyhan
 */
public class Cell {

    private Integer value;//the value of the cell
    private Types type;//the type of the cell
    private boolean crossed;//is true when cell is crossed
    private boolean filled;//is true when cell is filled
    private boolean exploded = false;//is true when cell is exploded
    private int point;//point of the cell

    /**
     * default constructor
     *
     * @param val
     * @param type
     * @param crossed
     * @param filled
     * @param point
     */
    Cell(Integer val, Types type, boolean crossed, boolean filled, int point) {
        this.value = val;
        this.crossed = crossed;
        this.filled = filled;
        this.type = type;
        this.point = point;
    }

    /**
     * gets the value of the cell
     *
     * @return integer value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * gets the point of the cell
     *
     * @return an integer value
     */
    public int getPoint() {
        return point;
    }

    /**
     * sets the value of the cell
     *
     * @param value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * gets the type of the cell
     *
     * @return type
     */
    public Types getType() {
        return type;
    }

    /**
     * sets the type of the cell
     *
     * @param type
     */
    public void setType(Types type) {
        this.type = type;
    }

    /**
     * sets the crossed
     *
     * @return
     */
    public boolean isCrossed() {
        return crossed;
    }

    /**
     * gets the exploded boolean value
     *
     * @return a boolean value
     */
    public boolean getExploded() {
        return exploded;
    }

    /**
     * sets the exploded value
     *
     * @param exploded
     */
    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    /**
     * sets the crossed value
     *
     * @param crossed
     */
    public void setCrossed(boolean crossed) {
        this.crossed = crossed;
    }

    /**
     * gets the filled boolean value
     *
     * @return boolean value
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * sets the filled boolean value
     *
     * @param filled
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

}
