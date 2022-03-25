package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    private Player player;
    private static final int xInt = 400;
    private static final int yInt = 300;
    private Position pos = new Position(xInt, yInt);

    @BeforeEach
    public void runBefore() {
        player = new Player();
    }

    @Test
    public void testMove() {
        player.move(1);
        pos = new Position(401,300);
        assertEquals(pos, player.getPlayerPos());
    }

    @Test
    public void testDirection() {
        player.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, player.getDir());
        player.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, player.getDir());
        player.setDirection(Direction.UP);
        assertEquals(Direction.UP, player.getDir());
        player.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, player.getDir());
    }

    @Test
    public void testGetPlayerPos() {
        assertEquals(pos, player.getPlayerPos());
    }

    @Test
    public void testSetPositionX() {
        assertEquals(400, player.getPlayerPos().getIntX());
        player.setPositionX(20);
        assertEquals(20, player.getPlayerPos().getIntX());
    }

    @Test
    public void testSetPositionY() {
        assertEquals(300, player.getPlayerPos().getIntY());
        player.setPositionY(10);
        assertEquals(10, player.getPlayerPos().getIntY());
    }


    @Test
    public void testHasCollided() {
        assertTrue(player.hasCollided(pos));
        pos = new Position(10,20);
        assertFalse(player.hasCollided(pos));
    }
}
