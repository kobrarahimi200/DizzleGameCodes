package logic;

/**
 * This class is used by Save class to store the players into an array.
 *
 * @author Reyhan
 */
public class Players {

    private boolean active;//is true when a player is not drop out
    private Position[] checked;// crossed list
    private Position[] diceOn; //filled list
    private Position[] exploded;//exploded
    private int flagReachedAs;//is the point of reached flag

    /**
     * return true if the player is not drop out or have dice to play
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     * sets the active value
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * return cells with dice on it
     *
     * @return a array of crossed positions
     */
    public Position[] getChecked() {
        return checked;
    }

    /**
     * sets the filled positions
     *
     * @param checked
     */
    public void setChecked(Position[] checked) {
        this.checked = checked;
    }

    /**
     * get the positions which are filled
     *
     * @return
     */
    public Position[] getDiceOn() {
        return diceOn;
    }

    /**
     * sets the position which dice are laid
     *
     * @param diceOn
     */
    public void setDiceOn(Position[] diceOn) {
        this.diceOn = diceOn;
    }

    /**
     * get the exploded positions
     *
     * @return an array of exploded positions
     */
    public Position[] getExploded() {
        return exploded;
    }

    /**
     * sets the exploded positions
     *
     * @param exploded
     */
    public void setExploded(Position[] exploded) {
        this.exploded = exploded;
    }

    /**
     * gets the flag number
     *
     * @return flag number
     */
    public int getFlagReachedAs() {
        return flagReachedAs;
    }

    /**
     * sets the flag number
     *
     * @param flagReachedAs
     */
    public void setFlagReachedAs(int flagReachedAs) {
        this.flagReachedAs = flagReachedAs;
    }

}
