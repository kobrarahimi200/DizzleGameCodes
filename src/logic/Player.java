/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.util.List;

/**
 * This class defines the player. A player is characterized by a id, player type
 * and a board. The human player and bot are an instance of this class.
 *
 * @author Reyhan
 */
public abstract class Player {

    private int id; // id of the player
    private playerType type; //type of player
    private Field board;//playing baord of the player
    private boolean dropOut;//sets true if the payer drop outs

    /**
     * default constructor
     *
     * @param id is the id of player
     * @param type is the type of player
     * @param board is the board of player
     */
    public Player(int id, playerType type, Field board) {
        this.id = id;
        this.type = type;
        this.board = board;
        dropOut = false;
    }

    /**
     * sets the drop out
     *
     * @param dropout
     */
    public void setDropOUt(boolean dropout) {
        this.dropOut = dropout;
    }

    /**
     * gets the drop out value
     *
     * @return
     */
    public boolean getDropOut() {
        return this.dropOut;
    }

    /**
     * gets the id of player
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * gets the type of player
     *
     * @return the type
     */
    public playerType getType() {
        return type;
    }

    /**
     * gets the board of the player
     *
     * @return an object of board
     */
    public Field getBoard() {
        return board;
    }

    /**
     * return true if the player can play
     *
     * @return a boolean value
     */
    protected boolean canPlay() {
        return true;
    }

    /**
     * used for BOt only and return the an object of choose where he can move
     *
     * @param neighbors the list of availabe neighbors
     * @param falsNum is the flag point
     * @return an object of choose class
     */
    protected Choose ai(List<Position> neighbors, int falsNum) {
        return null;
    }

    /**
     * gets the chosen position, type and point
     *
     * @return
     */
    protected Choose getChoose() {
        return null;
    }

    /**
     * if the given position is jewel then return the position,type and point of
     * that
     *
     * @param pos
     * @return an object of choose
     */
    protected Choose isJewel(Position pos) {
        return null;
    }
}
