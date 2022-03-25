package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// represents a bullet
public class Bullet {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20;
    public static final int DY = -4;
    public static final int DX = 4;
    private Position bulletPos;
    private static Direction bulletDir;
    private int bulletX;
    private int bulletY;

    // Constructs a missile
    // effects: missile is positioned at coordinates (x, y)
    public Bullet(Position pos, Direction bulletDir) {
        this.bulletX = pos.getIntX();
        this.bulletY = pos.getIntY();
        this.bulletPos = pos;
        Bullet.bulletDir = bulletDir;
    }

    // modifies: this
    // effects: returns X-coordinate of bullet
    public int getBulletX() {
        return bulletX;
    }

    // modifies: this
    // effects: returns Y-coordinate of bullet
    public int getBulletY() {
        return bulletY;
    }

    // modifies: this
    // effects: returns Position of bullet
    public Position getPos() {
        return bulletPos;
    }

    // Moves bullet
    // modifies: this
    // effects: bullet is moved according to it's direction
    public void move() {
        if (bulletDir == Direction.UP) {
            this.bulletPos = bulletDir.move(this.bulletPos,1);
            bulletY = bulletY + DY;
        } else if (bulletDir == Direction.DOWN) {
            this.bulletPos = bulletDir.move(this.bulletPos,1);
            bulletY = bulletY - DY;
        } else if (bulletDir == Direction.RIGHT) {
            this.bulletPos = bulletDir.move(this.bulletPos,1);
            bulletX = bulletX + DX;
        } else if (bulletDir == Direction.LEFT) {
            this.bulletPos = bulletDir.move(this.bulletPos,1);
            bulletX = bulletX - DX;
        }
    }

    // Checks if bullet has collided with given position
    // modifies: this
    // effects: returns true if bullet is at a given position, false otherwise
    public boolean hasCollided(Position pos) {
        Rectangle invaderBoundingRect = new Rectangle(getPos().getIntX() - SIZE_X / 2,
                getPos().getIntY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(pos.getIntX() - Bullet.SIZE_X / 2,
                pos.getIntY() - Bullet.SIZE_Y / 2, Bullet.SIZE_X, Bullet.SIZE_Y);
        return invaderBoundingRect.intersects(missileBoundingRect);
    }

}
