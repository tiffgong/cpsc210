package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReload {
    private Reload reload;
    private static final int xInt = 10;
    private static final int yInt = 20;
    private Position pos = new Position(xInt, yInt);


    @BeforeEach
    public void runBefore() {
        reload = new Reload(xInt, yInt);
    }

    @Test
    public void testGetIntX() {
        assertEquals(xInt, reload.getIntX());
    }

    @Test
    public void testGetIntY() {
        assertEquals(yInt, reload.getIntY());
    }

    @Test
    public void testGetPos() {
        assertEquals(pos, reload.getPos());
    }

    @Test
    public void testMove() {
        reload.move();
        assertEquals(yInt + 1, reload.getIntY());
    }

}
