
import ui.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JPanel {

    private int viewportWidth = 1600;
    private int viewportHeight = 900;

    //TODO this shouldn't be defined in the UI code- this should be defined somewhere in the game logic
    private final int MAP_WIDTH = 2000;
    private final int MAP_HEIGHT = 1300;

    private int scrollSpeed = 10;
    private int scrollX = 0;
    private int scrollY = 0;

    private ScheduledExecutorService uiUpdater;
    private Game game;

    public GameWindow(Game game) {
        this.game = game;
        setUpUiElements();

        setPreferredSize(new Dimension(viewportWidth, viewportHeight));
        this.setDoubleBuffered(true);
        setBackground(Color.black);

        this.addMouseListener(mouseList);
        this.addKeyListener(keyListener);
        startAnimation();


        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                viewportWidth = e.getComponent().getWidth();
                viewportHeight = e.getComponent().getHeight();
            }
        });

        game.startGame();
        this.setFocusable(true);
        this.requestFocus();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getEntities().forEach((entity) -> {
            g.setColor(entity.getColor());
            // The rounding here is an unfortunate side effect of the fact that everything Swing does seems to be integer based.
            // But a different graphical engine would probably care about the decimal precision
            g.fillRect((int) Math.round(entity.getX() - scrollX), (int) Math.round(entity.getY() - scrollY), entity.getSize(), entity.getSize());
        });

        // Draw the map boundaries
        g.setColor(Color.WHITE);
        g.drawRect(1 - scrollX, 1 - scrollY, MAP_WIDTH - 2, MAP_HEIGHT - 2);
    }

    private void setUpUiElements() {
        this.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(new PlayerPanel(), BorderLayout.WEST);
        bottomPanel.setOpaque(false);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void startAnimation() {
        uiUpdater = Executors.newScheduledThreadPool(1);
        uiUpdater.scheduleAtFixedRate(super::repaint, 0, 10, TimeUnit.MILLISECONDS);
    }

    private MouseListener mouseList = new MouseListener() {
        public void mousePressed(MouseEvent e) {
            game.getPlayer().moveToPoint(e.getX() + scrollX, e.getY() + scrollY);
        }
        public void mouseClicked(MouseEvent e) { }
        public void mouseReleased(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
    };

    private KeyListener keyListener = new KeyListener() {
        public void keyTyped(KeyEvent e) { }
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case 37: // Left
                    scrollX -= scrollSpeed;
                    if (scrollX < 0) {
                        scrollX = 0;
                    }
                    break;
                case 38: // Up
                    scrollY -= scrollSpeed;
                    if (scrollY < 0) {
                        scrollY = 0;
                    }
                    break;
                case 39: // Right
                    if (viewportWidth > MAP_WIDTH) {
                        return;
                    }
                    scrollX += scrollSpeed;
                    if (scrollX > MAP_WIDTH - viewportWidth) {
                        scrollX = MAP_WIDTH - viewportWidth;
                    }
                    break;
                case 40: // Down
                    if (viewportHeight > MAP_HEIGHT) {
                        return;
                    }
                    scrollY += scrollSpeed;
                    if (scrollY > MAP_HEIGHT - viewportHeight) {
                        scrollY = MAP_HEIGHT - viewportHeight;
                    }
                    break;
            }
        }
    };

}
