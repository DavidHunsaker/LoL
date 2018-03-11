import logic.Game;
import ui.UiController;

import javax.swing.*;

public class LoL {
    public static void main (String[] args){
        Game game = new Game();
        UiController uiController = new UiController(game);

        JFrame frame = new JFrame("LoL");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(uiController.getGameWindow());
        frame.pack();
        frame.setVisible(true);
    }
}


