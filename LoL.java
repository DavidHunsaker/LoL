import javax.swing.*;

public class LoL {
    public static void main (String[] args){
        Game game = new Game();

        JFrame frame = new JFrame("LoL");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GameWindow(game));
        frame.pack();
        frame.setVisible(true);
    }
}


