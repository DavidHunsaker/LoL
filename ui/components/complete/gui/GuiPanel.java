package ui.components.complete.gui;

import ui.UiController;

import javax.swing.*;
import java.awt.*;

public class GuiPanel extends JPanel {

    public GuiPanel(UiController uiController) {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(new PlayerPanel(uiController.getUiScale(), uiController.getBaseFont()));
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
}
