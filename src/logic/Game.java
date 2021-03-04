package logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Game class is the main class for this game. All objects and elements which
 * are required for dizzle game are created and initialized in this class. It
 * has three necessary constructor first one is used for creating the game,
 * second is for testing and the third one is used for loading the game. Game
 * class organizes other classes and also the flow of the game. From start the
 * game until the end of the game, this class knows in every step which method
 * should be called and processed.First constructor creates a game with given
 * parameters, creates field for playing, creates array of players, creates and
 * every elements that we need to play.
 *
 * @author Reyhan
 */
public class Game {

    private final int numOfPlayers;
    private final int levelNumber;//level number of the game
    private final GUIConnector gui;// object of the gui
    private IO io;//object for the IO operation like reading , writing data to the file
    private final LevelMaker levelObj;//an object of the read json data 
    private List<Integer> dices = new LinkedList<>();// list of all available dices
    private Player[] players;// array of players
    private final int numOfDices;// number of dices
    private int idxCurrPlayer;// id of current player

    private boolean gameIsWon = false;// determine the win
    private List<Integer> idWinner;// the list of winners id
    private int numOfRounds;//number of rounds
    private int numOfTurns = 0;//numner of turns
    private int curRound;// current round
    private Log logger;// an object used for logging the game
    private int flagsNum;//flag number 
    private boolean roll;//set to true when a player cannot play  
    private int indexFlag = 0;//index of the used flag in the flag array
    private boolean isFlag = false;// is true when current cell contains flag
    private int[] flags = new int[]{10, 6, 3, 1};//the array popits of flag

    /**
     * default constructor to set the number of player, level number, fill the
     * dice list, initialize the gui and set level data to an object of level
     * maker.
     *
     * @param numOfPlayers
     * @param levelNumber
     * @param gui
     * @throws FileNotFoundException
     */
    public Game(int numOfPlayers, int levelNumber, GUIConnector gui) throws FileNotFoundException {
        this.numOfPlayers = numOfPlayers;
        this.idxCurrPlayer = 0;
        this.gui = gui;
        gameIsWon = false;
        curRound = 0;
        this.levelNumber = levelNumber;
        levelObj = parseJsonFile(levelNumber);
        numOfRounds = setNumOfRounds(numOfPlayers);
        int plyerId = 0;
        players = new Player[numOfPlayers];
        for (int i = 0; i < 1; i++) {//build the human player
            players[i] = new Human(i, playerType.HUMAN, new Field(levelObj, levelNumber));
            plyerId = i;
        }
        for (int k = plyerId + 1; k < numOfPlayers; k++) {//build bot players
            players[k] = new Bot(k, playerType.BOT, new Field(levelObj, levelNumber));
        }
        this.gui.setLevelData(levelObj, numOfPlayers, levelNumber);
        numOfDices = setNumOfDices(numOfPlayers);// calculate the number of dices accoring to number of players
        fillDices();//fill a list for dices
        this.gui.setDicesInGrid(dices);
        addToNeihbors(players[idxCurrPlayer].getBoard().getAddCrossedNeighbors());
        this.logger = new Log("dizzle.log");
        roll = false; // used when a player cannot play again after rolling
        idWinner = new LinkedList<>();
        this.gui.updateLabels(curRound + 1, numOfTurns);//update the round and turn labels
        this.gui.fillPointTable(levelNumber);//sets the images for the point table
    }

    /**
     * this constructor is used for testing
     *
     * @param numOfPlayers
     * @param levelNumber
     * @param dices
     * @param gui
     * @throws FileNotFoundException
     */
    public Game(int numOfPlayers, int levelNumber,
            List<Integer> dices, GUIConnector gui) throws FileNotFoundException {
        this.numOfPlayers = numOfPlayers;
        this.idxCurrPlayer = 0;
        this.gui = gui;
        this.levelNumber = levelNumber;
        levelObj = parseJsonFile(levelNumber);

        numOfDices = setNumOfDices(numOfPlayers);
        players = new Player[numOfPlayers];
        int plyerId = 0;
        for (int i = 0; i < 1; i++) {
            players[i] = new Human(i, playerType.HUMAN, new Field(levelObj, levelNumber));
            plyerId = i;
        }
        for (int k = plyerId + 1; k < numOfPlayers; k++) {
            players[k] = new Bot(k, playerType.BOT, new Field(levelObj, levelNumber));
        }
        this.dices = dices;
        addToNeihbors(players[idxCurrPlayer].getBoard().getAddCrossedNeighbors());
        this.logger = new Log("dizzle.log");
    }

