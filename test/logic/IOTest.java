/*
 * Dizzle game developed by Kobra Rahimi ite102770 for advanced programming course for Summer Semester 2020
 */
package logic;

import java.io.FileNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Reyhan
 */
public class IOTest {

    FakeGUI gui = new FakeGUI();

    @Test
    public void testFiledLength() throws FileNotFoundException {
        IO io = new IO(gui);
        LevelMaker levels = io.getJsonValues(1);
        assertEquals(9, levels.getField()[0].length);
        assertEquals(7, levels.getField().length);
        assertEquals(3, levels.getJewels()[0].getPoints());
        assertNull(levels.getFlag());
    }

}
