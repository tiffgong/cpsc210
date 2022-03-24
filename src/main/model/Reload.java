package model;

// represents an item used for "reloads"
public class Reload {

    public static final int DY = 10;
    private static final int JIGGLE_X = 10;
    public static final int SIZE_X = 10;
    public static final int SIZE_Y = 9;
    private Position powerPos;
    private int intX;
    private int intY;

    // Constructs an Reload
    // effects: reload is positioned at coordinates (x, y)
    public Reload(int x, int y) {
        this.intX = x;
        this.intY = y;
        this.powerPos = new Position(x,y);
    }

    // modifies: this
    // effects: returns X-coordinate of reload
    public int getIntX() {
        return intX;
    }

    // modifies: this
    // effects: returns Y-coordinate of reload
    public int getIntY() {
        return intY;
    }

    // modifies: this
    // effects: returns Position of reload
    public Position getPos() {
        return powerPos;
    }

    // Updates reload on clock tick
    // modifies: this
    // effects:  reload is moved down the screen DY units and randomly takes
    //           a step of size no greater than JIGGLE_X to the left or right
    public void move() {
        intX = intX + Game.RND.nextInt(2 * JIGGLE_X + 1) - JIGGLE_X;
        intY = intY + DY;
        this.powerPos = new Position(intX, intY);
    }


}
