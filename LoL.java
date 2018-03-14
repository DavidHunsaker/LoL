import logic.Game;
import ui.UiController;

import javax.swing.*;

public class LoL {
    public static void main (String[] args){
        Game game = new Game();
        new UiController(game);
    }
}


