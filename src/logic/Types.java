/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * This class contains the types of the all objects of the playing board with
 * their priorities
 *
 * @author Reyhan
 */
public enum Types {
    PuzzleBlue(0), PuzzleGreen(0), FLAG(1), JewelYellow(2), JewelBlue(2), JewelRed(2), ROCKET(3), BOMBS(4),
    KeyBlue(5), KeyYellow(5), KeyHoleBlue(6), KeyHoleYellow(6),
    HORIZONTALLINES(7), VERTICALLINES(7), noType(8), PLANET(9);

    private int priority; // is the prority of each type (used mostly for AI)

    Types(int priority) {
        this.priority = priority;
    }

    /**
     * gets the priority of the type
     *
     * @return
     */
    protected int getPriority() {
        return this.priority;
    }

}
