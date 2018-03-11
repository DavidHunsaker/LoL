
import ui.PlayerPanel;
import ui.listeners.MousePresenceListener;
import ui.listeners.MousePressedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JLayeredPane {

    private int viewportWidth = 1600;
    private int viewportHeight = 900;

    //TODO this shouldn't be defined in the UI code- this should be defined somewhere in the game logic
    private final int MAP_WIDTH = 2000;
    private final int MAP_HEIGHT = 1300;

    private int scrollSpeed = 6;
    private int scrollX = 0;
    private int scrollY = 0;

    private int mouseScrollX = 0;
    private int mouseScrollY = 0;
    private int keyScrollX = 0;
    private int keyScrollY = 0;

    private ScheduledExecutorService uiUpdater;
    private Game game;

    private JPanel scrollPane;
    private JPanel gamePane;
    private JPanel guiPane;

    public GameWindow(Game game) {
        this.game = game;

        setPreferredSize(new Dimension(viewportWidth, viewportHeight));
        this.setDoubleBuffered(true);

        scrollPane = createScrollPanel();
        gamePane = new GamePanel();
        guiPane = createInterfacePanels();

        this.add(gamePane, JLayeredPane.DEFAULT_LAYER); // Depth 0
        this.add(guiPane, JLayeredPane.MODAL_LAYER); // Depth 200
        this.add(scrollPane, JLayeredPane.MODAL_LAYER); // Depth 400

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                viewportWidth = e.getComponent().getWidth();
                viewportHeight = e.getComponent().getHeight();

                // Because of the way the JLayeredPane works, these have to have their bounds set in this way, rather than using PreferredSize
                scrollPane.setBounds(0, 0, viewportWidth, viewportHeight);
                gamePane.setBounds(0, 0, viewportWidth, viewportHeight);
                guiPane.setBounds(0, 0, viewportWidth, viewportHeight);
            }
        });

        startAnimation();
        game.startGame();
    }

    private void updateUi() {
        scrollX += mouseScrollX * scrollSpeed;
        scrollY += mouseScrollY * scrollSpeed;
        scrollX += keyScrollX * scrollSpeed;
        scrollY += keyScrollY * scrollSpeed;

        super.repaint();
    }

    private void startAnimation() {
        uiUpdater = Executors.newScheduledThreadPool(1);
        uiUpdater.scheduleAtFixedRate(this::updateUi, 0, 10, TimeUnit.MILLISECONDS);
    }

    private JPanel createInterfacePanels() {
        JPanel guiPanel = new JPanel(new BorderLayout());
        guiPanel.setOpaque(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(new PlayerPanel());
        guiPanel.add(bottomPanel, BorderLayout.SOUTH);

        return guiPanel;
    }

    private class GamePanel extends JPanel {

        public GamePanel() {
            this.addMouseListener(mouseList);
            this.addKeyListener(keyListener);
            this.setFocusable(true);
            this.requestFocus();
            this.setDoubleBuffered(true);

            this.setBackground(Color.BLACK);
            this.setBounds(0, 0, viewportWidth, viewportHeight);
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

        private MouseListener mouseList = new MousePressedListener() {
            public void mousePressed(MouseEvent e) {
                game.getPlayer().moveToPoint(e.getX() + scrollX, e.getY() + scrollY);
            }
        };

        private KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case 37: // Left
                    case 39: // Right
                        keyScrollX = 0; break;
                    case 38: // Up
                    case 40: // Down
                        keyScrollY = 0; break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case 37: // Left
                        keyScrollX = -1; break;
                    case 38: // Up
                        keyScrollY = -1; break;
                    case 39: // Right
                        keyScrollX = 1; break;
                    case 40: // Down
                        keyScrollY = 1; break;
                }
            }
        };

    }

    private JPanel createScrollPanel() {
        int scrollAreaSize = 10;

        JPanel scrollLeft = new JPanel();
        JPanel scrollUp = new JPanel();
        JPanel scrollRight = new JPanel();
        JPanel scrollDown = new JPanel();
        JPanel scrollUpLeft = new JPanel();
        JPanel scrollUpRight = new JPanel();
        JPanel scrollDownLeft = new JPanel();
        JPanel scrollDownRight = new JPanel();

        scrollUp.setPreferredSize(new Dimension(0, scrollAreaSize));
        scrollUpLeft.setPreferredSize(new Dimension(scrollAreaSize, scrollAreaSize));
        scrollUpRight.setPreferredSize(new Dimension(scrollAreaSize, scrollAreaSize));

        scrollDown.setPreferredSize(new Dimension(0, scrollAreaSize));
        scrollDownLeft.setPreferredSize(new Dimension(scrollAreaSize, scrollAreaSize));
        scrollDownRight.setPreferredSize(new Dimension(scrollAreaSize, scrollAreaSize));

        scrollLeft.setPreferredSize(new Dimension(scrollAreaSize, 0));
        scrollRight.setPreferredSize(new Dimension(scrollAreaSize, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        topPanel.add(scrollUp, BorderLayout.CENTER);
        topPanel.add(scrollUpLeft, BorderLayout.WEST);
        topPanel.add(scrollUpRight, BorderLayout.EAST);

        bottomPanel.add(scrollDown, BorderLayout.CENTER);
        bottomPanel.add(scrollDownLeft, BorderLayout.WEST);
        bottomPanel.add(scrollDownRight, BorderLayout.EAST);

        scrollLeft.setOpaque(false);
        scrollUp.setOpaque(false);
        scrollRight.setOpaque(false);
        scrollDown.setOpaque(false);
        scrollUpLeft.setOpaque(false);
        scrollUpRight.setOpaque(false);
        scrollDownLeft.setOpaque(false);
        scrollDownRight.setOpaque(false);
        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        scrollLeft.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                mouseScrollX = -1;
            }
            public void mouseExited(MouseEvent e) {
                mouseScrollX = 0;
            }
        });

        scrollUp.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                mouseScrollY = -1;
            }
            public void mouseExited(MouseEvent e) {
                mouseScrollY = 0;
            }
        });

        scrollRight.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                mouseScrollX = 1;
            }
            public void mouseExited(MouseEvent e) {
                mouseScrollX = 0;
            }
        });

        scrollDown.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                mouseScrollY = 1;
            }
            public void mouseExited(MouseEvent e) {
                mouseScrollY = 0;
            }
        });

        scrollUpLeft.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                mouseScrollY = -1;
                mouseScrollX = -1;
            }
            public void mouseExited(MouseEvent e) {
                mouseScrollY = 0;
                mouseScrollX = 0;
            }
        });

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.setOpaque(false);
        scrollPanel.setBounds(0, 0, viewportWidth, viewportHeight);
        scrollPanel.add(scrollLeft, BorderLayout.WEST);
        scrollPanel.add(topPanel, BorderLayout.NORTH);
        scrollPanel.add(scrollRight, BorderLayout.EAST);
        scrollPanel.add(bottomPanel, BorderLayout.SOUTH);

        return scrollPanel;
    }

}
