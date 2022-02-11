package model;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Game {
    public static final int TICKS_PER_SECOND = 10;
    private final Player player = new Player();
    private final Set<Position> target = new HashSet<>();
    private static int currBullets = 5;
    private final List<Bullet> bullets;
    private final List<Reload> reloads;
    public static final Random RND = new Random();

    List<Bullet> bulletsToRemove = new ArrayList<>();
    List<Reload> powersToRemove = new ArrayList<>();
    private int score = 0;
    private int numReloads = 3;
    private boolean ended = false;
    public final int maxX;
    public final int maxY;

    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        bullets = new ArrayList<>();
        reloads = new ArrayList<>();
    }

    // progresses the game state
    // modifies: this
    // effects: progresses the game state, moving bullets and reload
    //          , and handling targets and reloads
    public void tick() {

        if (isOutOfBounds(player.getPlayerPos())) {
            ended = true;
            return;
        }
        bulletsHit();
        powersHit();
        moveBullets();
        moveReload();
        hitTarget();

        if (target.size() < 10) {
            spawnNewTarget();
        }

        if (reloads.isEmpty()) {
            spawnReload();
        }
    }

    // spawns a target
    // modifies: this
    // effects: spawns a target into a valid position
    public void spawnNewTarget() {
        Position pos = generateRandomPosition();
        while (!isValidPosition(pos)) {
            pos = generateRandomPosition();
        }
        target.add(pos);
    }

    // spawns a reload to pick up
    // modifies: this
    // effects: spawns a reload at top of the screen,
    //          but random x coordinate
    public void spawnReload() {
        Position pos = generateRandomPosition();
        Reload reload = new Reload(pos.getIntX(), 0);
        reloads.add(reload);
    }

    // updates the reloads
    // modifies: this
    // effects: moves the reloads pick-ups down the screen
    private void moveReload() {
        for (Reload next : reloads) {
            next.move();
        }
    }

    // Fires a bullet
    // modifies: this
    // effects:  fires a bullet if there are current bullets to fire
    //           otherwise silently returns if out of bullets.
    public void shoot() {
        if (bullets.size() < currBullets) {
            currBullets = currBullets - 1;
            Bullet bullet = new Bullet(player.getPlayerPos(), player.getDir());
            bullets.add(bullet);
        }
    }

    // updates the bullets
    // modifies: this
    // effects: moves the bullets
    private void moveBullets() {
        for (Bullet next : bullets) {
            next.move();
        }
    }

    // check if position is out of bounds
    // modifies: this
    // effects: returns whether a given position is out of game frame
    public boolean isOutOfBounds(Position pos) {
        return pos.getIntX() < 0
                ||
                pos.getIntY() < 0
                ||
                pos.getIntX() > maxX
                ||
                pos.getIntY() > maxY;
    }


    // checks if position is valid
    // modifies: this
    // effects: returns whether a given position is
    //          in bounds and not already occupied
    public boolean isValidPosition(Position pos) {
        return !isOutOfBounds(pos)
                &&
                !target.contains(pos)
                &&
                !player.hasCollided(pos);
    }

    // checks if player collides with a target
    // modifies: this
    // effects: checks if player collides with a target,
    //           ends game if it has happened
    private void hitTarget() {

        Position hitTarget = target.stream()
                .filter(player::hasCollided)
                .findFirst()
                .orElse(null);

        if (hitTarget == null) {
            return;
        }

        ended = true;
    }

    // check bullets
    // modifies: this
    // effects: checks all bullets for validity and
    //          removes any bullets that need to be removed
    private void bulletsHit() {
        for (Bullet bullet : bullets) {
            bulletHit(bullet);
        }
        bullets.removeAll(bulletsToRemove);
    }

    // check if individual bullet has hit any target
    // modifies: this
    // effects: checks for targets a bullet has hit, increases score
    //          marks bullet as invalid and removes target if hit. If bullet is
    //          out of bounds also marks it as invalid,
    private void bulletHit(Bullet bullet) {

        Position eatenFood = target.stream()
                .filter(bullet::hasCollided)
                .findFirst()
                .orElse(null);

        if (eatenFood == null) {
            if (isOutOfBounds(bullet.getPos())) {
                bulletsToRemove.add(bullet);
            }
            return;
        }

        target.remove(eatenFood);
        score++;
        bulletsToRemove.add(bullet);
    }

    // check reloads available for pickup
    // modifies: this
    // effects: checks all reloads for validity and
    //          removes any reloads that need to be removed
    private void powersHit() {
        for (Reload power : reloads) {
            powerHit(power);
        }
        reloads.removeAll(powersToRemove);
    }

    // check if player has eaten reload
    // modifies: this
    // effects: checks for reloads the player has eaten, increases score by 5
    //          marks power as invalid and adds reloads to player. If reload is
    //          out of bounds also marks it as invalid,
    private void powerHit(Reload power) {
        if (player.hasCollided(power.getPos())) {
            powersToRemove.add(power);
            score = score + 5;
            numReloads++;
        }
        if (isOutOfBounds(power.getPos())) {
            powersToRemove.add(power);
        }
    }

    // reload bullets
    // modifies: this
    // effects: if more than 0 reloads, add 5 bullets to current bullets
    public void useReload() {
        if (numReloads > 0) {
            numReloads = numReloads - 1;
            currBullets = currBullets + 5;
        }
    }


    // Generates a random position
    // modifies: this
    // effects: Generates a random position, guaranteed
    //          to be in bounds but not necessarily valid
    private Position generateRandomPosition() {
        return new Position(
                ThreadLocalRandom.current().nextInt(maxX),
                ThreadLocalRandom.current().nextInt(maxY)
        );
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Position> getTarget() {
        return target;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Reload> getReloads() {
        return reloads;
    }

    public int getNumPower() {
        return numReloads;
    }

    public int getScore() {
        return score;
    }

    public int getNumBullets() {
        return currBullets;
    }

    public boolean isEnded() {
        return ended;
    }
}

