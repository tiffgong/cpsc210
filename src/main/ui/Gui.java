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
import java.net.MalformedURLException;
import java.net.URL;

// main gui and splash screens
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
    // effects: sets up window in which the game will be played
    public Gui() {
        super("PEW PEW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        setUpGame();
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        addTimer();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // loading splash screen
    // modifies: none
    // effects:  shows an image displaying loading for 2 seconds
    public static void splash() throws MalformedURLException {
        JWindow window = new JWindow();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.getContentPane().add(
                new JLabel("", new ImageIcon(new URL("https://i.imgur.com/cgJoUBH.gif")), SwingConstants.CENTER));
        window.setBounds(((int) d.getWidth() - 722) / 2, ((int) d.getHeight() - 401) / 2, 500, 500);
        window.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    // controls splash screen
    // modifies: none
    // effects:  shows an image displaying controls for 4 seconds
    public static void controls() throws MalformedURLException {
        JWindow window = new JWindow();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.getContentPane().add(
                new JLabel("", new ImageIcon(new URL("https://i.imgur.com/W8Ness0.gif")), SwingConstants.CENTER));
        window.setBounds(((int) d.getWidth() - 722) / 2, ((int) d.getHeight() - 401) / 2, 500, 500);
        window.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    // Set up game
    // modifies: none
    // effects:  set-ups windows for a new game
    public void setUpGame() {
        menuBar = new JMenuBar();
        file = new JMenu("Options");
        menuItem("Restart");
        menuItem("Save");
        menuItem("Load");
        menuItem("Quit");

        setJMenuBar(menuBar);

        game = new Game(Game.WIDTH, Game.HEIGHT);
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        game = new Game(Game.WIDTH, Game.HEIGHT);
        reset(game);
    }

    // modifies: menuBar
    // effects: adds a menu item to the Jmenu
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

    // modifies: this
    // effects: corresponding to the action event, an action will occur
    public void actionPerformed(ActionEvent ae) {
        String choice = ae.getActionCommand();
        if (choice.equals("Save")) {
            saveGame();
            JOptionPane.showMessageDialog(this, "Saved");
        } else if (choice.equals("Load")) {
            loadGame();
            JOptionPane.showMessageDialog(this, "Loaded");
        } else if (choice.equals("Quit")) {
            System.exit(0);
        } else if (choice.equals("Restart")) {
            game = new Game(Game.WIDTH, Game.HEIGHT);
            reset(game);
        }
    }

    // helper function to reset/load game
    // modifies: this
    // effects: re- sets game
    private void reset(Game game) {
        gp.setGame(game);
        sp.setGame(game);
        setVisible(true);
        revalidate();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // modifies: this
    // effects: A key handler that respond to key events
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
            game.getPlayer().move(4);
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

    // effects: Plays the game
    public static void main(String[] args) throws MalformedURLException {
        splash();
        controls();
        new Gui();
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
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
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            reset(game);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
