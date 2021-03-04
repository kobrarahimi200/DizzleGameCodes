/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.util.List;

/**
 * The GUIConnector is an interface for connecting the logic with the GUI. It is
 * responsible for executing any change or update on the game graphical
 * interface which is initiated by the logic. The class JavaFXGUI implements
 * this class.
 *
 * @author Reyhan
 */
public interface GUIConnector {

    /**
     * pass one object of level maker to set all essential elements for example
     * sets all symbols to their positions.For each player fill his board with
     * related symbols which is read from the given json file.
     *
     * @param leveObj is an object of json file
     * @param players is the number of players
     * @param level is the current level
     */
    public void setLevelData(LevelMaker leveObj, int players, int level);

    /**
     * sets the images of given list to the image view dices in the gui
     *
     * @param dices
     */
    public void setDicesInGrid(List<Integer> dices);

    /**
     * update the label of current round and current turn number
     *
     * @param round is the current round
     * @param turn is current turn
     */
    public void updateLabels(int round, int turn);

    /**
     * highlight the given position with green background color
     *
     * @param pos is the given position to highlight
     */
    public void highlightPos(Position pos);

    /**
     * adding the given value die to the given position for the given id player
     *
     * @param pos is the position which die should displayed
     * @param value is the die value
     * @param id is the id of player
     */
    public void addDiceToClickedPos(Position pos, int value, int id);

    /**
     * remove the highlight from the given position
     *
     * @param pos is the given position
     */
    public void removeHighlightPos(Position pos);

    /**
     * add the crossed image to the given position for the given id player
     *
     * @param get is the given position to show the crossed image
     * @param id is the id of the player
     */
    public void putCrossedInFilledPos(Position get, int id);

    /**
     * displays the given string into the log area
     *
     * @param states
     */
    public void displayProtocol(List<String> states);

    /**
     * when the given value is true two buttons roll and drop out is disabled.
     * when it is false then these buttons are enabled
     *
     * @param canPlay is the boolean value to set to the buttons
     */
    public void disableDropAndRollBtn(boolean canPlay);

    /**
     * when a player is dropped out then a pen should displayed in his board
     *
     * @param idPlayer is id of the player that he is dropped out
     */
    public void showPenOnBoard(int idPlayer);

    /**
     * clear all symbols and images from board. remove all children from all
     * groups, image views, gridpanes and panes. Clear all labels. Enable the
     * human field
     */
    public void clear();

    /**
     * remove pen image from the board with given id player
     *
     * @param idPlayer is the id of player
     */
    public void removePenFromBoard(int idPlayer);

    /**
     * remove dice from the given position of the given id player
     *
     * @param pos
     * @param id is the id of player
     */
    public void removeDiceFromBoard(Position pos, int id);

    /**
     * update the point of the given type in the point table
     *
     * @param types is the type which his point is updating
     * @param point is the new point
     */
    public void addCounter(Types types, int point);

    /**
     * displays the exploded image into the given position for the given player
     * id
     *
     * @param pos is the position which exploded should display
     * @param id is the id of player
     */
    public void putExplodedPic(Position pos, int id);

    /**
     * During loading the game or reading from json file, If these files have
     * format problem then game cannot load them and show the corresponding
     * error.
     *
     * @param x is the error number
     */
    public void showMsg(int x);

    /**
     * when a given player id won the game a alert window is displayed and the
     * given max value with id is shown there.
     *
     * @param winnerInfo is the list of winner ids
     */
    public void gameWon(List<Integer> winnerInfo);

    /**
     * send the images of the point table to the setImageToPointTable method to
     * set the images into their coordinates. Each level has different point
     * table images(elements)
     */
    public void fillPointTable(int levelNumber);

    /**
     * display the total score for human player in each turn
     *
     * @param x
     */
    public void passTotalScore(int x);
}
