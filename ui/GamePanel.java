package ui;

import logic.Entity;
import ui.listeners.MousePressedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {

    private UiController uiController;
    private Entity player;

    //TODO this shouldn't be defined in the UI code- this should be defined somewhere in the game logic
    private final int MAP_WIDTH = 2000;
    private final int MAP_HEIGHT = 1300;

    GamePanel(UiController uiController, Entity player) {
        this.uiController = uiController;
        this.player = player;

        this.addMouseListener(mouseList);
        this.addKeyListener(keyListener);
        this.setFocusable(true);
        this.requestFocus();
        this.setDoubleBuffered(true);

        this.setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        uiController.getGameEntities().forEach((entity) -> {
            g.setColor(entity.getColor());
            // The rounding here is an unfortunate side effect of the fact that everything Swing does seems to be integer based.
            // But a different graphical engine would probably care about the decimal precision
            g.fillRect((int) Math.round(entity.getX() - uiController.getScrollOffsetX()), (int) Math.round(entity.getY() - uiController.getScrollOffsetY()), entity.getSize(), entity.getSize());
        });

        // Draw the map boundaries
        g.setColor(Color.WHITE);
        g.drawRect(1 - uiController.getScrollOffsetX(), 1 - uiController.getScrollOffsetY(), MAP_WIDTH - 2, MAP_HEIGHT - 2);
    }

    private MouseListener mouseList = new MousePressedListener() {
        // Probably want to abstract this out somewhere else. Maybe route it through the controller
        public void mousePressed(MouseEvent e) {
            player.moveToPoint(e.getX() + uiController.getScrollOffsetX(), e.getY() + uiController.getScrollOffsetY());
        }
    };

    private KeyListener keyListener = new KeyListener() {
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case 37: // Left
                case 39: // Right
                    uiController.setKeyScrollX(CardinalDirection.NONE); break;
                case 38: // Up
                case 40: // Down
                    uiController.setKeyScrollY(CardinalDirection.NONE); break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case 37: // Left
                    uiController.setKeyScrollX(CardinalDirection.LEFT); break;
                case 38: // Up
                    uiController.setKeyScrollY(CardinalDirection.UP); break;
                case 39: // Right
                    uiController.setKeyScrollX(CardinalDirection.RIGHT); break;
                case 40: // Down
                    uiController.setKeyScrollY(CardinalDirection.DOWN); break;
            }
        }
    };

}
