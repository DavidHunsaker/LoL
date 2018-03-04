
import ui.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JPanel {

    private final int INIT_WIDTH = 1600;
    private final int INIT_HEIGHT = 900;

    private ScheduledExecutorService uiUpdater;
    private Game game;

    public GameWindow(Game game) {
        this.game = game;
        setUpUiElements();

        setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        this.setDoubleBuffered(true);
        setBackground(Color.black);

        addMouseListener(mouseList);
        startAnimation();

        game.startGame();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getEntities().forEach((entity) -> {
            g.setColor(entity.getColor());
            // The rounding here is an unfortunate side effect of the fact that everything Swing does seems to be integer based.
            // But a different graphical engine would probably care about the decimal precision
            g.fillRect((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), entity.getSize(), entity.getSize());
        });
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
            game.getPlayer().moveToPoint(e.getX(), e.getY());
        }

        public void mouseClicked(MouseEvent e) { }
        public void mouseReleased(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
    };

}
