package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestBullet {

    private Bullet bull;
    private static final int xInt = 10;
    private static final int yInt = 20;
    private Position pos = new Position(xInt, yInt);

    @BeforeEach
    public void runBefore() {
        bull = new Bullet(pos, Direction.RIGHT);
    }

    @Test
    public void testGetBulletX() {
        assertEquals(xInt, bull.getBulletX());
    }

    @Test
    public void testGetBulletY() {
        assertEquals(yInt, bull.getBulletY());
    }

    @Test
    public void testGetPos() {
        assertEquals(pos, bull.getPos());
    }

    @Test
    public void testHasCollided() {
        assertTrue(bull.hasCollided(pos));
        assertFalse(bull.hasCollided(new Position(2, 0)));
    }

    @Test
    public void testMove() {
        assertEquals(xInt, bull.getBulletX());
        bull.move();
        assertEquals(14, bull.getBulletX());
        bull = new Bullet(pos, Direction.LEFT);
        bull.move();
        assertEquals(6, bull.getBulletX());
        bull = new Bullet(pos, Direction.UP);
        bull.move();
        assertEquals(16, bull.getBulletY());
        bull = new Bullet(pos, Direction.DOWN);
        bull.move();
        assertEquals(24, bull.getBulletY());

    }

}
