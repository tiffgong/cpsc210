package model;

public class Player {
    private Direction playerDir;
    private Position body;

    public Player() {
        this.body = new Position(1, 1);
        this.playerDir = Direction.RIGHT;
    }

    // moves player
    // modifies: this
    // effects: progresses player in current direction
    public void move(int speed) {
        this.body = playerDir.move(this.body, speed);
    }

    // modifies: this
    // effects: sets the direction of player to given direction
    public void setDirection(Direction direction) {
        this.playerDir = direction;
    }

    // modifies: this
    // effects: returns Position of player
    public Position getPlayerPos() {
        return body;
    }

    // modifies: this
    // effects: returns direction of player
    public Direction getDir() {
        return playerDir;
    }

    // Checks if bullet has collided with given position
    // modifies: this
    // effects: returns true if bullet is at a given position, false otherwise
    public boolean hasCollided(Position pos) {
        return body.equals(pos);
    }
}
