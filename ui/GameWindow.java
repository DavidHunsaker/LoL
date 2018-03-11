package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JLayeredPane {

    public GameWindow(UiController uiController, ScrollPanel scrollPanel, GamePanel gamePanel, GuiPanel guiPanel, int startingWidth, int startingHeight) {
        setPreferredSize(new Dimension(startingWidth, startingHeight));
        this.setDoubleBuffered(true);

        this.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Depth 0
        this.add(guiPanel, JLayeredPane.MODAL_LAYER); // Depth 200
        this.add(scrollPanel, JLayeredPane.MODAL_LAYER); // Depth 400

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                uiController.setViewportSize(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
    }

}
