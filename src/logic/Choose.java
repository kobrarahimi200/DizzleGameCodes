package logic;

/**
 * This class used by AI, when he chooses the best position for move then create
 * an object of this class and store the position,type and the point .
 *
 * @author Reyhan
 */
public class Choose {

    private Position pos;// chosen position
    private int point; //chosen point
    private Types type;//chosen type

    /**
     * default constructor
     *
     * @param pos
     * @param point
     * @param types
     */
    public Choose(Position pos, int point, Types types) {
        this.pos = pos;
        this.point = point;
        this.type = types;
    }

    /**
     * gets the chosen position
     *
     * @return
     */
    public Position getPos() {
        return pos;
    }

    /**
     * sets the chosen position
     *
     * @param pos
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * gets the chosen point
     *
     * @return
     */
    public int getPoint() {
        return point;
    }

    /**
     * sets the given point
     *
     * @param point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * gets the type
     *
     * @return
     */
    public Types getType() {
        return type;
    }

    /**
     * sets the given type
     *
     * @param type
     */
    public void setType(Types type) {
        this.type = type;
    }

    public String toString() {
        return this.pos + " type : " + type + " ,point : " + point;
    }
}
