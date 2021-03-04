package logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is responsible for making the playing field.A two dimensional
 * array of cell object is created and the dices are placed there with their
 * values and types.
 *
 * @author Reyhan
 */
public class Field {

    private List<Position> crossedList;//crossed positions list
    private List<Position> filledPos;//occupied positions list
    private Position curLaiedDice;// current laid dice
    private List<Position> neighbors;//all avalaible neighbors 
    HashMap<Types, Integer> points;//the list of all cells which has points
    // which first Type is the tyoe if symbol and Integer is the point of the that type
    private Cell[][] board;//main playing board
    LevelMaker levelVals;//an object of level maker to get the data from the passed json file of the current level
    private int point; // point of the current position
    private boolean keyHoleBlue = false; //is true if the type of the current position is key hole blue
    private boolean keyHoleYellow = false;//is true if the type of the current position is key hole yellow

    /**
     * default constructor to create the board
     *
     * @param levelValues an object of level maker to pass the data for the
     * current level
     * @param level is the current level
     */
    Field(LevelMaker levelValues, int level) {
        assert (levelValues != null);
        this.levelVals = levelValues;
        //this.field = levelValues;
        board = new Cell[levelValues.getField().length][levelValues.getField()[0].length];//initilize the board
        board = rotateBoard();
        initBoard();
        findInitCrossedList();

        this.neighbors = new LinkedList<>();
        crossedList = new LinkedList<>();
        filledPos = new LinkedList<>();
        points = new HashMap<Types, Integer>();
        pointTable(level);
        point = 0;
    }

    /**
     * gets the keyholeBlue value.
     *
     * @return
     */
    public boolean isKeyHoleBlue() {
        return keyHoleBlue;
    }

    /**
     * sets the given value to keyHoleBlue
     *
     * @param keyHoleBlue
     */
    public void setKeyHoleBlue(boolean keyHoleBlue) {
        this.keyHoleBlue = keyHoleBlue;
    }

    /**
     * gets the keyHoleYellow value
     *
     * @return a boolean value
     */
    public boolean isKeyHoleYellow() {
        return keyHoleYellow;
    }

    /**
     * sets the keyHoleYellow value to the given value
     *
     * @param keyHoleYellow
     */
    public void setKeyHoleYellow(boolean keyHoleYellow) {
        this.keyHoleYellow = keyHoleYellow;
    }

