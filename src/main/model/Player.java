package model;

import java.awt.*;

// the player use controls
public class Player {
    private Direction playerDir;
    private Position body;
    public static final int SIZE_X = 45;
    public static final int SIZE_Y = 45;

    public Player() {
        this.body = new Position(400, 300);
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
    // effects: sets the position x of player
    public void setPositionX(int positionX) {
        this.body = new Position(positionX, body.getIntY());
    }

    // modifies: this
    // effects: sets the position y of player
    public void setPositionY(int positionY) {
        this.body = new Position(body.getIntX(), positionY);
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
        //return body.equals(pos);
        int size = 40;
        Rectangle invaderBoundingRect = new Rectangle(getPlayerPos().getIntX() - SIZE_X / 2,
                getPlayerPos().getIntY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(pos.getIntX() - size / 2,
                pos.getIntY() - size / 2, size, size);
        return invaderBoundingRect.intersects(missileBoundingRect);
    }
}
