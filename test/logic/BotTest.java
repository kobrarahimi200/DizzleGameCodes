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
public class BotTest {

    FakeGUI gui = new FakeGUI();

    /**
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testJewelPosition() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(0, 0));//bomb
        neighbors.add(new Position(1, 1));//jewel -1
        neighbors.add(new Position(7, 4));//jewel -3
        neighbors.add(new Position(7, 2));//jewel -2

        Position pos = bot.ai(neighbors, 2).getPos();

        assertEquals(new Position(7, 4).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(7, 4).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testPuzzlePositionLevel3() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 1);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(0, 0));//bomb
        neighbors.add(new Position(4, 5));//puzzle -15
        neighbors.add(new Position(7, 2));//jewel -2
        neighbors.add(new Position(7, 4));//jewel -3
        Position pos = bot.ai(neighbors, 2).getPos();

        assertEquals(new Position(4, 5).getX(), pos.getX());
        assertEquals(new Position(4, 5).getY(), pos.getY());
    }

    @Test
    public void testPuzzlePositionLevel3_lastRow() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(7, 1));//bomb
        neighbors.add(new Position(8, 2));//puzzle -15
        neighbors.add(new Position(8, 3));//jewel -2
        neighbors.add(new Position(7, 2));//jewel -3
        neighbors.add(new Position(0, 6));//rocket
        Position pos = bot.ai(neighbors, 2).getPos();
        assertEquals(new Position(8, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(8, 2).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testPuzzlePositionLevel2_lastRow() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(7, 1));//bomb
        neighbors.add(new Position(8, 2));//puzzle -15
        neighbors.add(new Position(8, 3));//jewel -2
        neighbors.add(new Position(7, 2));//jewel -3
        neighbors.add(new Position(0, 6));//rocket
        Position pos = bot.ai(neighbors, 2).getPos();
        assertEquals(new Position(8, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(8, 2).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testAI_isHorizontalLine_oneLine() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();

        neighbors.add(new Position(0, 3));//normal
        neighbors.add(new Position(3, 3));//normal
        neighbors.add(new Position(2, 2));//Horizotal 
        neighbors.add(new Position(4, 3));//normal

        bot.getBoard().addToFilledPos(new Position(0, 2));
        bot.getBoard().addToFilledPos(new Position(1, 2));
        bot.getBoard().addToCrossedList(new Position(3, 2));
        bot.getBoard().addToCrossedList(new Position(4, 2));

        bot.ai(neighbors, 2);

        assertEquals(new Position(2, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(2, 2).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testAI_isVerticalLine_oneLine() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(1);
        Field f = new Field(l, 1);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();

        neighbors.add(new Position(2, 1));//vertical . this position fill one column
        neighbors.add(new Position(1, 2));//normal
        neighbors.add(new Position(3, 0));//noraml 

        bot.getBoard().addToFilledPos(new Position(0, 2));
        bot.getBoard().addToFilledPos(new Position(1, 2));
        bot.getBoard().addToCrossedList(new Position(3, 2));
        bot.getBoard().addToCrossedList(new Position(4, 2));
        bot.getBoard().addToCrossedList(new Position(5, 2));
        bot.getBoard().addToCrossedList(new Position(6, 2));
        bot.ai(neighbors, 2);

        assertEquals(new Position(1, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(1, 2).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testAI_isInLine_twoLines_H_H_5point() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();

        neighbors.add(new Position(2, 2));//vertical
        neighbors.add(new Position(0, 3));//normal
        neighbors.add(new Position(2, 4));//noraml 

        bot.getBoard().addToFilledPos(new Position(0, 2));
        bot.getBoard().addToFilledPos(new Position(1, 2));
        bot.getBoard().addToFilledPos(new Position(0, 4));
        bot.getBoard().addToFilledPos(new Position(3, 1));
        bot.getBoard().addToFilledPos(new Position(3, 2));
        bot.getBoard().addToFilledPos(new Position(4, 2));

        bot.getBoard().addToCrossedList(new Position(1, 4));
        bot.getBoard().addToCrossedList(new Position(3, 4));
        bot.getBoard().addToCrossedList(new Position(4, 4));

        bot.ai(neighbors, 2);
        assertEquals(new Position(2, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(2, 2).getY(), bot.getChoose().getPos().getY());
    }

    @Test
    public void testAI_isInLine_twoLines_V_V_5point() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(1);
        Field f = new Field(l, 2);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();

        neighbors.add(new Position(2, 5));
        neighbors.add(new Position(5, 0));//fill one colunm with 10 points
        neighbors.add(new Position(1, 3));//fill one comumn with 5 points

        bot.getBoard().addToFilledPos(new Position(2, 0));
        bot.getBoard().addToFilledPos(new Position(2, 1));
        bot.getBoard().addToFilledPos(new Position(2, 2));
        bot.getBoard().addToFilledPos(new Position(2, 4));
        bot.getBoard().addToFilledPos(new Position(2, 3));
        bot.getBoard().addToFilledPos(new Position(2, 6));
        bot.getBoard().addToFilledPos(new Position(5, 6));

        bot.getBoard().addToCrossedList(new Position(5, 3));
        bot.getBoard().addToCrossedList(new Position(5, 1));
        bot.getBoard().addToCrossedList(new Position(5, 2));
        bot.getBoard().addToCrossedList(new Position(5, 4));
        bot.getBoard().addToCrossedList(new Position(5, 5));
        bot.ai(neighbors, 6);
        assertEquals(new Position(5, 0).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(5, 0).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * test two columns with different points one with 5 point and the another
     * one is 10 point then the bot chooses a position which fill the column
     * with 10 points
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testAI_isInLine_twoLines_V_V() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(1);
        Field f = new Field(l, 1);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();

        neighbors.add(new Position(2, 5));//fill one comlumn with 5 points
        neighbors.add(new Position(5, 0));//fill one column with 10 points
        neighbors.add(new Position(2, 4));//noraml 

        bot.getBoard().addToFilledPos(new Position(2, 0));
        bot.getBoard().addToFilledPos(new Position(2, 1));
        bot.getBoard().addToFilledPos(new Position(2, 4));
        bot.getBoard().addToFilledPos(new Position(2, 3));
        bot.getBoard().addToFilledPos(new Position(2, 5));
        bot.getBoard().addToFilledPos(new Position(5, 6));

        bot.getBoard().addToCrossedList(new Position(5, 1));
        bot.getBoard().addToCrossedList(new Position(5, 2));
        bot.getBoard().addToCrossedList(new Position(5, 3));
        bot.getBoard().addToCrossedList(new Position(5, 4));
        bot.getBoard().addToCrossedList(new Position(5, 5));
        bot.ai(neighbors, 2);
        assertEquals(new Position(5, 0).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(5, 0).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * compare the bomb bomb cell and jewel red cell and chooses the jewel red
     * because the jewel red has the highest point among others
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testChooseaI_Jewelred() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(1);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(2, 1));//bomb
        neighbors.add(new Position(2, 2));//jewel red
        neighbors.add(new Position(1, 1));//normal cell
        neighbors.add(new Position(2, 4));//normal cell without point
        neighbors.add(new Position(6, 2));//puzzle
        bot.ai(neighbors, 2);
        assertEquals(new Position(2, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(2, 2).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * bot chooses the flag between jewel yellow and flag.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testChooseAI_Flag() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(6, 0));//jewel yellow
        neighbors.add(new Position(7, 0));//jewel jellow
        neighbors.add(new Position(8, 0));//flag cell
        neighbors.add(new Position(3, 3));//normal cell without point
        bot.ai(neighbors, 10);
        assertEquals(new Position(8, 0).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(8, 0).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * bot select the puzzle because has the lowest priority among the others
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testChooseAI_Puzzle() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(6, 0));//jewel yellow
        neighbors.add(new Position(7, 0));//jewel jellow
        neighbors.add(new Position(8, 0));//flag cell
        neighbors.add(new Position(3, 3));//normal cell without point
        neighbors.add(new Position(8, 2));//puzzle
        bot.ai(neighbors, 10);
        assertEquals(new Position(8, 2).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(8, 2).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * bot select the jewel yellow because has the lowest priority among the
     * others
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testChooseAI_JewelYellow() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(2);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(7, 1));//key hole blue
        //neighbors.add(new Position(7, 0));//jewel red
        neighbors.add(new Position(8, 1));//bomb
        neighbors.add(new Position(3, 2));//normal cell without point
        neighbors.add(new Position(6, 1));//jewel yellow

        bot.ai(neighbors, 10);
        assertEquals(new Position(6, 1).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(6, 1).getY(), bot.getChoose().getPos().getY());
    }

    /**
     * bot select the jewel yellow because has the lowest priority among the
     * others
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testChooseAI_Rocket() throws FileNotFoundException {
        IO io = new IO();
        LevelMaker l = io.getJsonValues(3);
        Field f = new Field(l, 3);
        Player bot = new Bot(1, playerType.BOT, f);
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(new Position(0, 0));//bomb
        neighbors.add(new Position(0, 6));//rocket
        neighbors.add(new Position(4, 6));//key
        neighbors.add(new Position(3, 2));//normal cell without point
        neighbors.add(new Position(6, 1));//jewel yellow

        bot.ai(neighbors, 10);
        assertEquals(new Position(0, 6).getX(), bot.getChoose().getPos().getX());
        assertEquals(new Position(0, 6).getY(), bot.getChoose().getPos().getY());
    }
}