    /**
     * This constructor is used for loading the game.
     *
     * @param currPlayer
     * @param players
     * @param round
     * @param turn
     * @param levelNumber
     * @param gui
     * @throws FileNotFoundException
     */
    public Game(int currPlayer, int levelNumber, Players[] players, int round, int turn,
            int[] diceArray, GUIConnector gui) throws FileNotFoundException {
        this.numOfPlayers = players.length;
        this.idxCurrPlayer = currPlayer;
        this.gui = gui;
        gameIsWon = false;
        this.levelNumber = levelNumber;
        levelObj = parseJsonFile(levelNumber);
        curRound = round;//current round
        numOfTurns = turn;//current turn
        numOfRounds = setNumOfRounds(numOfPlayers);//setting the number of rounds
        int plyerId = 0;
        this.players = new Player[numOfPlayers];
        for (int i = 0; i < 1; i++) {
            this.players[i] = new Human(i, playerType.HUMAN, new Field(levelObj, levelNumber));
            plyerId = i;
        }
        for (int k = plyerId + 1; k < numOfPlayers; k++) {
            this.players[k] = new Bot(k, playerType.BOT, new Field(levelObj, levelNumber));
        }
        //clear the dices and set it again with the new dice list
        numOfDices = setNumOfDices(numOfPlayers);
        this.dices.clear();
        for (int i : diceArray) {
            this.dices.add(Integer.valueOf(i));
        }
        Collections.sort(dices);
        this.gui.clear();

        this.gui.setDicesInGrid(dices);//show dices in the gui
        this.gui.setLevelData(levelObj, numOfPlayers, levelNumber);//fill the board with symbols
        this.gui.updateLabels(curRound + 1, numOfTurns);//update the round and turn labels
        loadInBoard(players);
        this.gui.fillPointTable(levelNumber);//sets the images for the point table
        idWinner = new LinkedList<>();//store the id of winners
        fillPlayersNeighborsList();
        this.logger = new Log("dizzle.log");
        roll = false; // used when a player cannot play again after rolling
        updateLoadedData();
        if(checkWinner()){
           endGame(getLastPoint());
        }
    }

    /**
     * gets the number of the dices
     *
     * @return number of dices
     */
    protected int getNumOfDices() {
        return this.numOfDices;
    }

    /**
     * gets the number of the rounds
     *
     * @return number of round
     */
    protected int getNumOfrounds() {
        return this.numOfRounds;
    }

    /**
     * gets the current player id
     *
     * @return the id of the player
     */
    protected int getCurrPlayer() {
        return this.idxCurrPlayer;
    }

    /**
     * gets the array of the players
     *
     * @return an array of players
     */
    protected Player[] getPlayers() {
        return this.players;
    }
   /**
     * gets the list of the dices(need for testing)
     *
     * @return an array of players
     */
    protected List<Integer> getDices() {
        return this.dices;
    }
    /**
     * This method reads the values from the json file according to the selected
     * level number.
     *
     * @param level is the selected level of the game
     * @return an object of levelMaker for the gui
     * @throws FileNotFoundException
     */
    LevelMaker parseJsonFile(int level) throws FileNotFoundException {
        this.io = new IO(gui);
        return io.getJsonValues(level);
    }

    /**
     * sets the number of dices according to the given number of players. if we
     * have two players the dices are 7, if we have three players dices are 10
     * and if we have four players dices are 13.
     *
     * @param numOfPlayers
     * @return
     */
    private int setNumOfDices(int numOfPlayers) {
        switch (numOfPlayers) {
            case 2:
                return 7;
            case 3:
                return 10;
            default:
                return 13;
        }
    }

    /**
     * sets the number of dices according to the given number of players. if we
     * have two players the dices are 7, if we have three players dices are 10
     * and if we have four players dices are 13.
     *
     * @param numOfPlayers
     * @return
     */
    private int setNumOfRounds(int numOfPlayers) {
        switch (numOfPlayers) {
            case 1:
                return 10;
            case 2:
                return 6;
            case 3:
                return 4;
            default:
                return 3;
        }
    }

    /**
     * according to the number of dices, a random number between 1 and 6 is
     * selected and added to the list. at the end sort the dice in ascending
     * order.
     *
     * @return
     */
    List<Integer> fillDices() {
        this.dices.clear();
        for (int i = 0; i < numOfDices; i++) {
            this.dices.add(selectRandom());
        }
        Collections.sort(dices);
        return dices;
    }

    /**
     * returns a random number between one and six.
     *
     * @param array
     * @return
     */
    int selectRandom() {
        int min = 1;
        int max = 6;
        int rnd = new Random().nextInt(max - min + 1) + min;
        return rnd;
    }

