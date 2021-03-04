package logic;

import java.util.List;

/**
 * this class is implemented for another type of players (BOT) with all required
 * methods. This also calculate the strategy for the selecting the best cell for
 * moving.
 *
 * @author Reyhan
 */
public class Bot extends Player {

    private Choose chosen = null;//chosen position with type and point of that 

    /**
     * default constructor
     *
     * @param id of player
     * @param type is the type of player
     * @param board is the board of player
     */
    public Bot(int id, playerType type, Field board) {
        super(id, playerType.BOT, board);
        chosen = null;
    }

    /**
     * gets the chosen object by AI
     *
     * @return
     */
    protected Choose getChoose() {
        return this.chosen;
    }

    /**
     * this method goes through all cells of the field and find the best cell
     * for laying the dice according to these preferences:Puzzle piece, flag,
     * Jewel, Rocket, Bomb, Key, Keyhole, Line, Completing a line with a die
     * most significant first ,any cell. f several cells can be reached within a
     * preference, the cell that is furthest up and then furthest left must be
     * selected.
     *
     * I changed the new position (i, j ) to new position(j, i) because when the
     * positions in the json file is x is the j and y is the i. So for this
     * reason the order of i and j should change.
     *
     * @param neighbors
     * @param flagNum
     * @return
     */
    protected Choose ai(List<Position> neighbors, int flagNum) {
        chosen = null;
        Choose temp = null;
        for (int i = 0; i < this.getBoard().getBoard().length; i++) {
            for (int j = 0; j < this.getBoard().getBoard()[i].length; j++) {
                boolean isInList = false;
                // go through the neighbors of the current player
                for (int k = 0; k < neighbors.size(); k++) {
                    //checks if the current position exist in the neighbor list
                    if (this.getBoard().getBoard()[i][j] != null && neighbors.get(k).equals(new Position(i, j))) {

                        if (this.getBoard().getBoard()[i][j].getType() != null) {
                            temp = new Choose(new Position(i, j), this.getBoard().getBoard()[i][j].getPoint(), this.getBoard().getBoard()[i][j].getType());
                            chooseBasedOridinal(chosen, temp);//return the most valuable symbol
                        }
                        //checks if the position complete one row
                        if (this.getBoard().isHorizontalLines(new Position(i, j)) != null) {
                            isInList = true;
                            isInLine(new Position(i, j));
                        }
                        //checks if the position complete one column
                        if (this.getBoard().isVerticalLines(new Position(i, j)) != null) {
                            isInList = true;
                            isInLine(new Position(i, j));
                        }

                    }
                }
            }
        }
        return chosen;
    }

    /**
     * checks if the position complete the row or column, then it gets the most
     * valuable type(according to their points) and store in choose object
     *
     * @param pos
     */
    private void isInLine(Position pos) {

        if (chosen == null /*|| (chosen.getType() != Types.HORIZONTALLINES && chosen.getType() != Types.VERTICALLINES)*/) {
            // if this position complete the row then store it in a choose object
            if (this.getBoard().isHorizontalLines(pos) != null) {
                chooseBasedOridinal(chosen, this.getBoard().isHorizontalLines(pos));
            }
            // is this position complete the column then store it in a choose object
            if (this.getBoard().isVerticalLines(pos) != null) {
                chooseBasedOridinal(chosen, this.getBoard().isVerticalLines(pos));
            }
        } else {
            //if this position chosen type is vertical or horizontal and checks the point of that with the point of row
            if (this.getBoard().isHorizontalLines(pos) != null && this.getBoard().isHorizontalLines(pos).getPoint() > chosen.getPoint()) {
                chosen = this.getBoard().isHorizontalLines(pos);
            }
            //compare the point of chosen with the point of vertical line point and store the bigger point in the chosen
            if (this.getBoard().isVerticalLines(pos) != null && this.getBoard().isVerticalLines(pos).getPoint() > chosen.getPoint()) {

                chosen = this.getBoard().isVerticalLines(pos);
            }
        }

    }

    /**
     * compare the priority of two given choose object and return the lowest
     * priority and store in chosen object
     *
     * @param main is a choose object to compare
     * @param temp is the second choose object to compare
     */
    private void chooseBasedOridinal(Choose main, Choose temp) {

        if (main == null) {
            chosen = temp;
            //checks if both has the same prority then gets the most valuable(has high point) choose
        } else if (temp.getType() != Types.noType && main.getType().getPriority() == temp.getType().getPriority()) {
            if (this.getBoard().getBoard()[temp.getPos().getX()][temp.getPos().getY()].getPoint()
                    > this.getBoard().getBoard()[main.getPos().getX()][main.getPos().getY()].getPoint()) {
                chosen = temp;
            }

        } else {
            //gets the choose which has  lowest prority
            if (temp.getType().getPriority() < main.getType().getPriority()) {
                chosen = temp;
            }
        }
    }

}
