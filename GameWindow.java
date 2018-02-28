
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JPanel {

    private final int INIT_WIDTH = 400;
    private final int INIT_HEIGHT = 600;

    private ScheduledExecutorService uiUpdater;
    private Game game;

    public GameWindow(Game game) {
        this.game = game;

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

    private void startAnimation() {
        uiUpdater = Executors.newScheduledThreadPool(1);
        uiUpdater.scheduleAtFixedRate(super::repaint, 0, 200, TimeUnit.MILLISECONDS);
    }

    MouseListener mouseList = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            game.getPlayer().moveToPoint(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };


}
