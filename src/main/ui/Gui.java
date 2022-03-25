package ui;


import model.Direction;
import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Gui extends JFrame implements ActionListener {

    private static final int INTERVAL = 1;
    private Game game;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/game.json";
    private GamePanel gp;
    private ScorePanel sp;
    private JMenu file;
    private JMenuBar menuBar;

    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public Gui() {
        super("PEW PEW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        //this.setLayout(new FlowLayout());
        setUpGame();
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        addTimer();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public static void splash() throws MalformedURLException {
        JWindow window = new JWindow();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.getContentPane().add(
                new JLabel("", new ImageIcon(new URL("https://i.imgur.com/cgJoUBH.gif")), SwingConstants.CENTER));
        window.setBounds(((int) d.getWidth() - 722) / 2, ((int) d.getHeight() - 401) / 2, 500, 500);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    public void setUpGame() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        file = new JMenu("Options");
        menuItem("Restart");
        menuItem("Save");
        menuItem("Load");
        menuItem("Quit");
        game = new Game(Game.WIDTH, Game.HEIGHT);
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        setVisible(true);
    }


    private void menuItem(String tex) {
        JMenuItem item = new JMenuItem(tex);
        item.addActionListener(this);
        file.add(item);
        menuBar.add(file);
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                game.tick();
                gp.repaint();
                sp.update();
            }
        });

        t.start();
    }

    public void actionPerformed(ActionEvent ae) {

        String choice = ae.getActionCommand();
        if (choice.equals("Save")) {
            saveWorkRoom();
            JOptionPane.showMessageDialog(this, "Saved");
        } else if (choice.equals("Load")) {
            loadWorkRoom();
            JOptionPane.showMessageDialog(this, "Loaded");
        } else if (choice.equals("Quit")) {
            System.exit(0);
        } else if (choice.equals("Restart")) {
            dispose();
            setUpGame();
        }
    }


    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                game.shoot();
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                game.useReload();
            }

            Direction dir = directionFrom(e.getKeyCode());
            if (dir == null) {
                return;
            }

            game.getPlayer().setDirection(dir);
            game.getPlayer().move(5);
        }
    }

    // modifies: this
    // effects: Returns the natural direction corresponding to the KeyType.
    //          Null if none found.
    private Direction directionFrom(int type) {
        switch (type) {
            case KeyEvent.VK_UP:
                return Direction.UP;
            case KeyEvent.VK_DOWN:
                return Direction.DOWN;
            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;
            case KeyEvent.VK_LEFT:
                return Direction.LEFT;
            default:
                return null;
        }
    }

    /*
     * Play the game
     */
    public static void main(String[] args) throws MalformedURLException {
        splash();
        new Gui();

    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved from " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            file = new JMenu("Options");
            menuItem("Restart");
            menuItem("Save");
            menuItem("Load");
            menuItem("Quit");
            game = jsonReader.read();
            gp = new GamePanel(game);
            sp = new ScorePanel(game);
            add(gp);
            add(sp, BorderLayout.NORTH);
            setVisible(true);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
