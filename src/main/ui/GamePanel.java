package ui;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

// The panel in which the game is rendered.
public class GamePanel extends JPanel {

    private static final String OVER = "Game Over !";
    private static final String SCORE = "Score:";
    private Game game;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.white);
        this.game = g;
    }

    // effects: sets game to given game
    public void setGame(Game g) {
        game = g;
    }

    // modifies: g
    // effects: paints on the components of the game
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.isEnded()) {
            gameOver(g);

        } else {
            try {
                drawGame(g);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) throws MalformedURLException {
        drawPlayer(g);
        drawTarget(g);
        drawBullets(g);
        drawReloads(g);
    }


    // effects: Renders the player
    private void drawPlayer(Graphics g) throws MalformedURLException {
        Player player = game.getPlayer();
        g.setColor(Color.white);
        Image image = new ImageIcon(new URL("https://i.imgur.com/Ik322mC.png")).getImage();
        g.drawImage(image, player.getPlayerPos().getIntX() - Player.SIZE_X / 2,
                      player.getPlayerPos().getIntY() - Player.SIZE_X / 2, Player.SIZE_X, Player.SIZE_Y,
                null);
        g.setColor(Color.white);

    }


    // Draw the targets
    // modifies: g
    // effects:  draws the targets onto g
    private void drawTarget(Graphics g) throws MalformedURLException {
        for (Position target : game.getTarget()) {
            drawPosition(g, target);
        }
    }

    // Draw a target
    // modifies: g
    // effects:  draws the target i onto g
    private void drawPosition(Graphics g, Position i) throws MalformedURLException {
        Image image = new ImageIcon(new URL("https://i.imgur.com/gxnILDM.png")).getImage();
        g.drawImage(image, i.getIntX() - 40 / 2, i.getIntY() - 40 / 2, 40, 40, null);
        g.setColor(Color.red);
    }

    // Draws the bullets
    // modifies: g
    // effects:  draws the bullets onto g
    private void drawBullets(Graphics g) {
        for (Bullet next : game.getBullets()) {
            drawBullet(g, next);
        }
    }

    // Draws a bullet
    // modifies: g
    // effects:  draws the bullet m onto g
    private void drawBullet(Graphics g, Bullet m) {
        g.fillOval(m.getBulletX() - Bullet.SIZE_X / 2, m.getBulletY() - Bullet.SIZE_Y / 2,
                Bullet.SIZE_X, Bullet.SIZE_Y);
        g.setColor(Color.red);
    }


    // effects: Renders the pickup-able reloads
    private void drawReloads(Graphics g) {
        for (Reload next : game.getReloads()) {
            drawPower(g, next);
        }
    }

    // Draws the reloads
    // modifies: g
    // effects:  draws the reloads onto g
    private void drawPower(Graphics g, Reload reload) {
        g.drawOval(reload.getIntX() - Reload.SIZE_X / 2, reload.getIntY() - Reload.SIZE_Y / 2,
                Reload.SIZE_X, Reload.SIZE_Y);
        g.setColor(Color.red);
    }

    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        centreString(SCORE + game.getScore(), g, fm, Game.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }
}
