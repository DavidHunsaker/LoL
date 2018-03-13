package ui;

import logic.Entity;
import logic.Game;
import ui.components.complete.ScrollPanel;
import ui.components.complete.game.GamePanel;
import ui.components.complete.GameWindow;
import ui.components.complete.gui.GuiPanel;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UiController {

    private final int STARTING_VIEWPORT_WIDTH = 1600;
    private final int STARTING_VIEWPORT_HEIGHT = 900;

    private int scrollOffsetX = 0;
    private int scrollOffsetY = 0;

    private int scrollSpeed = 6;

    private CardinalDirection mouseScrollX = CardinalDirection.NONE;
    private CardinalDirection mouseScrollY = CardinalDirection.NONE;
    private CardinalDirection keyScrollX = CardinalDirection.NONE;
    private CardinalDirection keyScrollY = CardinalDirection.NONE;

    private Game game;
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private ScrollPanel scrollPanel;
    private GuiPanel guiPanel;

    public UiController(Game game) {
        this.game = game;

        gamePanel = new GamePanel(this, game.getPlayer());
        scrollPanel = new ScrollPanel(this);
        guiPanel = new GuiPanel();

        gameWindow = new GameWindow(this, scrollPanel, gamePanel, guiPanel, STARTING_VIEWPORT_WIDTH, STARTING_VIEWPORT_HEIGHT);
        startAnimation();

        // Might make sense to not have the game logic start from the UI. Though this will probably change so much what we do now likely doesn't matter
        game.startGame();
    }

    private void updateUi() {
        scrollOffsetX += mouseScrollX.getDirectionAsInt() * scrollSpeed;
        scrollOffsetY += mouseScrollY.getDirectionAsInt() * scrollSpeed;
        scrollOffsetX += keyScrollX.getDirectionAsInt() * scrollSpeed;
        scrollOffsetY += keyScrollY.getDirectionAsInt() * scrollSpeed;

        gameWindow.repaint();
    }

    private void startAnimation() {
        ScheduledExecutorService uiUpdater = Executors.newScheduledThreadPool(1);
        uiUpdater.scheduleAtFixedRate(this::updateUi, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void setViewportSize(int width, int height) {
        // Because of the way the JLayeredPane works, these have to have their bounds set in this way, rather than using PreferredSize
        scrollPanel.setBounds(0, 0, width, height);
        gamePanel.setBounds(0, 0, width, height);
        guiPanel.setBounds(0, 0, width, height);
    }

    public void setMouseScrollX(CardinalDirection direction) {
        this.mouseScrollX = direction;
    }

    public void setMouseScrollY(CardinalDirection direction) {
        this.mouseScrollY = direction;
    }

    public void setKeyScrollX(CardinalDirection direction) {
        this.keyScrollX = direction;
    }

    public void setKeyScrollY(CardinalDirection direction) {
        this.keyScrollY = direction;
    }

    public List<Entity> getGameEntities() {
        return game.getEntities();
    }

    public int getScrollOffsetX() {
        return scrollOffsetX;
    }

    public int getScrollOffsetY() {
        return scrollOffsetY;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