    /**
     * when human player clicks on the cell, that position sent to this method.
     * checks if the neighbors of the given positions are available then laid
     * the dice into this position, gui is updating the laid dice is removed
     * from the dice list. this position is added to the filled list and next
     * player is called
     *
     * @param pos
     */
    public void setOnBoard(Position pos) {
        //checks if the given position is in the neighbor list
        if (isNeighbors(pos) && !roll) {

            for (int j = 0; j < this.players[idxCurrPlayer].getBoard().getNeighbors().size(); j++) {//remove highlihted effect of neighbors
                this.gui.removeHighlightPos(this.players[idxCurrPlayer].getBoard().getNeighbors().get(j));
            }

            players[idxCurrPlayer].getBoard().setLaidDicePos(pos);//get the current laid dice value
            //adding the dice to the given position
            this.gui.addDiceToClickedPos(pos, players[idxCurrPlayer].getBoard().getCellValue(pos), idxCurrPlayer);
            //add given position to the filled list
            players[idxCurrPlayer].getBoard().addToFilledPos(pos);
            removeFromDice(pos);
            //checksFlagPoint(pos);//we need it for array of 
            if (isLastActivePlayer()) {//if all players drop out
                players[idxCurrPlayer].setDropOUt(true);
            }
            nextPlayer();
        } else {
            //checks if the given position is filled before and roll button is active
            if (players[idxCurrPlayer].getBoard().getBoard()[pos.getX()][pos.getY()].isFilled() && roll) {

                players[idxCurrPlayer].getBoard().getBoard()[pos.getX()][pos.getY()].setFilled(false);
                this.gui.removeDiceFromBoard(pos, idxCurrPlayer);//remove dice when player after rolling again cannot play again 
                // he should do the penalty and return one dice to the dices.
                dices.add(players[idxCurrPlayer].getBoard().getCellValue(pos));//return the clicked pos to the dices
                this.gui.setDicesInGrid(dices);
                roll = false;
                nextPlayer();
            }
        }
    }

    /**
     * return true if neighbors list contains the given position.
     *
     * @param pos
     * @return
     */
    private boolean isNeighbors(Position pos) {
        boolean isNeighbor = false;
        for (int i = 0; i < players[idxCurrPlayer].getBoard().getNeighbors().size() && !isNeighbor; i++) {
            if (pos.equals(players[idxCurrPlayer].getBoard().getNeighbors().get(i))) {
                isNeighbor = true;
            }
        }
        return isNeighbor;
    }

    /**
     * Only when a player is no longer able to place dice according to the rules
     * are they allowed to choose to roll again or drop out.
     *
     * @return true if current player has enough dice to play
     */
    private boolean canPlay() {
        boolean canPlay = true;
        if (players[idxCurrPlayer].getBoard().getNeighbors().isEmpty()) {
            canPlay = false;
        }
        return canPlay;
    }

    /**
     * if game is not ended, checks if dice list is empty or all players drop
     * out and if current turn is finished, then new turn is started and id of
     * player and turn number is reset. dice list is filling and all previous
     * crossed positions are moved to the filled list
     */
    protected void nextPlayer() {
        this.logger.logging(players, idxCurrPlayer);
        this.gui.displayProtocol(logger.logLists);
        //checkWinner();
        if (!checkWinner()) {
            if (this.dices.isEmpty() || allDropOut()) {
                if (numOfTurns == players.length - 1) {
                    curRound++;
                    numOfTurns = 0;
                    idxCurrPlayer = 0;
                    fillDices();
                    this.gui.setDicesInGrid(dices);
                    resetDropOut();
                    resetTurn();
                    // filled pos move to crossed list and crossed the filled positions
                    moveToCrossedList();
                } else {
                    numOfTurns++;
                    idxCurrPlayer = numOfTurns;
                    fillDices();
                    this.gui.setDicesInGrid(dices);
                    resetDropOut();
                    resetTurn();
                    moveToCrossedList();
                }
            } else {
                this.idxCurrPlayer = (this.idxCurrPlayer + 1) % players.length;//next player
            }
            fillPlayersNeighborsList();//fill the player neighbors list
            if (!canPlay() && players[idxCurrPlayer].getType() == playerType.HUMAN) {
                this.gui.disableDropAndRollBtn(canPlay());
            }
            //updates the turn  and round labels in the gui
            this.gui.updateLabels(curRound + 1, numOfTurns + 1);
            //if curent player is bot the botTuen should call
            if (players[idxCurrPlayer].getType() == playerType.BOT) {
                botsTurn();
            }
            //if current player is ddopped out he should skip this turn and next player is called
            if (players[idxCurrPlayer].getDropOut()) {
                nextPlayer();
            }
        } else {
            //game is finished and endGame is called
            if (!gameIsWon) {
                gameIsWon = true;
                moveToCrossedList();
                endGame(getLastPoint());
            }
        }
    }

