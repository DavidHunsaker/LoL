import logic.Game;
import ui.UiController;

import javax.swing.*;

public class LoL {
    public static void main (String[] args){
        Game game = new Game();
        JFrame frame = new JFrame("LoL");
        UiController uiController = new UiController(frame, game);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(uiController.getGameWindow());
        frame.pack();
        frame.setVisible(true);
    }
}


