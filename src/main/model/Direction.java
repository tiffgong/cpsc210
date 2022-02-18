package model;

// four general directions
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // modifies: this
    // effects:  Moves a position one increment in the facing direction
    public Position move(Position pos, int blocks) {
        return new Position(
                pos.getIntX() + blocks * dx,
                pos.getIntY() + blocks * dy
        );
    }
}

