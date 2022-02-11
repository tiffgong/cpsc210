package model;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGame {
    private Game game;
    private static final int xInt = 100;
    private static final int yInt = 200;

    @BeforeEach
    private void runBefore() {
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

    }
}
