package ui.components.complete;

import ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Map;

public class SettingsPanel extends JPanel {

    private Map<SizeDefinition, Integer> sizeMapping;

    public SettingsPanel(UiController uiController) {
        sizeMapping = createSizeMapping(uiController.getUiScale());
        int margin = sizeMapping.get(SizeDefinition.MARGIN);
        Font font = uiController.getBaseFont().deriveFont((float) sizeMapping.get(SizeDefinition.FONT));

        List<Component> settingDescriptions = new ArrayList<>();
        List<Component> settingOptions = new ArrayList<>();

        settingDescriptions.add(new JLabel("Fullscreen"));
        settingDescriptions.add(new JLabel("Property 2"));
        settingDescriptions.add(new JLabel("Property 3"));

        JCheckBox value1 = new JCheckBox();
        value1.addItemListener(e -> uiController.setFullScreen(e.getStateChange() == ItemEvent.SELECTED));

        settingOptions.add(value1);
        settingOptions.add(new JLabel("Value 2"));
        settingOptions.add(new JLabel("Value 3"));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        this.setVisible(false);
        this.setBackground(Color.ORANGE);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup descriptionColumn = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        settingDescriptions.forEach(settingLabel -> {
            settingLabel.setFont(font);
            descriptionColumn.addComponent(settingLabel);
        });
        GroupLayout.ParallelGroup optionsColumn = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        settingOptions.forEach(settingComponent -> {
            settingComponent.setFont(font);
            optionsColumn.addComponent(settingComponent);
        });

        GroupLayout.SequentialGroup optionRows = layout.createSequentialGroup();
        optionRows.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, margin);

        for (int i = 0; i < settingDescriptions.size(); i++) {
            optionRows.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(settingDescriptions.get(i))
                    .addComponent(settingOptions.get(i)));
            optionRows.addGap(sizeMapping.get(SizeDefinition.ELEMENT_GAP_SIZE));
        }

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, margin)
                        .addGroup(descriptionColumn)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(optionsColumn)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, margin));

        layout.setVerticalGroup(optionRows);
    }

    private Map<SizeDefinition, Integer> createSizeMapping(double scalingMultiplier) {
        Map<SizeDefinition, Integer> sizeMapping = new HashMap<>();
        sizeMapping.put(SizeDefinition.MODAL_WIDTH, 350);
        sizeMapping.put(SizeDefinition.MODAL_HEIGHT, 450);
        sizeMapping.put(SizeDefinition.MARGIN, 50);
        sizeMapping.put(SizeDefinition.ELEMENT_GAP_SIZE, 20);
        sizeMapping.put(SizeDefinition.FONT, 22);

        sizeMapping.forEach((key, value) -> sizeMapping.put(key, (int) (value * scalingMultiplier)));
        return sizeMapping;
    }

    public int getWidth() {
        return sizeMapping.get(SizeDefinition.MODAL_WIDTH);
    }

    public int getHeight() {
        return sizeMapping.get(SizeDefinition.MODAL_HEIGHT);
    }

    private enum SizeDefinition {
        MODAL_WIDTH, MODAL_HEIGHT, MARGIN, ELEMENT_GAP_SIZE, FONT
    }

}
