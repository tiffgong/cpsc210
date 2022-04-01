package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// represents game
public class Game implements Writable {
    public static final int TICKS_PER_SECOND = 10;
    private Player player = new Player();
    private Set<Position> target = new HashSet<>();
    private ArrayList<Bullet> bullets;
    private List<Reload> reloads;
    public static final Random RND = new Random();

    List<Bullet> bulletsToRemove = new ArrayList<>();
    List<Reload> powersToRemove = new ArrayList<>();
    private int score = 0;
    private int numReloads = 1;
    private int currBullets = 5;
    private boolean ended = false;
    public final int maxX;
    public final int maxY;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

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
        if (isEnded()) {
            bullets.clear();
            target.clear();
        } else {
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

            if (reloads.size() < 2) {
                spawnReload();
            }
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

        Position hit = target.stream()
                .filter(bullet::hasCollided)
                .findFirst()
                .orElse(null);

        target.remove(hit);
        if (hit == null) {
            if (isOutOfBounds(bullet.getPos())) {
                bulletsToRemove.add(bullet);
            }
            return;
        }
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
            EventLog.getInstance().logEvent(new Event("Got reload."));
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
            EventLog.getInstance().logEvent(new Event("Used reload."));
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

    // modifies: this
    // effects: returns player
    public Player getPlayer() {
        return player;
    }

    // modifies: this
    // effects: returns set of Targets
    public Set<Position> getTarget() {
        return target;
    }

    // modifies: this
    // effects: returns list of Bullets
    public List<Bullet> getBullets() {
        return bullets;
    }

    // modifies: this
    // effects: returns list of Reloads
    public List<Reload> getReloads() {
        return reloads;
    }

    // modifies: this
    // effects: returns number of reloads
    public int getNumPower() {
        return numReloads;
    }

    // modifies: this
    // effects: returns score number
    public int getScore() {
        return score;
    }

    // modifies: this
    // effects: returns current number of bullets
    public int getNumBullets() {
        return currBullets;
    }

    // modifies: this
    // effects: returns whether game is ended
    public boolean isEnded() {
        return ended;
    }

    // effects: sets list of Bullets
    public void setBullets(int bull) {
        currBullets = bull;
    }

    // effects: sets number of reloads
    public void setNumPower(int nump) {
        numReloads = nump;
    }

    // effects: sets score number
    public void setScore(int sc) {
        score = sc;
    }

    // effects: get maxX
    public int getMaxX() {
        return maxX;
    }

    // effects: get maxY
    public int getMaxY() {
        return maxY;
    }

    // effects: set targets
    public void setTarget(int posX, int posY) {
        target.remove(0);
        target.add(new Position(posX, posY));
    }

    // effects: set player X coord
    public void setPlayerX(int playerX) {
        this.player.setPositionX(playerX);
    }

    // effects: set player Y coord
    public void setPlayerY(int playerY) {
        this.player.setPositionY(playerY);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("score", score);
        json.put("number reloads", getNumPower());
        json.put("number bullets", getNumBullets());
        json.put("targets", getTarget());
        json.put("player x", player.getPlayerPos().getIntX());
        json.put("player y", player.getPlayerPos().getIntY());
        json.put("max x", getMaxX());
        json.put("max y", getMaxY());
        json.put("ended?", isEnded());


        return json;
    }

}

