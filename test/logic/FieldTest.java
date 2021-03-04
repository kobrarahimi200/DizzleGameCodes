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
public class FieldTest {

    FakeGUI gui = new FakeGUI();

    @Test
    public void testInitBoard() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);

        Field field = new Field(level, 1);
        assertEquals(9, field.getBoard().length);
        assertEquals(7, field.getBoard()[0].length);

    }

    @Test
    public void testGetBoardValue_addToCrossed() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.addToCrossedList(new Position(4, 5));
        field.addToCrossedList(new Position(5, 3));
        field.addToCrossedList(new Position(5, 4));
        assertTrue(field.getBoard()[4][5].isCrossed());
        assertTrue(field.getBoard()[5][4].isCrossed());
    }

    @Test
    public void testGetBoardValue_addToFilled() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        assertFalse(field.getBoard()[3][2].isFilled());
        assertFalse(field.getBoard()[3][5].isFilled());
        field.addToFilledPos(new Position(3, 2));
        field.addToFilledPos(new Position(3, 5));
        assertTrue(field.getBoard()[3][2].isFilled());
        assertTrue(field.getBoard()[3][5].isFilled());
    }

    @Test
    public void testGetBoardValue_getCrosseneighbors() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);

        for (int i = 0; i < field.getAddCrossedNeighbors().size(); i++) {
            field.getNeighbors().add(i, field.getAddCrossedNeighbors().get(i));
        }
        assertEquals(6, field.getAddCrossedNeighbors().size());
        assertTrue(field.isContains(new Position(3, 4), field.getNeighbors()));
        assertTrue(field.isContains(new Position(4, 5), field.getNeighbors()));
        assertTrue(field.isContains(new Position(4, 2), field.getNeighbors()));
        assertTrue(field.isContains(new Position(5, 3), field.getNeighbors()));
        assertTrue(field.getBoard()[4][3].isCrossed());// is crossed
        assertTrue(field.getBoard()[4][4].isCrossed());// is crossed
        assertFalse(field.isContains(new Position(4, 3), field.getNeighbors()));
        assertFalse(field.isContains(new Position(4, 4), field.getNeighbors()));

    }

    @Test
    public void testGetBoardValue_getNeighbors() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();

        assertTrue(field.getBoard()[4][3].isCrossed());// is crossed
        assertTrue(field.getBoard()[4][4].isCrossed());// is crossed
        field.addToFilledPos(new Position(3, 4));
        field.addToFilledPos(new Position(3, 5));
        field.addToFilledPos(new Position(2, 5));
    }

    @Test
    public void testgetNeighborsCrossedPos() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();

        assertTrue(field.getBoard()[4][3].isCrossed());// is crossed
        assertTrue(field.getBoard()[4][4].isCrossed());// is crossed
        field.addToCrossedList(new Position(0, 4));
        field.addToCrossedList(new Position(1, 4));
        field.addToCrossedList(new Position(1, 5));
        field.addToCrossedList(new Position(1, 6));
        field.addToCrossedList(new Position(2, 5));
        field.addToCrossedList(new Position(3, 5));
        field.addToCrossedList(new Position(4, 5));
        field.addToCrossedList(new Position(5, 5));
        field.addToCrossedList(new Position(4, 6));
        field.addToCrossedList(new Position(5, 6));

        field.addToFilledPos(new Position(0, 6));
        field.addToFilledPos(new Position(2, 6));
        field.addToFilledPos(new Position(3, 6));
        assertTrue(field.getFilledNeighbors().isEmpty());
    }

    @Test
    public void testgetNeighborsCrossedPos_oneNeighbor() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();
        assertTrue(field.getBoard()[4][3].isCrossed());// is crossed
        assertTrue(field.getBoard()[4][4].isCrossed());// is crossed
        field.addToCrossedList(new Position(0, 4));
        field.addToCrossedList(new Position(1, 4));
        field.addToCrossedList(new Position(1, 5));
        field.addToCrossedList(new Position(1, 6));
        field.addToCrossedList(new Position(2, 5));
        field.addToCrossedList(new Position(3, 5));
        field.addToCrossedList(new Position(4, 5));
        field.addToCrossedList(new Position(5, 5));
        field.addToCrossedList(new Position(4, 6));
        field.addToCrossedList(new Position(5, 6));
        field.addToFilledPos(new Position(0, 6));
        field.addToFilledPos(new Position(2, 6));
        assertFalse(field.getFilledNeighbors().isEmpty());
        assertEquals(3, field.getFilledNeighbors().get(0).getX());
        assertEquals(6, field.getFilledNeighbors().get(0).getY());
    }

    @Test
    public void testcheckIfVertical() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();
        assertTrue(field.getBoard()[4][3].isCrossed());// is crossed
        assertTrue(field.getBoard()[4][4].isCrossed());// is crossed
        field.addToCrossedList(new Position(0, 4));
        field.addToCrossedList(new Position(1, 4));
        field.addToCrossedList(new Position(1, 5));
        field.addToCrossedList(new Position(1, 6));
        field.addToCrossedList(new Position(2, 5));
        field.addToCrossedList(new Position(3, 5));
        field.addToCrossedList(new Position(4, 5));
        field.addToCrossedList(new Position(5, 5));
        field.addToCrossedList(new Position(4, 6));
        field.addToCrossedList(new Position(5, 6));
        field.addToFilledPos(new Position(0, 6));
        field.addToFilledPos(new Position(2, 6));
        assertFalse(field.getFilledNeighbors().isEmpty());
        assertEquals(3, field.getFilledNeighbors().get(0).getX());
        assertEquals(6, field.getFilledNeighbors().get(0).getY());
    }

    @Test
    public void testCheckHorizontalFill() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(3);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();

        field.addToFilledPos(new Position(0, 2));
        field.addToFilledPos(new Position(1, 2));
        field.addToFilledPos(new Position(3, 2));
        field.addToFilledPos(new Position(4, 2));
        assertEquals(2, field.isHorizontalLines(new Position(2, 2)).getPos().getX());

    }

    @Test
    public void testCheck_TwoHorizontalFill() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker level = io.getJsonValues(1);
        Field field = new Field(level, 1);
        field.getAddCrossedNeighbors();
        field.addToFilledPos(new Position(2, 0));
        field.addToFilledPos(new Position(2, 1));
        field.addToFilledPos(new Position(2, 2));
        field.addToFilledPos(new Position(2, 4));
        field.addToFilledPos(new Position(2, 3));
        field.addToFilledPos(new Position(2, 6));
        field.addToFilledPos(new Position(5, 6));

        field.addToCrossedList(new Position(5, 3));
        field.addToCrossedList(new Position(5, 1));
        field.addToCrossedList(new Position(5, 2));
        field.addToCrossedList(new Position(5, 4));
        field.addToCrossedList(new Position(5, 5));

        assertEquals(5, field.isVerticalLines(new Position(5, 0)).getPos().getX());

    }
}
