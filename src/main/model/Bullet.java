package model;

public class Bullet {

    public static final int DY = -2;
    public static final int DX = 2;
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
        return bulletPos.equals(pos);
    }

}
