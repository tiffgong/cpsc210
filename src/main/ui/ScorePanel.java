package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the panel in which the scoreboard is displayed.
 */
public class ScorePanel extends JPanel {
    private static final String INVADERS_TXT = "Score: ";
    private static final String MISSILES_TXT = "Bullets remaining: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel invadersLbl;
    private JLabel missilesLbl;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));

        invadersLbl = new JLabel(INVADERS_TXT + game.getScore());
        invadersLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        missilesLbl = new JLabel(MISSILES_TXT + game.getNumBullets());
        missilesLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        add(invadersLbl);
        add(Box.createHorizontalStrut(10));
        add(missilesLbl);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        invadersLbl.setText(INVADERS_TXT + game.getScore());
        missilesLbl.setText(MISSILES_TXT + game.getNumBullets());
        repaint();
    }
}