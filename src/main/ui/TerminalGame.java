package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;

import java.io.IOException;

public class TerminalGame {
    private Game game;
    private Screen screen;
    private WindowBasedTextGUI endGui;


    // modifies: this
    // effects: Begins the game and method does not leave execution
    //          until game is complete.
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new Game(
                // divide the columns in two
                // this is so we can make the each part of
                // the snake wide, since terminal characters are
                // taller than they are wide
                (terminalSize.getColumns() - 1) / 2,
                // first row is reserved for us
                terminalSize.getRows() - 2
        );
        beginTicks();
    }


    // modifies: this
    // effects: Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
    //          game has ended and the endGui has been exited.
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }

        System.exit(0);
    }

    // modifies: this
    // effects: Handles one cycle in the game by taking user input,
    //          ticking the game internally, and rendering the effects
    private void tick() throws IOException {
        handleUserInput();

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));

    }


    // modifies: this
    // effects: Sets the snake's direction, or shoots or reloads corresponding to the
    //          user's keystroke
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();


        if (stroke == null) {
            return;
        }

        if (stroke.getKeyType() == KeyType.Enter) {
            game.shoot();
        }

        if (stroke.getKeyType() == KeyType.Backspace) {
            game.useReload();
        }

        if (stroke.getCharacter() != null) {
            return;
        }

        Direction dir = directionFrom(stroke.getKeyType());

        if (dir == null) {
            return;
        }

        game.getPlayer().setDirection(dir);

        game.getPlayer().move(1);
    }


    // modifies: this
    // effects: Returns the natural direction corresponding to the KeyType.
    //          Null if none found.
    private Direction directionFrom(KeyType type) {
        switch (type) {
            case ArrowUp:
                return Direction.UP;
            case ArrowDown:
                return Direction.DOWN;
            case ArrowRight:
                return Direction.RIGHT;
            case ArrowLeft:
                return Direction.LEFT;
            default:
                return null;
        }
    }


    // effects: Renders the current screen.
    //          Draws the end screen if the game has ended, otherwise
    //          draws the score, player, targets, bullets, reloads.
    private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawScore();
        drawNumReloads();
        drawPlayer();
        drawTarget();
        drawBullets();
        drawReloads();
        drawNumBullets();
    }

    // effects: Renders the end screen.
    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You died lul also got " + game.getScore() + "!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    // effects: Renders the current score
    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    // effects: Renders the current bullets
    private void drawNumBullets() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.RED);
        text.putString(12, 0, "Bullets: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(20, 0, String.valueOf(game.getNumBullets()));
    }

    // effects: Renders the current reloads
    private void drawNumReloads() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.BLUE);
        text.putString(24, 0, "Reloads: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(33, 0, String.valueOf(game.getNumPower()));
    }

    // effects: Renders the player
    private void drawPlayer() {
        Player snake = game.getPlayer();

        drawPosition(snake.getPlayerPos(), TextColor.ANSI.WHITE, '█', true);

    }

    // effects: Renders the targets
    private void drawTarget() {
        for (Position target : game.getTarget()) {
            drawPosition(target, TextColor.ANSI.RED, '⬤', false);
        }
    }

    // effects: Renders the bullets
    private void drawBullets() {
        for (Bullet next : game.getBullets()) {
            drawBullet(next);
        }
    }

    // effects: Renders the pickup-able reloads
    private void drawReloads() {
        for (Reload next : game.getReloads()) {
            drawPower(next);
        }
    }

    // modifies: terminal
    // effects: Draws a character in a given position on the terminal.
    //           If wide, it will draw the character twice to make it appear wide.
    private void drawPosition(Position pos, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getIntX() * 2, pos.getIntY() + 1, String.valueOf(c));

        if (wide) {
            text.putString(pos.getIntX() * 2 + 1, pos.getIntY() + 1, String.valueOf(c));
        }
    }

    // effects: Renders a bullet
    private void drawBullet(Bullet bull) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(bull.getBulletX() * 2, bull.getBulletY() + 1, String.valueOf('★'));

    }

    // effects: Renders a reload
    private void drawPower(Reload bull) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.YELLOW);
        text.putString(bull.getIntX() * 2, bull.getIntY() + 1, String.valueOf('★'));

    }
}