package ui;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.lang.annotation.Target;

/*
 * The panel in which the game is rendered.
 */
public class GamePanel extends JPanel {

    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";
    private Game game;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isEnded()) {
            gameOver(g);
        }
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) {
        drawPlayer(g);
        drawTarget(g);
        drawBullets(g);
        drawReloads(g);
    }


    // effects: Renders the player
    private void drawPlayer(Graphics g) {
        Player player = game.getPlayer();
        g.setColor(Color.white);
        g.fillRect(player.getPlayerPos().getIntX() - player.SIZE_X / 2,
                player.getPlayerPos().getIntY() - player.SIZE_X / 2, player.SIZE_X, player.SIZE_Y);
        //g.fillRect(4, 4, 4, 4);
        g.setColor(Color.white);

    }


    // Draw the invaders
    // modifies: g
    // effects:  draws the invaders onto g
    private void drawTarget(Graphics g) {
        for (Position target : game.getTarget()) {
            drawPosition(g, target);
        }
    }

    // Draw an invader
    // modifies: g
    // effects:  draws the invader i onto g
    private void drawPosition(Graphics g, Position i) {
        Color savedCol = g.getColor();
        g.setColor(Color.red);
        g.fillOval(i.getIntX() - 15 / 2, i.getIntY() - 9 / 2, 15, 9);
        g.setColor(Color.red);
    }

    // Draws the missiles
    // modifies: g
    // effects:  draws the bullets onto g
    private void drawBullets(Graphics g) {
        for (Bullet next : game.getBullets()) {
            drawBullet(g, next);
        }
    }

    // Draws a missile
    // modifies: g
    // effects:  draws the missile m onto g
    private void drawBullet(Graphics g, Bullet m) {
        Color savedCol = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval(m.getBulletX() - m.SIZE_X / 2, m.getBulletY() - m.SIZE_Y / 2, m.SIZE_X, m.SIZE_Y);
        g.setColor(Color.yellow);
    }


    // effects: Renders the pickup-able reloads
    private void drawReloads(Graphics g) {
        for (Reload next : game.getReloads()) {
            drawPower(g, next);
        }
    }

    // Draws a missile
    // modifies: g
    // effects:  draws the missile m onto g
    private void drawPower(Graphics g, Reload m) {
        Color savedCol = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval(m.getIntX() - m.SIZE_X / 2, m.getIntY() - m.SIZE_Y / 2, m.SIZE_X, m.SIZE_Y);
        g.setColor(Color.yellow);
    }

    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        centreString(REPLAY, g, fm, Game.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, yPos);
    }
}