    /**
     * when a one turn is finished, all filled positions should move to the
     * filled positions list and and in the gui, the diad dices should remove
     * from the board and replaced with the crossed.
     */
    protected void moveToCrossedList() {
        List<Position> bombs = new LinkedList<>();
        Types type = null;
        int point = 0;
        for (int k = 0; k < players.length; k++) {
            for (int i = 0; i < players[k].getBoard().getBoard().length; i++) {
                for (int j = 0; j < players[k].getBoard().getBoard()[i].length; j++) {
                    //chcks if the cell is occupied 
                    if (players[k].getBoard().getBoard()[i][j].isFilled()) {
                        players[k].getBoard().addToCrossedList(new Position(i, j));
                        type = players[k].getBoard().getBoard()[i][j].getType();
                        point = players[k].getBoard().getBoard()[i][j].getPoint();
                        // Whoever has crossed out all puzzle-fields of the same color earns the corresponding points denoted on the sheet
                        if (type.equals(Types.PuzzleBlue) || type.equals(Types.PuzzleGreen)) {
                            if (players[k].getBoard().checkIfAllPuzzlesFilled(type)
                                    && players[k].getBoard().getPointsList().get(type) == 0) {//if before the point is not added 
                                players[k].getBoard().getPointsList().put(type, point);
                                updatePointTableValues(type, k);//update the point table in the gui

                            }
                            //players who have a die on a field with a "flag" can earn their reached points.
                        } else if (type.equals(Types.FLAG)) {
                            isFlag = true;
                            flagsNum = flags[indexFlag];
                            players[k].getBoard().getPointsList().put(type, flags[indexFlag]);
                            updatePointTableValues(type, k);

                            //The player who has put a die on the bomb puts a regular  on it. All the other players
                            //must strike out the same bomb-field on their game sheets with exploded picture
                        } else if (type == Types.BOMBS) {
                            if (!players[k].getBoard().isContains(new Position(i, j), bombs)) {
                                bombs.add(new Position(i, j));
                            }
                        } else {
                            if (point > 0) {//add the other cell which has point , add them into the point list
                                players[k].getBoard().getPointsList().put(type, players[k].getBoard().getPointsList().get(type) + point);
                                updatePointTableValues(type, k);
                            }
                        }
                        // if each row completely crossed out with an arrow provides the displayed points.
                        if (players[k].getBoard().isCrossedHorizontalLines(new Position(i, j)) > 0) {
                            players[k].getBoard().getPointsList().put(Types.HORIZONTALLINES,
                                    players[k].getBoard().getPointsList().get(Types.HORIZONTALLINES)
                                    + players[k].getBoard().isCrossedHorizontalLines(new Position(i, j)));
                            updatePointTableValues(Types.HORIZONTALLINES, k);
                        }
                        //if one column is crossed out the player gets the point of that column(if that has a point)
                        if (players[k].getBoard().isCrossedVerticalLines(new Position(i, j)) > 0) {
                            players[k].getBoard().getPointsList().put(Types.VERTICALLINES,
                                    players[k].getBoard().getPointsList().get(Types.VERTICALLINES)
                                    + players[k].getBoard().isCrossedVerticalLines(new Position(i, j)));
                            updatePointTableValues(Types.VERTICALLINES, k);

                        }

                        // A player is only allowed to put a die on a lock-field when they have 
                        //already covered the respective key-field with an  in a previous turn.
                        //On Level 2 game sheets, there are keys in different colors. 
                        //A player can only open a lock with a key of the same color.
                        if (type == Types.KeyBlue) {
                            players[k].getBoard().setKeyHoleBlue(true);
                        }
                        if (type == Types.KeyYellow) {
                            players[k].getBoard().setKeyHoleYellow(true);
                        }
                        // a player who crosses out a rocket-field must immediately put 1 more X on a planet-field of their choice.
                        if (type == Types.ROCKET) {
                            Position planet = players[k].getBoard().crossedPlanetPos();
                            this.gui.putCrossedInFilledPos(planet, k);
                        }
                        this.gui.removeDiceFromBoard(new Position(i, j), k);
                        if (type != Types.BOMBS) {
                            this.gui.putCrossedInFilledPos(new Position(i, j), k);
                        }
                    }
                }
            }
        }
        setExplodedOnPos(bombs);
        if (isFlag) {
            indexFlag++;
            isFlag = false;
        }
        this.gui.passTotalScore(getTotalScore());

    }

    /**
     * update the point table in the gui for the given type.The point table is
     * only used for the human player.
     *
     * @param type
     */
    private void updatePointTableValues(Types type, int id) {
        if (players[id].getType() == playerType.HUMAN) {
            this.gui.addCounter(type, players[id].getBoard().getPointsList().get(type));// update the lable of point table in left side of the gui
        }
    }

    /**
     * gets the earned points of human player and add them together(summing up
     * points)
     *
     * @return total score of human player per turn
     */
    private int getTotalScore() {
        int point = 0;
        for (Map.Entry<Types, Integer> e : players[0].getBoard().getPointsList().entrySet()) {
            Types key = e.getKey();
            Integer value = e.getValue();
            //bomb has minus points
            if (!value.equals(null)) {
                if (key == Types.BOMBS) {
                    point -= value;
                } else {
                    point += value;
                }
            }
        }
        return point;
    }

