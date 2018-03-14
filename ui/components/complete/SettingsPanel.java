package ui.components.complete;

import ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SettingsPanel extends JPanel {

    public final int BASE_WIDTH = 400;
    public final int BASE_HEIGHT = 600;

    private final int margin = 50;
    private final int elementGapSize = 20;

    private UiController uiController;

    public SettingsPanel(UiController uiController) {
        this.uiController = uiController;

        JLabel property1 = new JLabel("Fullscreen");
        JLabel property2 = new JLabel("Property 2");
        JLabel property3 = new JLabel("Property 3");

        JCheckBox value1 = new JCheckBox();
        JLabel value2 = new JLabel("Value 2");
        JLabel value3 = new JLabel("Value 3");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        this.setVisible(false);
        this.setBackground(Color.ORANGE);

        value1.addItemListener(e -> uiController.setFullScreen(e.getStateChange() == ItemEvent.SELECTED));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, margin)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(property1)
                                        .addComponent(property2)
                                        .addComponent(property3)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(value1)
                                        .addComponent(value2)
                                        .addComponent(value3)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, margin)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, margin)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(property1)
                                .addComponent(value1)
                        )
                        .addGap(elementGapSize)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(property2)
                                .addComponent(value2)
                        )
                        .addGap(elementGapSize)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(property3)
                                .addComponent(value3)
                        )
        );
    }
}
