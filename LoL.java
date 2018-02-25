import javax.swing.*;

public class LoL {
    public static void main (String[] args){



        JFrame frame = new JFrame("LoL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Graphic());
        frame.pack();
        frame.setVisible(true);

    }
}