    /**
     * for all players, checks every position from the list and set the crossed
     * attribute to true and then insert the exploded image into these
     * positions.
     *
     * @param list is the list of bomb which the dice are laid
     */
    private void setExplodedOnPos(List<Position> list) {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                //checks if the current position is still not occupied
                if (!players[i].getBoard().getBoard()[list.get(j).getX()][list.get(j).getY()].isCrossed()) {
                    players[i].getBoard().getBoard()[list.get(j).getX()][list.get(j).getY()].setExploded(true);
                    players[i].getBoard().getPointsList().put(Types.BOMBS,
                            players[i].getBoard().getPointsList().get(Types.BOMBS) + 2);
                    this.gui.putExplodedPic(new Position(list.get(j).getX(), list.get(j).getY()), i);
                    //update the points of the bomb value for human payer in the gui
                    if (players[i].getType() == playerType.HUMAN) {
                        updatePointTableValues(Types.BOMBS, i);
                    }
                } else {//this position is not filled
                    players[i].getBoard().getBoard()[list.get(j).getX()][list.get(j).getY()].setCrossed(true);
                    this.gui.putCrossedInFilledPos(new Position(list.get(j).getX(), list.get(j).getY()), i);
                }
            }
        }
    }

    /**
     * when a turn is finished the following options should reset: pen should
     * remove from the boards. the dropout boolean values sets to false. the
     * buttons , roll and drop out should be disable.
     */
    private void resetTurn() {
        //remove pen from 
        for (int i = 0; i < players.length; i++) {
            this.gui.removePenFromBoard(i);
        }
        //disable buttons roll and dropout
        players[idxCurrPlayer].setDropOUt(false);
        this.gui.disableDropAndRollBtn(true);
    }

    /**
     * if all players cannot play and their drop out values are true,
     *
     * then this method returns true.
     *
     * @return a boolean value
     */
    private boolean allDropOut() {
        for (Player player : players) {
            if (!player.getDropOut()) {
                return false;
            }
        }
        return true;
    }

    /**
     * for all players sets the the boolean value of drop out to false.
     */
    private void resetDropOut() {
        for (Player player : players) {
            player.setDropOUt(false);
        }
    }

    /**
     * When all players but one have dropped out, the last active player has
     * exactly one more opportunity to choose a die.
     *
     * They can take a die from the table, roll again, or drop out if none of
     * the dice can be placed according to the rules. Once this last active
     * player makes their choice, this round is over for everyone.
     *
     * @return true if all players drop out
     */
    private boolean isLastActivePlayer() {
        int temp = players.length;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getDropOut()) {
                temp--;
            }
        }
        return temp > 1 ? false : true;
    }

    /**
     * at first clears the neighbors list of the current player, then checks if
     * the filled list is empty if true then the neighbors list is filled with
     * the neighbors of the crossed positions, otherwise, is filled with the
     * neighbors of filled positions
     */
    private void fillPlayersNeighborsList() {
        players[idxCurrPlayer].getBoard().getNeighbors().clear();
        if (players[idxCurrPlayer].getBoard().getFilledNeighbors().isEmpty()) {
            addToNeihbors(players[idxCurrPlayer].getBoard().getAddCrossedNeighbors());
        } else {
            addToNeihbors(players[idxCurrPlayer].getBoard().getFilledNeighbors());
        }
    }

    /**
     * The game ends once all rounds have been played and all players cannot
     * play anymore. Or game is ended if all rounds and turns are finished and
     * there are no dices any more in the dice list
     *
     *
     */
    private boolean checkWinner() {
        if (players[idxCurrPlayer].getBoard().allCrossedPos()
                || (curRound >= numOfRounds - 1 && numOfTurns >= players.length - 1 && allDropOut())
                || (curRound >= numOfRounds - 1 && numOfTurns >= players.length - 1 && dices.isEmpty())) {
            return true;
        }
        return false;
    }

    /**
     * Each player adds up the points they earned for special fields minus the
     * points for bombs and brown piles.if two players got the same earned point
     * whoever has lower crossed positions in his field he won the game. if
     * their crossed positions are the same then both of them are winners.
     * Whoever earned the most points wins the game.
     *
     * @return
     */
    private List<Integer> getLastPoint() {
        int[] point = new int[numOfPlayers];
        List<Integer> temp = new LinkedList<>();
        for (int i = 0; i < players.length; i++) {
            //collects all earned poitn for each player and store them in point array
            for (Map.Entry<Types, Integer> e : players[i].getBoard().getPointsList().entrySet()) {
                Types key = e.getKey();
                Integer value = e.getValue();
                if (!value.equals(null)) {
                    if (key == Types.BOMBS) {
                        point[i] -= value;
                    } else {
                        point[i] += value;
                    }
                }
            }
        }
        int max = 0;//gets the highest score from the point array 
        for (int i = 0; i < point.length; i++) {
            if (point[i] > max) {
                max = point[i];
                idWinner.clear();
                idWinner.add(i);// id of the winner
            } else {
                if (point[i] == max) {
                    //if two player gets the same score, then the number of crossed position should compare, whoever has less crossed cell won the game
                    if (players[idWinner.get(0)].getBoard().getCrossedList().size() > players[i].getBoard().getCrossedList().size()) {
                        idWinner.clear();
                        idWinner.add(i);
                    } else {
                        //if two player has the same size of crossed positions then both of them are winner,
                        if (players[idWinner.get(0)].getBoard().getCrossedList().size() == players[i].getBoard().getCrossedList().size()) {
                            idWinner.add(i);
                        }
                    }
                }
            }
        }
        temp.add(max);
        //adding all idWinner value into temp
        for (int i = 0; i < idWinner.size(); i++) {
            temp.add(idWinner.get(i));
        }
        return temp;
    }

    /**
     * when player is AI this method is called. checks and select the optimal
     * route for moving.
     */
    void botsTurn() {
        Choose temp = null;
        players[idxCurrPlayer].getBoard().getNeighbors().clear();
        //checks if the filled list is emtpy then gets all crossed neighbors otherwise gets filled neighbors positions
        if (players[idxCurrPlayer].getBoard().getFilledNeighbors().isEmpty()) {
            addToNeihborsBot(players[idxCurrPlayer].getBoard().getAddCrossedNeighbors());
        } else {
            addToNeihborsBot(players[idxCurrPlayer].getBoard().getFilledNeighbors());
        }
        //choose the best position for the laying die
        temp = players[idxCurrPlayer].ai(players[idxCurrPlayer].getBoard().getNeighbors(), flagsNum);
        if (temp != null) {//lay the dice in the board and display in the gui
            players[idxCurrPlayer].getBoard().setLaidDicePos(temp.getPos());
            this.gui.addDiceToClickedPos(temp.getPos(), players[idxCurrPlayer].getBoard().getCellValue(temp.getPos()), idxCurrPlayer);
            //when the die is laid to the board after that add it to the filled list
            players[idxCurrPlayer].getBoard().addToFilledPos(temp.getPos());
            removeFromDice(temp.getPos());

        } else {//if ai does not choose any position then player must drop out
            if (!players[idxCurrPlayer].getDropOut()) {
                players[idxCurrPlayer].setDropOUt(true);
                this.gui.showPenOnBoard(idxCurrPlayer);
            }
        }
        if (isLastActivePlayer()) {
            players[idxCurrPlayer].setDropOUt(true);
        }
        nextPlayer();
    }

    /**
     * If a player decides to drop out, they are out of the current turn and can
     * no longer take dice. To show that they have dropped out, they put their
     * pen on their game sheet. However, a player who rolls the dice again,
     * stays in the game.
     */
    public void clickOnDropOut() {
        players[idxCurrPlayer].setDropOUt(true);
        this.gui.disableDropAndRollBtn(true);
        this.gui.showPenOnBoard(idxCurrPlayer);
        nextPlayer();
    }

    /**
     * When a player decides to roll again, they take all the remaining dice
     * from the table and roll them again. Thereby they can acquire more useful
     * dice.
     */
    public void clickRoll() {
        int length = dices.size();
        dices.clear();
        for (int i = 0; i < length; i++) {
            this.dices.add(selectRandom());
        }
        Collections.sort(dices);
        logger.logRollAgain(idxCurrPlayer, dices);//add it to the logger
        this.gui.displayProtocol(logger.logLists);//display in the log area
        addToNeihbors(players[idxCurrPlayer].getBoard().getFilledNeighbors());
        this.gui.setDicesInGrid(dices);
        this.gui.disableDropAndRollBtn(true);
        if (players[idxCurrPlayer].getBoard().getNeighbors().isEmpty()) {
            roll = true;
        }

    }

    /**
     * search in dice list and find a matched value with the value of the given
     * position, if it found a match value a boolean value set to true and the
     * given position removed from the dice list. in last step, the updated dice
     * list is shown in the gui.
     *
     * @param pos
     */
    private void removeFromDice(Position pos) {
        boolean isRemoved = false;
        for (int i = 0; i < dices.size() && !isRemoved; i++) {
            if (dices.get(i) == players[idxCurrPlayer].getBoard().getCellValue(pos)) {
                isRemoved = true;
                dices.remove(i);
                this.gui.setDicesInGrid(dices);
            }
        }
    }

    /**
     * if the neighbor's position of all positions of the given list is valid
     * and is not the crossed position and also is contains in the dice list ,
     * then add these positions to the neighbors list and highlight all
     * neighbors list position in the gui.(this method used for human player)
     *
     * @param list
     */
    protected List<Position> addToNeihbors(List<Position> list) {

        for (int i = 0; i < list.size(); i++) {
            if (isInDiceList(list.get(i))) {
                if (players[idxCurrPlayer].getBoard().getBoard()[list.get(i).getX()][list.get(i).getY()].getType() == Types.KeyHoleBlue) {
                    if (players[idxCurrPlayer].getBoard().isKeyHoleBlue()) {//if the key blue is crosed in prevoius turn
                        this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                    }
                } else if (players[idxCurrPlayer].getBoard().getBoard()[list.get(i).getX()][list.get(i).getY()].getType() == Types.KeyHoleYellow) {
                    if (players[idxCurrPlayer].getBoard().isKeyHoleYellow()) {//if the key yellow is crossed out in previous turn
                        this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                    }
                } else {
                    this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                }
            }
        }
        //highlight all neighbors positions
        if (players[idxCurrPlayer].getType() == playerType.HUMAN) {
            for (int j = 0; j < this.players[idxCurrPlayer].getBoard().getNeighbors().size(); j++) {
                this.gui.highlightPos(this.players[idxCurrPlayer].getBoard().getNeighbors().get(j));
            }
        }
        return this.players[idxCurrPlayer].getBoard().getNeighbors();
    }

    /**
     * if the neighbor's position of all positions of the given list is valid
     * and is not the crossed position and also is contains in the dice list ,
     * then add this position to the neighbors list and highlight all neighbors
     * list position in the gui.(used only for bots)
     *
     * @param list
     */
    protected void addToNeihborsBot(List<Position> list) {

        for (int i = 0; i < list.size(); i++) {
            if (isInDiceList(list.get(i))) {
                if (players[idxCurrPlayer].getBoard().getBoard()[list.get(i).getX()][list.get(i).getY()].getType() == Types.KeyHoleBlue) {
                    if (players[idxCurrPlayer].getBoard().isKeyHoleBlue()) {//if the key blue is crosed in prevoius turn
                        this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                    }
                } else if (players[idxCurrPlayer].getBoard().getBoard()[list.get(i).getX()][list.get(i).getY()].getType() == Types.KeyHoleYellow) {
                    if (players[idxCurrPlayer].getBoard().isKeyHoleYellow()) {//if the key yellow is crossed out in previous turn
                        this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                    }
                } else {
                    this.players[idxCurrPlayer].getBoard().getNeighbors().add(list.get(i));
                }
            }
        }
    }

    /**
     * return true if the value of the given position is equals to the value of
     * the dice list values.
     *
     * @param pos
     * @return
     */
    private boolean isInDiceList(Position pos) {
        boolean isInList = false;
        for (int i = 0; i < dices.size() && !isInList; i++) {
            if (dices.get(i) == players[idxCurrPlayer].getBoard().getCellValue(pos)) {
                isInList = true;
            }
        }
        return isInList;
    }

    /**
     * save the game
     */
    public void save() {
        io.save(players, levelNumber, idxCurrPlayer, curRound +1, this.dices);
    }

    /**
     * loads the game and return an object of this game.
     *
     * @param game
     * @return
     * @throws FileNotFoundException
     */
    public Game load(Game game) throws FileNotFoundException {
        IO load = new IO(gui);
        try {
            game = load.loadFile(this.gui, game);
        } catch (IOException ex) {
        }
        return game;
    }

    /**
     * clear the game
     */
    public void clear() {
        this.gui.clear();
    }

    /**
     * loading the required information to the board when loading the game.this
     * All these positions like filled positions, crossed positions and the
     * exploded positions are loaded into the board and displayed in the
     * gui.(used for loading the game)
     *
     * @param playerSave
     */
    private void loadInBoard(Players[] playerSave) {
        // List<Position> explodedPos = new LinkedList<>();

        for (int i = 0; i < playerSave.length; i++) {
            this.players[i].setDropOUt(!playerSave[i].isActive());
            if (!playerSave[i].isActive()) {//if the player is dropped out show the pen on his board
                this.gui.showPenOnBoard(i);
            }
            //gets all crossed position and update the board with these positions for each player.
            for (Position checked : playerSave[i].getChecked()) {
                this.players[i].getBoard().getBoard()[checked.getX()][checked.getY()].setCrossed(true);
                this.gui.putCrossedInFilledPos(new Position(checked.getX(), checked.getY()), i);
            }
            //gets all filled positions from the loaded file and upated these positions to the board 
            for (Position diceOn : playerSave[i].getDiceOn()) {
                this.players[i].getBoard().getBoard()[diceOn.getX()][diceOn.getY()].setFilled(true);
                this.gui.addDiceToClickedPos(new Position(diceOn.getX(), diceOn.getY()),
                        this.players[i].getBoard().getBoard()[diceOn.getX()][diceOn.getY()].getValue(), i);

            }
            // gets the all exploded positions and updated the relevant board
            for (Position exploded : playerSave[i].getExploded()) {
                this.players[i].getBoard().getBoard()[exploded.getX()][exploded.getY()].setExploded(true);
                this.gui.putExplodedPic(new Position(exploded.getX(), exploded.getY()), i);
            }

            // setExplodedOnPos(explodedPos);
            //players who have a die on a field with a "flag" can earn their reached points.
            if (playerSave[i].getFlagReachedAs() > 0) {
                players[i].getBoard().getPointsList().put(Types.FLAG, playerSave[i].getFlagReachedAs());
                updatePointTableValues(Types.FLAG, i);
                //checks if the player reached the flag
                if (getFalgFromLoad(playerSave[i].getFlagReachedAs()) > indexFlag) {
                    indexFlag = getFalgFromLoad(playerSave[i].getFlagReachedAs());
                    flagsNum = playerSave[i].getFlagReachedAs();
                }
            }
        }
    }

    /**
     * this method gets all symbols which have point and insert them into point
     * table with their point.(update the point table values and keys)(used for
     * loading the game)
     */
    private void updateLoadedData() {
        Types type = null;
        for (int k = 0; k < players.length; k++) {
            //go through the board of each player
            for (int i = 0; i < players[k].getBoard().getBoard().length; i++) {
                for (int j = 0; j < players[k].getBoard().getBoard()[i].length; j++) {
                    // checks if the cell is crossed or exploded then checks the inside condition
                    if (players[k].getBoard().getBoard()[i][j].isCrossed() || players[k].getBoard().getBoard()[i][j].getExploded()) {
                        type = players[k].getBoard().getBoard()[i][j].getType();
                        //check if the type is bomb then insert it into point table
                        if (players[k].getBoard().getBoard()[i][j].getType() == Types.BOMBS) {

                            if (players[k].getBoard().getBoard()[i][j].getExploded()) {
                                players[k].getBoard().getPointsList().put(Types.BOMBS, players[k].getBoard().getPointsList().get(type)
                                        + players[k].getBoard().getBoard()[i][j].getPoint());
                                updatePointTableValues(Types.BOMBS, k);//update the point table in the gui
                            }
                        }
                        //checks if the type is puzzle blue or puzzle green then chcks if both puzzles are crossed then insert them into point list
                        if (type.equals(Types.PuzzleBlue) || type.equals(Types.PuzzleGreen)) {
                            //chceks if both puzzles are crossed
                            if (players[k].getBoard().checkIfAllPuzzlesCrossedInLoadedGame(type)
                                    && players[k].getBoard().getPointsList().get(type) == 0) {//if before the point is not added 
                                players[k].getBoard().getPointsList().put(type, players[k].getBoard().getBoard()[i][j].getPoint());
                                updatePointTableValues(type, k);//update the point table in the gui
                            }
                        } else {
                            if (players[k].getBoard().getBoard()[i][j].getPoint() > 0 && Types.BOMBS != type) {//add the other cell which has point , add them into the point list
                                players[k].getBoard().getPointsList().put(type, players[k].getBoard().getPointsList().get(type)
                                        + players[k].getBoard().getBoard()[i][j].getPoint());
                                updatePointTableValues(type, k);
                            }
                        }
                        //checks if current position complete one row it yes then insert the his point into point list
                        if (checksHorizontalPointInLoad(new Position(i, j))) {
                            if (players[k].getBoard().isCrossedHorizontalLines(new Position(i, j)) > 0) {
                                players[k].getBoard().getPointsList().put(Types.HORIZONTALLINES,
                                        players[k].getBoard().getPointsList().get(Types.HORIZONTALLINES)
                                        + players[k].getBoard().isCrossedHorizontalLines(new Position(i, j)));
                                updatePointTableValues(Types.HORIZONTALLINES, k);
                            }
                        }
                        //checks if current position complete one column it yes then insert the his point into point list
                        if (checksVerticalPointInLoad(new Position(i, j))) {
                            //checks if the current position fill one clumn if yes then update his point 
                            if (players[k].getBoard().isCrossedVerticalLines(new Position(i, j)) > 0) {
                                players[k].getBoard().getPointsList().put(Types.VERTICALLINES,
                                        players[k].getBoard().getPointsList().get(Types.VERTICALLINES)
                                        + players[k].getBoard().isCrossedVerticalLines(new Position(i, j)));
                                updatePointTableValues(Types.VERTICALLINES, k);//update the point table in gui
                            }
                        }
                    }
                }

            }

        }
        this.gui.passTotalScore(getTotalScore());
    }

    /**
     * checks if the given position has the same x coordinate with y coordinate
     * of the horizontal lines positions in the json file(used for loading the
     * game)
     *
     * @param pos
     * @return
     */
    private boolean checksHorizontalPointInLoad(Position pos) {

        for (int i = 0; i < players[0].getBoard().getLevelVals().getHorizontalLines().length; i++) {
            if (pos.getY() == players[0].getBoard().getLevelVals().getHorizontalLines()[i].getPositions()[0].getY()
                    && pos.getX() == players[0].getBoard().getLevelVals().getHorizontalLines()[i].getPositions()[0].getX()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the given position has the same x coordinate with y coordinate
     * of the horizontal lines positions in the json file(used for loading the
     * game)
     *
     * @param pos
     * @return
     */
    private boolean checksVerticalPointInLoad(Position pos) {

        for (int i = 0; i < players[0].getBoard().getLevelVals().getVerticalLines().length; i++) {
            if (pos.getY() == players[0].getBoard().getLevelVals().getVerticalLines()[i].getPositions()[1].getY()
                    && pos.getX() == players[0].getBoard().getLevelVals().getVerticalLines()[i].getPositions()[1].getX()) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets the corresponding flag index according to given flag value
     *
     * @param flagVal
     * @return
     */
    private int getFalgFromLoad(int flagVal) {
        int temp = 0;
        switch (flagVal) {
            case 10:
                temp = 1;
                break;
            case 6:
                temp = 2;
                break;
            case 3:
                temp = 3;
                break;
            case 1:
                temp = 4;
                break;
            default:
                temp = 0;
                break;
        }
        return temp;
    }

    /**
     * end this game when this method is called, the parameter is an array with
     * two values that the first value is the id of the winner and the second
     * one is the score of the winner.
     *
     * @param winnerInfo
     */
    private void endGame(List<Integer> winnerInfo) {
        this.gui.gameWon(winnerInfo);
        logger.closeLog();
    }

}
