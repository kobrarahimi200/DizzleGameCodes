package logic;

import com.google.gson.annotations.SerializedName;

/**
 * passing the read json data to an instance of this class. This class sets the
 * data to a structured format to make the progress of the game easier.
 *
 * @author Reyhan
 */
public class LevelMaker {

    private Integer[][] field;//array of the main field
    private PointObjects[] jewels;//array of  the jewel
    private PointObjects bombs;//used for bomb symbol
    private PointObjects[] puzzles;//array of puzzles

    @SerializedName("horizontal-lines")
    private PointObjects[] horizontalLines;//array of horizontal lines

    @SerializedName("vertical-lines")
    private PointObjects[] verticalLines;//array of vertical lines

    private Key[] keys;//array of key object
    private Flag flag;//an object of flag
    private Position rocket;//the position of rocket
    private Position planet;//the position of planet

    /**
     * gets the field
     *
     * @return an array of field
     */
    public Integer[][] getField() {
        return field;
    }

    /**
     * sets the field array
     *
     * @param field
     */
    public void setField(Integer[][] field) {
        this.field = field;
    }

    /**
     * gets the jewel array data
     *
     * @return an array of pointObject
     */
    public PointObjects[] getJewels() {
        return jewels;
    }

    /**
     * sets the jewel array data
     *
     * @param jewels
     */
    public void setJewels(PointObjects[] jewels) {
        this.jewels = jewels;
    }

    /**
     * gets the bomb information
     *
     * @return an object of pointObject
     */
    public PointObjects getBombs() {
        return bombs;
    }

    /**
     * sets the bomb
     *
     * @param bombs
     */
    public void setBombs(PointObjects bombs) {
        this.bombs = bombs;
    }

    /**
     * gets the puzzles array
     *
     * @return an array of puzzles
     */
    public PointObjects[] getPuzzles() {
        return puzzles;
    }

    /**
     * sets the puzzle array
     *
     * @param puzzles
     */
    public void setPuzzles(PointObjects[] puzzles) {
        this.puzzles = puzzles;
    }

    /**
     * gets the horizontal line array
     *
     * @return an array of horizontal lines
     */
    public PointObjects[] getHorizontalLines() {
        return horizontalLines;
    }

    /**
     * sets the horizontal line array
     *
     * @param horizontalLines
     */
    public void setHorizontalLines(PointObjects[] horizontalLines) {
        this.horizontalLines = horizontalLines;
    }

    /**
     * gets the vertical line array
     *
     * @return
     */
    public PointObjects[] getVerticalLines() {
        return verticalLines;
    }

    /**
     * sets the vertical line array
     *
     * @param verticalLines
     */
    public void setVerticalLines(PointObjects[] verticalLines) {
        this.verticalLines = verticalLines;
    }

    /**
     * gets the array key
     *
     * @return an array of keys
     */
    public Key[] getKeys() {
        return keys;
    }

    /**
     * sets the array key
     *
     * @param keys
     */
    public void setKeys(Key[] keys) {
        this.keys = keys;
    }

    /**
     * gets rocket position
     *
     * @return a position
     */
    public Position getRocket() {
        return rocket;
    }

    /**
     * sets the rocket position
     *
     * @param rocket
     */
    public void setRocket(Position rocket) {
        this.rocket = rocket;
    }

    /**
     * gets the planet position
     *
     * @return a position
     */
    public Position getPlanet() {
        return planet;
    }

    /**
     * sets the planet position
     *
     * @param planet
     */
    public void setPlanet(Position planet) {
        this.planet = planet;
    }

    /**
     * sets the flag
     *
     * @param flag
     */
    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    /**
     * gets the flag object
     *
     * @return
     */
    public Flag getFlag() {
        return flag;
    }

}
