package persistence;

import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Game game = reader.read();
            assertEquals(1, game.getNumBullets());
            assertEquals(10, game.getScore());
            assertEquals(3, game.getNumPower());
            assertEquals(7, game.getMaxX());
            assertEquals(22, game.getMaxY());
            assertEquals(11 ,game.getPlayer().getPlayerPos().getIntX());
            assertEquals(4 ,game.getPlayer().getPlayerPos().getIntY());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
