package ui.components.complete.gui;

import javax.swing.*;
import java.awt.*;

public class GuiPanel extends JPanel {

    public GuiPanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(new PlayerPanel());
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
}
