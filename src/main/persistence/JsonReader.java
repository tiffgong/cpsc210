package persistence;

import model.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseWorkRoom(JSONObject jsonObject) {

        Game game = new Game(jsonObject.getInt("max x"), jsonObject.getInt("max y"));

        game.setScore(jsonObject.getInt("score"));
        game.setBullets(jsonObject.getInt("number bullets"));
        game.setNumPower(jsonObject.getInt("number reloads"));

        game.setPlayerX(jsonObject.getInt("player x"));
        game.setPlayerY(jsonObject.getInt("player y"));

        addTargets(game, jsonObject);
        return game;
    }

    private void addTargets(Game wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("targets");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addTarget(wr, nextThingy);
        }
    }

    private void addTarget(Game wr, JSONObject jsonObject) {
        wr.setTarget(jsonObject.getInt("intX"), jsonObject.getInt("intY"));
    }


}