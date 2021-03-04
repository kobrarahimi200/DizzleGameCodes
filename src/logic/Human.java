/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

/**
 * This class is a child of the player class. it used for building the human
 * player. Each player has one board, an id and type.
 *
 * @author Reyhan
 */
public class Human extends Player {

    /**
     * default constructor
     *
     * @param id
     * @param type
     * @param board
     */
    public Human(int id, playerType type, Field board) {
        super(id, playerType.HUMAN, board);
    }

}
