package persistence;

import model.Game;
import model.Player;
import model.Position;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest  {

    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game(100, 100);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Game game = new Game(100, 100);
            game.tick();
            Player player = game.getPlayer();
            assertEquals((new Position(400,300)),player.getPlayerPos());
            game.tick();
            game.shoot();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();

            assertEquals(4, game.getNumBullets());
            assertEquals(0, game.getScore());
            assertEquals(1, game.getNumPower());
            assertEquals(400 ,game.getPlayer().getPlayerPos().getIntX());
            assertEquals(300 ,game.getPlayer().getPlayerPos().getIntY());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
