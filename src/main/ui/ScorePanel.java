package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

//Represents the panel in which the scoreboard is displayed.
public class ScorePanel extends JPanel {
    private static final String SCORE_TXT = "Score: ";
    private static final String BULLETS_TXT = "Bullets remaining: ";
    private static final String RELOADS_TXT = "Reloads available: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel scorelbl;
    private JLabel bulletsLBL;
    private JLabel reloadsLbl;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(Color.white);

        scorelbl = new JLabel(SCORE_TXT + game.getScore());
        scorelbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        bulletsLBL = new JLabel(BULLETS_TXT + game.getNumBullets());
        bulletsLBL.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        reloadsLbl = new JLabel(RELOADS_TXT + game.getNumPower());
        bulletsLBL.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        add(scorelbl);
        add(Box.createHorizontalStrut(10));
        add(bulletsLBL);
        add(Box.createHorizontalStrut(10));
        add(reloadsLbl);
    }

    public void setGame(Game g) {
        game = g;
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates score, bullets, reloads to reflect current state of game
    public void update() {
        scorelbl.setText(SCORE_TXT + game.getScore());
        scorelbl.setForeground(Color.black);

        bulletsLBL.setText(BULLETS_TXT + game.getNumBullets());
        if (game.getNumBullets() < 3) {
            bulletsLBL.setForeground(Color.RED);
        } else {
            bulletsLBL.setForeground(Color.black);
        }

        reloadsLbl.setText(RELOADS_TXT + game.getNumPower());
        reloadsLbl.setForeground(Color.black);

        repaint();
    }
}