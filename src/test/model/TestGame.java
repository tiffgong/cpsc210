package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals((new Position(400,300)),player.getPlayerPos());
        List<Bullet> bullets = game.getBullets();
        assertEquals(0, bullets.size());
        List<Reload> reloads = game.getReloads();
        assertEquals(0, reloads.size());
    }

    @Test
    public void testTick() {
        final int NUM_UPDATES = 10;
        Player player = game.getPlayer();
        assertEquals((new Position(400,300)),player.getPlayerPos());
        game.tick();

        game.shoot();
        game.tick();
        assertEquals(4,game.getNumBullets());

        for(int count = 1; count < NUM_UPDATES; count++) {
            game.tick();
        }
        assertEquals(0,game.getTarget().size());
        game.tick();
        assertEquals(0,game.getTarget().size());

        player.setDirection(Direction.LEFT);
        player.move(1);
        game.tick();
        player.move(1);
        game.tick();
        assertEquals(398, player.getPlayerPos().getIntX());
        assertTrue(game.isEnded());

    }

    @Test
    public void testSpawnNewTarget() {
        game.spawnNewTarget();
        assertEquals(1,game.getTarget().size());
        game.spawnNewTarget();
        assertEquals(2,game.getTarget().size());
        game.tick();
    }

    @Test
    public void testSpawnReload() {
        game.spawnReload();
        assertEquals(1, game.getReloads().size());
        game.spawnReload();
        assertEquals(2, game.getReloads().size());
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
        pos = new Position(0, 1000);
        assertTrue(game.isOutOfBounds(pos));
        pos = new Position(10, 10);
        assertFalse(game.isOutOfBounds(pos));
        pos = new Position(100, 100);
        assertFalse(game.isOutOfBounds(pos));
        pos = new Position(0, 0);
        assertFalse(game.isOutOfBounds(pos));
        pos = new Position(0, -100);
        assertTrue(game.isOutOfBounds(pos));
    }

    @Test
    public void TestTsValidPosition() {
        Position pos = new Position(1000, 1000);
        assertFalse(game.isValidPosition(pos));

        pos = new Position(100, 100);
        assertTrue(game.isValidPosition(pos));

        pos = new Position(-2, -10); // player at this pos
        assertFalse(game.isValidPosition(pos));

        game.spawnNewTarget();
        pos = game.getTarget().iterator().next();
        assertFalse(game.isValidPosition(pos));
    }

    @Test
    public void testUseReload() {

        assertEquals(5,game.getNumBullets());
        assertEquals(1,game.getNumPower());
        game.useReload();
        assertEquals(10,game.getNumBullets());
        game.useReload();
        assertEquals(0,game.getNumPower());
        game.useReload();
        assertEquals(0,game.getNumPower());
        assertEquals(10,game.getNumBullets());

    }
    @Test
    public void testGetNumPower() {
        assertEquals(1,game.getNumPower());
    }

    @Test
    public void testGetScore() {
        assertEquals(0,game.getScore());
    }
    @Test
    public void testIsEnded() {
        assertFalse(game.isEnded());
    }


    @Test
    public void testSetBullets() {
        int num = 11;
        assertEquals(5, game.getNumBullets());
        game.setBullets(num);
        assertEquals(num, game.getNumBullets());
    }

    @Test
    public void testSetNumPower() {
        int num = 10;
        assertEquals(1, game.getNumPower());
        game.setNumPower(num);
        assertEquals(num, game.getNumPower());
    }

    @Test
    public void testSetScore(){
        int num = 10;
        assertEquals(0, game.getScore());
        game.setScore(num);
        assertEquals(num, game.getScore());
    }

    @Test
    public void testGetMaxX() {
        assertEquals(100, game.getMaxX());
    }

    @Test
    public void testGetMaxY() {
        assertEquals(200, game.getMaxY());
    }


    @Test
    public void testSetPlayerX() {
        assertEquals(400, game.getPlayer().getPlayerPos().getIntX());
        game.setPlayerX(10);
        assertEquals(10, game.getPlayer().getPlayerPos().getIntX());
    }

    @Test
    public void testSetPlayerY() {
        assertEquals(300, game.getPlayer().getPlayerPos().getIntY());
        game.setPlayerY(10);
        assertEquals(10, game.getPlayer().getPlayerPos().getIntY());

    }

}