    /**
     * this method is rotating the objectBoard based on what user see on GUI
     *
     * @return baord of cells
     */
    private Cell[][] rotateBoard() {
        Cell[][] temp = new Cell[board[0].length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                temp[j][i] = board[i][j];
            }
        }
        return temp;
    }

    /**
     * insert the symbols which contains points. The type and the point of them
     * inserted into the hashMap. This list is different for each level.
     *
     * @param level
     */
    protected void pointTable(int level) {

        points.put(Types.JewelRed, 0);
        points.put(Types.HORIZONTALLINES, 0);
        points.put(Types.VERTICALLINES, 0);
        points.put(Types.BOMBS, 0);
        if (level == 1) {//level one
            points.put(Types.PuzzleBlue, 0);
        } else if (level == 2) {//level two
            points.put(Types.JewelYellow, 0);
            points.put(Types.PuzzleBlue, 0);
            points.put(Types.PuzzleGreen, 0);

        } else if (level == 3) {//level three
            points.put(Types.JewelBlue, 0);
            points.put(Types.JewelYellow, 0);
            points.put(Types.PuzzleBlue, 0);
            points.put(Types.FLAG, 0);
        }

    }

    /**
     * search in the board array and find a cell which his type is planet, is
     * not null and is not occupied , then return his position.
     *
     */
    protected Position crossedPlanetPos() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null && board[i][j].getType() != null
                        && board[i][j].getType().equals(Types.PLANET)) {
                    board[i][j].setCrossed(true);
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    /**
     * There are several "puzzle" special fields on a game sheet. Whoever has
     * crossed out all puzzle-fields of the same color earns the corresponding
     * points denoted on the sheet. In Level 2, there are puzzle pieces in two
     * different colors that only grant points if combined with the correct
     * colors.(from website of the game rule) checks in the board and find all
     * positions of puzzles and checks if these positions are filled or crossed,
     * if occupied both then return true.
     *
     * @param type
     * @return
     */
    protected boolean checkIfAllPuzzlesFilled(Types type) {
        boolean isOccupied = true;
        for (int i = 0; i < board.length && isOccupied; i++) {
            for (int j = 0; j < board[i].length && isOccupied; j++) {
                if (board[i][j] != null && board[i][j].getType() != null
                        && board[i][j].getType().equals(type) && (!board[i][j].isCrossed() && !board[i][j].isFilled())) {
                    isOccupied = false;
                }
            }
        }
        return isOccupied;
    }

    /**
     * this method is like above method to checks if both puzzles are crossed,
     * then return true otherwise return false
     *
     * @param type of the given type to check
     * @return true if both puzzles positions are crossed
     */
    protected boolean checkIfAllPuzzlesCrossedInLoadedGame(Types type) {
        boolean isOccupied = true;
        for (int i = 0; i < board.length && isOccupied; i++) {
            for (int j = 0; j < board[i].length && isOccupied; j++) {
                if (board[i][j] != null && board[i][j].getType() != null
                        && board[i][j].getType().equals(type) && (!board[i][j].isCrossed())) {
                    isOccupied = false;
                }
            }
        }
        return isOccupied;
    }

    /**
     * gets the point.
     *
     * @return
     */
    protected int getPointCell() {
        return this.point;
    }

    /**
     * gets the an object of level maker.
     *
     * @return
     */
    protected LevelMaker getLevelVals() {
        return levelVals;
    }

    /**
     * initialize the board. for every cell checks the type it it has a type
     * then insert the type and also insert his point. it the cell does not have
     * any type then insert noType and zero for the point.
     */
    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(levelVals.getField()[j][i], checkTypesInCell(new Position(i, j)), true, true, point);
            }
        }
    }

    /**
     * gets the playing board
     *
     * @return
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * gets the neighbors list
     *
     * @return
     */
    public List<Position> getNeighbors() {
        return neighbors;
    }

    /**
     * search on the board and gets positions which exploded attribute is true ,
     * add this position to the list. and then return a list of all exploded
     * positions.
     *
     * @return a list of all exploded positions
     */
    protected List<Position> getExplodedPos() {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getExploded()) {//if this position is exploded
                    temp.add(new Position(i, j));
                }
            }
        }
        return temp;
    }

    /**
     * gets a list of all filled positions in the board
     *
     * @return a linked list of filled list positions
     */
    protected List<Position> getFilledList() {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isFilled()) {//if this position is filled
                    temp.add(new Position(i, j));
                }
            }
        }
        return temp;
    }

    /**
     * gets a list of all positions which is crossed out
     *
     * @return a linked list of crossed list positions
     */
    protected List<Position> getCrossedList() {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isCrossed()) {
                    temp.add(new Position(i, j));
                }
            }
        }
        return temp;
    }

    /**
     * checks the board and return false if all cells is not crossed and not
     * filled
     *
     * @return true if all cells are crossed
     */
    protected boolean allCrossedPos() {
        boolean isCrossed = true;
        for (int i = 0; i < board.length && isCrossed; i++) {
            for (int j = 0; j < board[i].length && isCrossed; j++) {
                if (board[i][j] != null && (!board[i][j].isCrossed() && !board[i][j].isFilled())) {
                    isCrossed = false;
                }
            }
        }
        return isCrossed;
    }

    /**
     * go though the board and checks if all cells are not in the crossed list.
     * if all of them are empty without any dices, then return true
     *
     * @return true if the board is empty
     */
    protected boolean isEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < board.length && isEmpty; i++) {
            for (int j = 0; j < board[i].length && isEmpty; j++) {
                //go through the crossed list 
                for (int k = 0; k < crossedList.size() && isEmpty; k++) {
                    if (board[i][j] != null && board[i][j].equals(crossedList.get(k))) {
                        isEmpty = false;
                    }
                }
            }
        }
        return isEmpty;
    }

    /**
     * reverse the column with row of the main board.
     *
     * @return a 2 dimensional reversed main board
     */
    protected Cell[][] reverseBoard() {
        Cell[][] temp = new Cell[board[0].length][board.length];
        //go through the board and reverse each row with column
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                temp[j][i] = board[i][j];

            }
        }
        return temp;
    }

    /**
     * go though the board and checks the cells that are crossed out then gets
     * all neighbors of that position and add them into a list and finally
     * return this list
     *
     * @return a list of all neighbors of the crossed positions
     */
    protected List<Position> getAddCrossedNeighbors() {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isCrossed()) {
                    temp.addAll(checkNeighbor(new Position(i, j).getNeighbours()));
                }
            }
        }
        return temp;
    }

    /**
     * checks all given positions if it is not null and is not crossed and not
     * filled not exploded then add this position to the list. Finally return
     * this list
     *
     * @param pos is given position list to check
     * @return a list of positions
     */
    private List<Position> checkNeighbor(Position[] pos) {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < pos.length; i++) {
            //if this position is valid and have value and is empty
            if (pos[i] != null && isValidPosition(pos[i]) && board[pos[i].getX()][pos[i].getY()].getValue() != null
                    && !board[pos[i].getX()][pos[i].getY()].isCrossed() && !board[pos[i].getX()][pos[i].getY()].isFilled()
                    && !board[pos[i].getX()][pos[i].getY()].getExploded()
                    && !isContains(pos[i], temp)) {
                temp.add(pos[i]);
            }
        }
        return temp;
    }

    /**
     * go through the board and checks if the cell is filled then gets all
     * neighbors of that and add them into a list.
     *
     * @return a list of neighbors of the filled positions
     */
    protected List<Position> getFilledNeighbors() {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isFilled()) {
                    temp.addAll(checkNeighbor(new Position(i, j).getNeighbours()));
                }
            }
        }
        return temp;
    }

    /**
     * go through the given list and gets the neighbors of this list positions
     * and if they are valid add them into another list.
     *
     * @param filledPos is given filled position list
     * @return a list of the neighbors of the given list
     */
    protected List<Position> getFilledPosNeighbors(List<Position> filledPos) {
        List<Position> temp = new LinkedList<>();
        for (int i = 0; i < filledPos.size(); i++) {
            for (int j = 0; j < filledPos.get(i).getNeighbours().length; j++) {
                for (int k = 0; k < temp.size(); k++) {
                    if (!filledPos.get(i).getNeighbours()[j].equals(null) && isValidPosition(filledPos.get(i).getNeighbours()[j])) {
                        temp.add(filledPos.get(i).getNeighbours()[j]);
                    }
                }
            }
        }
        return temp;
    }

    /**
     * gets the filled position list
     *
     * @return
     */
    public HashMap<Types, Integer> getPointsList() {
        return points;
    }

    /**
     * add the given position to the filled position list
     *
     * @param pos
     */
    protected void addToFilledPos(Position pos) {
        board[pos.getX()][pos.getY()].setFilled(true);

    }

    /**
     * set the laid dice position
     *
     * @param pos
     */
    void setLaidDicePos(Position pos) {
        this.curLaiedDice = pos;
    }

    /**
     * returns the current laid position
     *
     * @return
     */
    Position getLaidDice() {
        return this.curLaiedDice;
    }

    /**
     * search in board object and checks if the current value is not null and
     * equals to zero it means this value is crossed, get this crossed position
     * and add it to the startPoint list.
     */
    private void findInitCrossedList() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() != null && board[i][j].getValue() == 0) {
                    board[i][j].setCrossed(true);
                } else {
                    board[i][j].setCrossed(false);
                }
                board[i][j].setFilled(false);
            }
        }
    }

    /**
     * return true if the given position is in the given list
     *
     * @param pos is the given position
     * @param list is the given list to check
     * @return a boolean value to check existence of the given position
     */
    protected boolean isContains(Position pos, List<Position> list) {
        boolean isContain = false;
        for (Position temp : list) {
            if (temp.equals(pos)) {
                isContain = true;
            }
        }
        return isContain;
    }

    /**
     * sets the crossed to the given position
     *
     * @param pos is the given position
     */
    protected void addToCrossedList(Position pos) {
        assert (pos != null);
        board[pos.getX()][pos.getY()].setCrossed(true);
        board[pos.getX()][pos.getY()].setFilled(false);

    }

    /**
     * gets the value of the given position from the field
     *
     * @param pos
     * @return
     */
    protected int getCellValue(Position pos) {
        assert (pos != null && board[pos.getX()][pos.getY()] != null);
        return board[pos.getX()][pos.getY()].getValue();
    }

    /**
     * checks if the given x value is valid in the board
     *
     * @param x
     * @return true if the given value is valid
     */
    private boolean isValidX(int x) {
        return x >= 0 && x < board.length;
    }

    /**
     * checks if the given value is valid in the board
     *
     * @param y
     * @return true if it is valid
     */
    private boolean isValidY(int y) {
        return y >= 0 && y < board[0].length;
    }

    /**
     * return true if the x and y value of the given position is valid
     *
     * @param pos
     * @return
     */
    protected boolean isValidPosition(Position pos) {
        return isValidX(pos.getX()) && isValidY(pos.getY()) && board[pos.getX()][pos.getY()] != null;
    }

    /**
     * checks if the given position has type or not.Go through the json file of
     * the current level and read one by one and checks the type of this
     * position.
     *
     * At the end return the type and the point of the given position. if it has
     * no type then return noType and 0 point
     *
     * @param pos is the given position to check
     * @return the type of the position
     */
    private Types checkTypesInCell(Position pos) {
        Types type = null;
        point = 0;
        for (int i = 0; i < levelVals.getJewels().length; i++) {//checks the jewel array
            if (containsPos(levelVals.getJewels()[i].getPositions(), pos)) {
                switch (levelVals.getJewels()[i].getPoints()) {
                    case 3:
                        type = Types.JewelRed;
                        point = levelVals.getJewels()[i].getPoints();
                        break;
                    case 2:
                        type = Types.JewelYellow;
                        point = levelVals.getJewels()[i].getPoints();
                        break;
                    default:
                        type = Types.JewelBlue;
                        point = levelVals.getJewels()[i].getPoints();
                        break;

                }

            }
        }
        //checks the rocket positions
        if (levelVals.getRocket() != null && levelVals.getRocket().equals(pos)) {//rocket
            type = Types.ROCKET;
            point = 0;
        }
        // chcecks the bomb positions 
        if (containsPos(levelVals.getBombs().getPositions(), pos)) {//bomb
            type = Types.BOMBS;
            point = levelVals.getBombs().getPoints();
        }
        //checks the keys positions 
        for (int i = 0; i < levelVals.getKeys().length; i++) {//key
            if (levelVals.getKeys()[i].getPosition() != null
                    && pos.equals(levelVals.getKeys()[i].getPosition())) {
                if (pos.equals(new Position(0, 4))) {
                    type = Types.KeyBlue;
                    point = 0;
                } else {
                    type = Types.KeyYellow;
                    point = 0;
                }
            }
        }
        //checks the key hole positions
        for (int i = 0; i < levelVals.getKeys().length; i++) {//hole
            if (containsPos(levelVals.getKeys()[i].getHoles(), pos)) {
                if (pos.equals(new Position(7, 1))) {
                    type = Types.KeyHoleBlue;
                    point = 0;
                } else {
                    type = Types.KeyHoleYellow;
                    point = 0;
                }
            }
        }
        //checks tje flag position and the flag point array 
        if (levelVals.getFlag() != null && levelVals.getFlag().getPosition().equals(pos)) {//flag
            type = Types.FLAG;
            point = 10;
        }
        if (levelVals.getPlanet() != null && levelVals.getPlanet().equals(pos)) {//flag
            type = Types.PLANET;
            point = 0;
        }
        // checks the puzzle positions
        for (int i = 0; i < levelVals.getPuzzles().length; i++) {//puzzle
            if (containsPos(levelVals.getPuzzles()[i].getPositions(), pos)) {
                if (pos.equals(new Position(0, 0)) || pos.equals(new Position(8, 6))) {
                    type = Types.PuzzleGreen;
                    point = levelVals.getPuzzles()[i].getPoints();
                } else {
                    type = Types.PuzzleBlue;
                    point = levelVals.getPuzzles()[i].getPoints();
                }
            }
        }
        //if it has not type then return noType
        if (levelVals.getField()[pos.getY()][pos.getX()] != null && type == null) {//flag
            type = Types.noType;
            point = 0;
        }
        return type;
    }

    /**
     * checks if the given array of positions contain the given position, if
     * contains return true.
     *
     * @param positions
     * @param pos
     * @return
     */
    private boolean containsPos(Position[] positions, Position pos) {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null && positions[i].equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * this method checks if the given position complete a row , then gets the
     * point of that and return a choose with this point and type of horizontal
     * otherwise return null(used for bot)
     *
     * @param pos
     * @return
     */
    protected Choose isHorizontalLines(Position pos) {
        boolean isHorizontalLine = false;
        Choose temp = null;
        for (int i = 0; i < levelVals.getHorizontalLines().length && !isHorizontalLine; i++) {
            if (pos.getY() == levelVals.getHorizontalLines()[i].getPositions()[0].getY()) {
                if (fillTheHorizontalLine(pos,
                        levelVals.getHorizontalLines()[i].getPositions()[0].getX(),
                        levelVals.getHorizontalLines()[i].getPositions()[1].getX())) {
                    isHorizontalLine = true;
                    temp = new Choose(pos, levelVals.getHorizontalLines()[i].getPoints(), Types.HORIZONTALLINES);
                }
            }
        }
        return temp;
    }

    /**
     * if the given position complete a column then return a choose object with
     * the point of vertical type(used for bot)
     *
     * @param pos
     * @return a choose object if the type is vertical
     */
    protected Choose isVerticalLines(Position pos) {
        boolean isVerticalLine = false;
        Choose temp = null;
        for (int i = 0; i < levelVals.getVerticalLines().length && !isVerticalLine; i++) {
            if (pos.getX() == levelVals.getVerticalLines()[i].getPositions()[0].getX()) {
                if (fillTheVerticalLine(pos,
                        levelVals.getVerticalLines()[i].getPositions()[0].getY(),
                        levelVals.getVerticalLines()[i].getPositions()[1].getY())) {
                    isVerticalLine = true;
                    temp = new Choose(pos, levelVals.getVerticalLines()[i].getPoints(), Types.VERTICALLINES);
                }
            }
        }
        return temp;
    }

    /**
     * checks if the given position fill the rows or not , the xBegin is the
     * beginning of the row and the xEnd is the end of the row.
     *
     * @param pos is the given position
     * @param xBegin is the starting point of the row
     * @param xEnd is the end point of the row
     * @return true if this position fill the row
     */
    private boolean fillTheHorizontalLine(Position pos, int xBegin, int xEnd) {
        for (int i = xBegin; i < xEnd; i++) {
            Position temp = new Position(i, pos.getY());
            if (!board[temp.getX()][temp.getY()].isCrossed() && !board[temp.getX()][temp.getY()].isFilled() && !temp.equals(pos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the given position fill the column or not , the xBegin is the
     * beginning of the column and the xEnd is the end of the column.
     *
     * @param pos is the given position
     * @param yBegin is the starting point of the column
     * @param yEnd is the end point of the column
     * @return true if this position fill the column
     */
    private boolean fillTheVerticalLine(Position pos, int yBegin, int yEnd) {
        for (int i = yBegin; i < yEnd; i++) {
            Position temp = new Position(pos.getX(), i);
            //if the given position is not crossed and is not filled 
            if (!board[temp.getX()][temp.getY()].isCrossed() && !board[temp.getX()][temp.getY()].isFilled() && !temp.equals(pos)) {
                return false;
            }

        }
        return true;
    }

    /**
     * checks if the given position fills the one line horizontally and all
     * position in this line is crossed out.(each completely crossed out row or
     * column with an arrow provides the displayed points.)
     *
     * @param pos is the given position
     * @param xBegin is the first cell x value
     * @param xEnd is the end of the horizontal line
     * @return true if this position complete crosse out a row
     */
    private boolean fillCrossedHorizontalLine(Position pos, int xBegin, int xEnd) {
        for (int i = xBegin; i <= xEnd; i++) {
            Position temp = new Position(i, pos.getY());
            if (!board[temp.getX()][temp.getY()].isCrossed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the given position fills the one line vertically and all
     * position in this line is crossed out.(each completely crossed out row or
     * column with an arrow provides the displayed points.)
     *
     * @param pos is the given position
     * @param yBegin is the first point of the vertical line
     * @param yEnd s the end point of the vertical line
     * @return true if this position complete crosse out a column
     */
    private boolean fillCrossedVerticalLine(Position pos, int yBegin, int yEnd) {
        for (int i = yBegin; i <= yEnd; i++) {
            Position temp = new Position(pos.getX(), i);

            if (board[temp.getX()][temp.getY()].getValue() != null && !board[temp.getX()][temp.getY()].isCrossed()) {
                return false;
            }

        }
        return true;
    }

    /**
     * returns the point of horizontal line if the given position complete the a
     * crossed row(we need this point for checking the winner)
     *
     * @param pos
     * @return
     */
    protected int isCrossedHorizontalLines(Position pos) {
        boolean isHorizontalLine = false;
        int temp = 0;
        for (int i = 0; i < levelVals.getHorizontalLines().length && !isHorizontalLine; i++) {
            //if the given position exist in the horizontal line
            if (pos.getY() == levelVals.getHorizontalLines()[i].getPositions()[0].getY()
                    && pos.getX() >= levelVals.getHorizontalLines()[i].getPositions()[0].getX()
                    && pos.getX() <= levelVals.getHorizontalLines()[i].getPositions()[1].getX()) {
                //if this position fills the crossed horizontal line
                if (fillCrossedHorizontalLine(pos,
                        levelVals.getHorizontalLines()[i].getPositions()[0].getX(),
                        levelVals.getHorizontalLines()[i].getPositions()[1].getX())) {
                    isHorizontalLine = true;
                }
            }
            //gets the point of the position if it fills a crossed row
            if (isHorizontalLine) {
                temp = levelVals.getHorizontalLines()[i].getPoints();
            }
        }
        return temp;
    }

    /**
     * checks if the given position complete the a crossed column, then gets his
     * point(we need this point for checking the winner)
     *
     * @param pos is the given position to check
     * @return the point of this position if it complete a crossed column
     */
    protected int isCrossedVerticalLines(Position pos) {
        boolean isVerticalLine = false;
        int temp = 0;
        for (int i = 0; i < levelVals.getVerticalLines().length && !isVerticalLine; i++) {
            if (pos.getX() == levelVals.getVerticalLines()[i].getPositions()[0].getX()) {
                //if this position fills the crossed vertical line
                if (fillCrossedVerticalLine(pos,
                        levelVals.getVerticalLines()[i].getPositions()[0].getY(),
                        levelVals.getVerticalLines()[i].getPositions()[1].getY())) {
                    isVerticalLine = true;
                }
            }
            //gets the point of the position if it fills a crossed column
            if (isVerticalLine) {
                temp = levelVals.getVerticalLines()[i].getPoints();
            }

        }
        return temp;
    }

}
