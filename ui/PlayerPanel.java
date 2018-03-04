package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class PlayerPanel extends JPanel {

    private double uiScale = 1.0;

    private Map<SizeDefinition, Integer> sizeMapping;

    private final Color ENHANCED_STAT_COLOR = new Color(18, 230, 31);
    private final Color NORMAL_STAT_COLOR = new Color(94, 166, 196);
    private final Color LEVEL_COLOR = new Color(243, 233, 235);
    private final Color MONEY_COLOR = new Color(255, 246, 155);
    private final Color EXPERIENCE_COLOR = new Color(255, 157, 46);

    private JLabel level = new JLabel();

    private JLabel attackDamage = new JLabel();
    private JLabel abilityPower = new JLabel();
    private JLabel attackSpeed = new JLabel();
    private JLabel movementSpeed = new JLabel();
    private JLabel armor = new JLabel();
    private JLabel magicResist = new JLabel();

    private ImagePanel[] inventorySlots = new ImagePanel[6];

    private JLabel moneyAmount = new JLabel();
    private JPanel experienceBar = new JPanel();

    public PlayerPanel() {
        this.setBackground(new Color(27, 38, 40));
        this.sizeMapping = createSizeMapping(uiScale);

        loadAndAssignFonts();

        // Need to initialize with a standard for loop since it's an array
        for (int i = 0; i < inventorySlots.length; i++) {
            inventorySlots[i] = new ImagePanel();
        }

        this.add(createLeftPanel());
        this.add(createStatsPanel());
        this.add(createRightPanel());

        initializeTestValues();
    }

    // I'm uncertain on how much I like this approach I took, since it's a bit of typing to get any values out. But it does make
    // scaling the UI rather simple, since the scaling only has to happen in a single location; and because the sizes are defined
    // at run time, it will make it easier to adjust the UI scaling on the fly than if they were defined as constants
    private Map<SizeDefinition, Integer> createSizeMapping(double scalingMultiplier) {
        Map<SizeDefinition, Integer> sizeMapping = new HashMap<>();
        sizeMapping.put(SizeDefinition.ITEM_PANEL, 48);
        sizeMapping.put(SizeDefinition.PORTRAIT, 106);
        sizeMapping.put(SizeDefinition.STAT_ICON, 20);
        sizeMapping.put(SizeDefinition.EXPERIENCE_BAR_HEIGHT, 20);
        sizeMapping.put(SizeDefinition.STAT_FONT, 17);
        sizeMapping.put(SizeDefinition.LEVEL_FONT, 18);
        sizeMapping.put(SizeDefinition.MONEY_FONT, 22);
        sizeMapping.put(SizeDefinition.MONEY_ICON_WIDTH, 50);
        sizeMapping.put(SizeDefinition.MONEY_ICON_HEIGHT, 20);
        sizeMapping.put(SizeDefinition.GAP, 2);

        sizeMapping.forEach((key, value) -> sizeMapping.put(key, (int) (value * scalingMultiplier)));
        return sizeMapping;
    }

    // The panel that holds the character portrait and experience bar
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(createPortraitPanel(), BorderLayout.NORTH);
        leftPanel.setOpaque(false);
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(0, sizeMapping.get(SizeDefinition.GAP)));
        leftPanel.add(spacer);
        leftPanel.add(createExperienceBar(), BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(createInventoryPanel(), BorderLayout.NORTH);
        rightPanel.add(createMoneyPanel(), BorderLayout.SOUTH);

        return rightPanel;
    }

    private void loadAndAssignFonts() {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("ui/assets/interface/myriad-pro-regular.ttf"));
            Font levelFont = baseFont.deriveFont((float) sizeMapping.get(SizeDefinition.LEVEL_FONT));
            Font statFont = baseFont.deriveFont((float) sizeMapping.get(SizeDefinition.STAT_FONT));
            Font moneyFont = baseFont.deriveFont((float) sizeMapping.get(SizeDefinition.MONEY_FONT));

            level.setFont(levelFont);
            attackDamage.setFont(statFont);
            abilityPower.setFont(statFont);
            attackSpeed.setFont(statFont);
            movementSpeed.setFont(statFont);
            armor.setFont(statFont);
            magicResist.setFont(statFont);

            moneyAmount.setFont(moneyFont);
        } catch (Exception e) {
            System.err.println("Could not load in Label statFont");
            e.printStackTrace();
        }
    }

    private JPanel createPortraitPanel() {
        int portraitSize = sizeMapping.get(SizeDefinition.PORTRAIT);
        int levelPaddingSize = sizeMapping.get(SizeDefinition.GAP);

        ImagePanel portraitPanel = new ImagePanel("portraits/teemo.png", portraitSize, portraitSize);
        portraitPanel.setLayout(new BorderLayout());

        JPanel levelPanel = new JPanel();
        levelPanel.setPreferredSize(new Dimension(portraitSize / 3, portraitSize / 4));
        levelPanel.setBackground(Color.BLACK);
        levelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, levelPaddingSize));
        level.setForeground(LEVEL_COLOR);
        levelPanel.add(level);

        JPanel bottomPortrait = new JPanel();
        bottomPortrait.setLayout(new BorderLayout());
        bottomPortrait.setOpaque(false);
        bottomPortrait.add(levelPanel, BorderLayout.EAST);

        portraitPanel.add(bottomPortrait, BorderLayout.SOUTH);

        return portraitPanel;
    }

    private JPanel createExperienceBar() {
        JPanel fullBar = new JPanel();
        fullBar.setBackground(Color.BLACK);
        fullBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        experienceBar.setBackground(EXPERIENCE_COLOR);
        experienceBar.setPreferredSize(new Dimension(0, sizeMapping.get(SizeDefinition.EXPERIENCE_BAR_HEIGHT)));

        fullBar.add(experienceBar);
        return fullBar;
    }

    // experiencePercent is assumed to be a decimal number between 0 and 1. 60% of a way to a level would be 0.6
    private void updateExperienceProgress(double experiencePercent) {
        experienceBar.setPreferredSize(new Dimension((int) (sizeMapping.get(SizeDefinition.PORTRAIT) * experiencePercent), sizeMapping.get(SizeDefinition.EXPERIENCE_BAR_HEIGHT)));
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);

        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(new StatPanel("attack-damage.png", attackDamage));
        statsPanel.add(new StatPanel("ability-power.png", abilityPower));
        statsPanel.add(new StatPanel("attack-speed.png", attackSpeed));
        statsPanel.add(new StatPanel("movement-speed.png", movementSpeed));
        statsPanel.add(new StatPanel("armor.png", armor));
        statsPanel.add(new StatPanel("magic-resist.png", magicResist));

        return statsPanel;
    }

    private class StatPanel extends JPanel {
        StatPanel(String statName, JLabel statValue) {
            int statIconSize = sizeMapping.get(SizeDefinition.STAT_ICON);
            int statGapSize = sizeMapping.get(SizeDefinition.GAP);

            // A box layout would seem to make more sense here, but I was unable to left-justify the labels when I tried using one
            this.setOpaque(false);
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            this.setPreferredSize(new Dimension(statIconSize * 3, statIconSize + statGapSize));

            JPanel spacer = new JPanel();
            spacer.setPreferredSize(new Dimension(4, 0));
            statValue.setHorizontalAlignment(SwingConstants.LEFT);

            ImagePanel icon = new ImagePanel("interface/" + statName, statIconSize, statIconSize);
            this.add(icon);
            this.add(spacer);
            this.add(statValue);
        }
    }

    private JPanel createInventoryPanel() {
        int itemPanelSize = sizeMapping.get(SizeDefinition.ITEM_PANEL);
        int inventoryGap = sizeMapping.get(SizeDefinition.GAP);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setOpaque(false);
        GridLayout layout = new GridLayout(2, 3);
        layout.setHgap(inventoryGap);
        layout.setVgap(inventoryGap);
        inventoryPanel.setLayout(layout);

        for (ImagePanel panel : inventorySlots) {
            panel.setPreferredSize(new Dimension(itemPanelSize, itemPanelSize));
            panel.setBackground(Color.DARK_GRAY);
            inventoryPanel.add(panel);
        }

        return inventoryPanel;
    }

    private JPanel createMoneyPanel() {
        int moneyIconWidth = sizeMapping.get(SizeDefinition.MONEY_ICON_WIDTH);
        int moneyIconHeight = sizeMapping.get(SizeDefinition.MONEY_ICON_HEIGHT);

        JPanel moneyPanel = new JPanel();
        moneyPanel.setOpaque(false);

        ImagePanel coinImage = new ImagePanel("interface/coin-pile.png", moneyIconWidth, moneyIconHeight);
        coinImage.setOpaque(false);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(10, 0));

        moneyPanel.add(coinImage);
        moneyPanel.add(spacer);
        moneyAmount.setForeground(MONEY_COLOR);
        moneyPanel.add(moneyAmount);
        return moneyPanel;
    }

    // This is a junk method that will be deleted when these pull from real values
    private void initializeTestValues() {
        level.setText("5");
        attackDamage.setForeground(ENHANCED_STAT_COLOR);
        attackDamage.setText("84");
        abilityPower.setForeground(NORMAL_STAT_COLOR);
        abilityPower.setText("0");
        attackSpeed.setForeground(ENHANCED_STAT_COLOR);
        attackSpeed.setText("0.95");
        movementSpeed.setForeground(ENHANCED_STAT_COLOR);
        movementSpeed.setText("325");
        armor.setForeground(ENHANCED_STAT_COLOR);
        armor.setText("38");
        magicResist.setForeground(ENHANCED_STAT_COLOR);
        magicResist.setText("35");

        int itemPanelSize = sizeMapping.get(SizeDefinition.ITEM_PANEL);

        inventorySlots[0].setPicture("items/boots-of-speed.png", itemPanelSize, itemPanelSize);
        inventorySlots[1].setPicture("items/boots-of-speed.png", itemPanelSize, itemPanelSize);
        inventorySlots[2].setPicture("items/boots-of-speed.png", itemPanelSize, itemPanelSize);
        inventorySlots[3].setPicture("items/boots-of-speed.png", itemPanelSize, itemPanelSize);

        moneyAmount.setText("371");

        updateExperienceProgress(0.33);
    }

    private enum SizeDefinition {
        ITEM_PANEL, PORTRAIT, EXPERIENCE_BAR_HEIGHT, STAT_ICON, MONEY_ICON_WIDTH, MONEY_ICON_HEIGHT, LEVEL_FONT,
        STAT_FONT, MONEY_FONT, GAP
    }
}
