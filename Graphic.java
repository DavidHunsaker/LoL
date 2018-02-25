
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphic extends JPanel {

    int INIT_WIDTH = 400;
    int INIT_HEIGHT = 600;

    int x, y = 0;
    int deltaX, deltaY;
    int destinationX, destinationY = 0;
    int DELAY = 1;
    int blockSpeed = 10;

    Color color;

    public void paintComponent(Graphics g) {

        int width = getWidth();
        int height = getHeight();


        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);


        deltaX = (destinationX-x)/blockSpeed;
        deltaY = (destinationY-y)/blockSpeed;

            x += deltaX;
            y += deltaY;


        g.setColor(color);
        g.fillRect(x, y, 10, 10);

        Toolkit.getDefaultToolkit().sync();


    }

    private void setDeltaX(int newDeltaX) {
        deltaX = newDeltaX;
    }

    private void setDeltaY(int newDeltaY) {
        deltaY = newDeltaY;
    }

    private int getGraphicX() {
        return x;
    }

    private int getGraphicY() {
        return y;
    }

    public Graphic() {

        setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        this.setDoubleBuffered(true);
        setBackground(Color.black);

        color = Color.BLUE;





        addMouseListener(mouseList);
        startAnimation();

    }

    private void startAnimation() {
        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        };
        new Timer(DELAY, taskPerformer).start();
    }

    MouseListener mouseList = new MouseListener() {


        @Override
        public void mouseClicked(MouseEvent e) {
            destinationX = e.getX();
            destinationY = e.getY();
        }

        @Override
        public void mousePressed(MouseEvent e) {

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
