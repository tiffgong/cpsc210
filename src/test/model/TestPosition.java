package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPosition {
    private static final int xInt = 10;
    private static final int yInt = 20;
    Position pos;

    @BeforeEach
    public void runBefore() {
        pos = new Position(xInt, yInt);
    }

    @Test
    public void testEquals() {
        Position test = new Position(xInt,yInt);
        assertTrue(pos.equals(test));
        test = new Position(1,1);
        assertFalse(pos.equals(test));

        assertFalse(pos.equals(null));

        test = new Position(10,1);
        assertFalse(pos.equals(test));
        test = new Position(10,20);
        assertTrue(pos.equals(test));
        test = new Position(1,20);
        assertFalse(pos.equals(test));
    }
}
