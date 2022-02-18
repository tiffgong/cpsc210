package model;


//Represents a position in the game space.
public class Position {
    private final int intX;
    private final int intY;

    public Position(int x, int y) {
        this.intX = x;
        this.intY = y;
    }

    // modifies: this
    // effects: returns X-coordinate of Position
    public int getIntX() {
        return intX;
    }

    // modifies: this
    // effects: returns Y-coordinate of Position
    public int getIntY() {
        return intY;
    }

    // checks if equal to given position
    // modifies: this
    // effects: true if position equal to given position, otherwise false
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position playerNode = (Position) o;
        return intX == playerNode.intX
                &&
                intY == playerNode.intY;
    }

}

