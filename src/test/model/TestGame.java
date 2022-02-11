package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    private Game game;
    private static final int xInt = 100;
    private static final int yInt = 200;

    @BeforeEach
    public void runBefore() {
        game = new Game(xInt,yInt);
    }

    @Test
    public void testInIt() {
        Player player = game.getPlayer();
        assertEquals((new Position(1,1)),player.getPlayerPos());
        List<Bullet> bullets = game.getBullets();
        assertEquals(0, bullets.size());
        List<Reload> reloads = game.getReloads();
        assertEquals(0, reloads.size());
    }

    @Test
    public void testTick() {
        Player player = game.getPlayer();
        assertEquals((new Position(1,1)),player.getPlayerPos());
        game.tick();
        /// ELPELPELEPLPE
    }

    @Test
    public void testSpawnNewTarget() {
        game.spawnNewTarget();
        assertEquals(1,game.getTarget().size());
        game.spawnNewTarget();
        assertEquals(2,game.getTarget().size());
    }

    @Test
    public void testSpawnReload() {
        game.spawnReload();
        assertEquals(1, game.getReloads().size());
        game.spawnReload();
        assertEquals(2, game.getReloads().size());
    }
 ///// ALSO DIDNT DO THIS ONE
    @Test
    public void testMoveReload() {

        game.spawnReload();
        game.spawnReload();
        List<Reload> reloads = game.getReloads();

    }

    @Test
    public void testShoot() {
        assertEquals(5, game.getNumBullets());
        game.shoot();
        assertEquals(4, game.getNumBullets());
        game.shoot();
        assertEquals(3, game.getNumBullets());
        game.shoot();
        assertEquals(2, game.getNumBullets());
        game.shoot();
        assertEquals(2, game.getNumBullets());
    }

    @Test
    public void TestIsOutOfBounds() {
        Position pos = new Position(1000, 1000);
        assertTrue(game.isOutOfBounds(pos));
        pos = new Position(10, 10);
        assertFalse(game.isOutOfBounds(pos));
        pos = new Position(100, 100);
        assertFalse(game.isOutOfBounds(pos));
        pos = new Position(0, 0);
        assertFalse(game.isOutOfBounds(pos));
    }

    @Test
    public void TestTsValidPosition() {

        Position pos = new Position(1000, 1000);
        assertFalse(game.isValidPosition(pos));

        pos = new Position(100, 100);
        assertTrue(game.isValidPosition(pos));

        pos = new Position(1, 1); // player at this pos
        assertFalse(game.isValidPosition(pos));

        game.spawnNewTarget();
        pos = game.getTarget().iterator().next();
        assertFalse(game.isValidPosition(pos));

    }
}
