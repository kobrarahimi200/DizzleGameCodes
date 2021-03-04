package logic;

/**
 * Before the user makes his move, he can save the current game or load a saved
 * game.
 *
 * A save file contains values for:
 *
 * the level played, the current round, the index of the player whose turn is
 * played (index starting at 0 for the human player), the display in the form of
 * an array with the pips of the cubes displayed, each participating player
 * whether he still participates in the round (did dropped out) checked cells
 * cells with dice on it exploded cells as the number of players who have
 * reached the flag(from the lms)
 *
 * @author Reyhan
 */
public class Save {

    private int levelNo;//the level played
    private int round;//the current round
    private int turnOf;//the index of the player whose turn is played
    private int[] dice;//he display in the form of an array with the pips of the cubes displayed
    private Players[] players;//ach participating player whether he still participates in the round

    public int getLevelNo() {
        return levelNo;
    }

    /**
     * sets the level number
     *
     * @param levelNo
     */
    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    /**
     * gets the round number
     *
     * @return
     */
    public int getRound() {
        return round;
    }

    /**
     * sets the round number
     *
     * @param round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * gets the turn number
     *
     * @return
     */
    public int getTurnOf() {
        return turnOf;
    }

    /**
     * sets the turn number
     *
     * @param turnOf
     */
    public void setTurnOf(int turnOf) {
        this.turnOf = turnOf;
    }

    /**
     * gets the dice array
     *
     * @return
     */
    public int[] getDice() {
        return dice;
    }

    /**
     * sets the dice array
     *
     * @param dice
     */
    public void setDice(int[] dice) {
        this.dice = dice;
    }

    /**
     * gets the player array
     *
     * @return
     */
    public Players[] getPlayers() {
        return players;
    }

    /**
     * sets the player array
     *
     * @param players
     */
    public void setPlayers(Players[] players) {
        this.players = players;
    }

}
