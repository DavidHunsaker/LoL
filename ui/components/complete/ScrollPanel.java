package ui.components.complete;

import ui.CardinalDirection;
import ui.UiController;
import ui.listeners.MousePresenceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ScrollPanel extends JPanel {
    private final int SCROLL_AREA_SIZE = 10;

    public ScrollPanel(UiController uiController) {
        JPanel scrollLeft = createTransparentJPanel();
        JPanel scrollUp = createTransparentJPanel();
        JPanel scrollRight = createTransparentJPanel();
        JPanel scrollDown = createTransparentJPanel();
        JPanel scrollUpLeft = createTransparentJPanel();
        JPanel scrollUpRight = createTransparentJPanel();
        JPanel scrollDownLeft = createTransparentJPanel();
        JPanel scrollDownRight = createTransparentJPanel();

        scrollUp.setPreferredSize(new Dimension(0, SCROLL_AREA_SIZE));
        scrollUpLeft.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, SCROLL_AREA_SIZE));
        scrollUpRight.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, SCROLL_AREA_SIZE));

        scrollDown.setPreferredSize(new Dimension(0, SCROLL_AREA_SIZE));
        scrollDownLeft.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, SCROLL_AREA_SIZE));
        scrollDownRight.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, SCROLL_AREA_SIZE));

        scrollLeft.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, 0));
        scrollRight.setPreferredSize(new Dimension(SCROLL_AREA_SIZE, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        topPanel.add(scrollUp, BorderLayout.CENTER);
        topPanel.add(scrollUpLeft, BorderLayout.WEST);
        topPanel.add(scrollUpRight, BorderLayout.EAST);

        bottomPanel.add(scrollDown, BorderLayout.CENTER);
        bottomPanel.add(scrollDownLeft, BorderLayout.WEST);
        bottomPanel.add(scrollDownRight, BorderLayout.EAST);

        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        scrollLeft.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollX(CardinalDirection.LEFT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        scrollUp.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.UP);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
            }
        });

        scrollRight.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollX(CardinalDirection.RIGHT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        scrollDown.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.DOWN);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
            }
        });

        scrollUpLeft.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.UP);
                uiController.setMouseScrollX(CardinalDirection.LEFT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        scrollUpRight.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.UP);
                uiController.setMouseScrollX(CardinalDirection.RIGHT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        scrollDownLeft.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.DOWN);
                uiController.setMouseScrollX(CardinalDirection.LEFT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        scrollDownRight.addMouseListener(new MousePresenceListener() {
            public void mouseEntered(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.DOWN);
                uiController.setMouseScrollX(CardinalDirection.RIGHT);
            }
            public void mouseExited(MouseEvent e) {
                uiController.setMouseScrollY(CardinalDirection.NONE);
                uiController.setMouseScrollX(CardinalDirection.NONE);
            }
        });

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(scrollLeft, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollRight, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTransparentJPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setOpaque(false);

        return jPanel;
    }
}
