/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Reyhan
 */
public class GameTest {

    FakeGUI gui = new FakeGUI();

    @Test
    public void testNumberOfRounds_TwoPlayers() throws FileNotFoundException {
        Game game = new Game(2, 1, gui);
        assertEquals(6, game.getNumOfrounds());
    }

    @Test
    public void testNumberOfRounds_OnePlayer() throws FileNotFoundException {
        Game game = new Game(1, 1, gui);
        assertEquals(10, game.getNumOfrounds());
    }

    @Test
    public void testNumberOfRounds_ThreePlayers() throws FileNotFoundException {
        Game game = new Game(3, 1, gui);
        assertEquals(4, game.getNumOfrounds());
    }

    @Test
    public void testNumberOfDices_TwoPlayers() throws FileNotFoundException {
        Game game = new Game(2, 1, gui);
        assertEquals(7, game.getNumOfDices());
    }

    @Test
    public void testNumberOfDices_ThreePlayers() throws FileNotFoundException {
        Game game = new Game(3, 1, gui);
        assertEquals(10, game.getNumOfDices());

    }

    @Test
    public void testNumberOfDices4Players() throws FileNotFoundException {
        Game game = new Game(4, 1, gui);
        assertEquals(13, game.getNumOfDices());
    }

    @Test
    public void testNumberOfRounds$players() throws FileNotFoundException {
        Game game = new Game(4, 1, gui);
        assertEquals(3, game.getNumOfrounds());
    }

    @Test
    public void testOccupiedByDiceLaied() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        dices.add(1);
        dices.add(3);
        dices.add(5);
        dices.add(6);
        Game game = new Game(4, 1, dices, gui);

        game.addToNeihbors(game.getPlayers()[game.getCurrPlayer()].getBoard().getAddCrossedNeighbors());
        game.setOnBoard(new Position(3, 3));
        assertEquals(10, dices.size());
    }

    @Test
    public void testOccupiedByDiceNotLaied() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        dices.add(1);
        dices.add(3);
        dices.add(5);
        dices.add(6);
        Game game = new Game(4, 1, dices, gui);
        assertEquals(0, game.getCurrPlayer());

        game.addToNeihbors(game.getPlayers()[game.getCurrPlayer()].getBoard().getAddCrossedNeighbors());
        game.setOnBoard(new Position(7, 3));
        assertEquals(0, game.getCurrPlayer());
        assertEquals(4, dices.size());
    }

    @Test
    public void testLayDiceInBoard() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        dices.add(3);
        dices.add(5);
        dices.add(6);
        Game game = new Game(2, 1, dices, gui);
        assertEquals(0, game.getCurrPlayer());
        game.addToNeihbors(game.getPlayers()[game.getCurrPlayer()].getBoard().getAddCrossedNeighbors());
        //there are three dice value 3,5,6 are in the dice list
        game.setOnBoard(new Position(3, 3));//the diice with value 6 laid in the board
        assertEquals(0, game.getCurrPlayer());
        assertEquals(1, game.getDices().size());//only dice with value 3 exist
    }


    @Test
    public void testPointVertCalLinesFill() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 1, dices, gui);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][0].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][1].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][5].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][6].setFilled(true);
        game.moveToCrossedList();
        assertEquals(10, game.getPlayers()[0].getBoard().getPointsList().get(Types.VERTICALLINES).intValue());
    }

    @Test
    public void testPointVertCalLinesFill_RedJewedl() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 1, dices, gui);
        assertEquals(0, game.getCurrPlayer());
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][0].setCrossed(true);//in gui we see vertical
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][1].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][2].setFilled(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][5].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][6].setCrossed(true);
        game.moveToCrossedList();

        assertEquals(3, game.getPlayers()[0].getBoard().getPointsList().get(Types.JewelRed).intValue());
        assertEquals(5, game.getPlayers()[0].getBoard().getPointsList().get(Types.VERTICALLINES).intValue());
    }

    @Test
    public void testPointHorizontalFill_Bomb() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 1, dices, gui);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[5][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[6][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[7][2].setFilled(true);
        game.moveToCrossedList();

        assertEquals(0, game.getPlayers()[0].getBoard().getPointsList().get(Types.BOMBS).intValue());
        assertEquals(0, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }

    @Test
    public void testPointHorizontalFill_REDJewel() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 1, dices, gui);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][3].setFilled(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[5][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[6][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[7][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[8][3].setCrossed(true);
        game.moveToCrossedList();

        assertEquals(3, game.getPlayers()[0].getBoard().getPointsList().get(Types.JewelRed).intValue());
        assertEquals(10, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }

    @Test
    public void testPointHorizontalFill_Level2() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 2, dices, gui);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][6].setFilled(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[5][6].setCrossed(true);
        game.moveToCrossedList();
        assertEquals(0, game.getPlayers()[0].getBoard().getPointsList().get(Types.PuzzleBlue).intValue());
        assertEquals(5, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }

    @Test
    public void testFillHorizontalLine_FillPuzleBlue_Level2() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 2, dices, gui);

        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[8][0].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[5][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][6].setFilled(true);
        game.moveToCrossedList();

        assertEquals(10, game.getPlayers()[0].getBoard().getPointsList().get(Types.PuzzleBlue).intValue());
        assertEquals(5, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }

    @Test
    public void testPointHorizontalFillPuzleBlueNotFilled_Level2() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 2, dices, gui);

        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[5][6].setFilled(true);
        game.moveToCrossedList();
        assertEquals(0, game.getPlayers()[0].getBoard().getPointsList().get(Types.PuzzleBlue).intValue());
        assertEquals(5, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }

    @Test
    public void testPointHorizontalFillPuzzleGReen_Level2() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 2, dices, gui);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][0].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][6].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[8][6].setFilled(true);
        game.moveToCrossedList();
        assertEquals(10, game.getPlayers()[0].getBoard().getPointsList().get(Types.PuzzleGreen).intValue());
    }

    @Test
    public void testPointHorizontalFillTwoLines_Level3() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        Game game = new Game(2, 3, dices, gui);

        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][2].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][2].setFilled(true);
        game.moveToCrossedList();
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][3].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][3].setFilled(true);
        game.moveToCrossedList();
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[0][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[1][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[2][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][4].setCrossed(true);
        game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[4][4].setFilled(true);

        game.moveToCrossedList();
        assertEquals(15, game.getPlayers()[0].getBoard().getPointsList().get(Types.HORIZONTALLINES).intValue());
    }
    @Test
    public void testOccupiedCell() throws FileNotFoundException {
        List<Integer> dices = new LinkedList<>();
        dices.add(3);
        dices.add(5);
        dices.add(6);
        Game game = new Game(2, 1, dices, gui);
        assertEquals(0, game.getCurrPlayer());
        game.addToNeihbors(game.getPlayers()[game.getCurrPlayer()].getBoard().getAddCrossedNeighbors());
        assertFalse(game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][3].isFilled());
        //there are three dice value 3,5,6 are in the dice list
        game.setOnBoard(new Position(3, 3));//the diice with value 6 laid in the board
        assertTrue(game.getPlayers()[game.getCurrPlayer()].getBoard().getBoard()[3][3].isFilled());
    }
}
