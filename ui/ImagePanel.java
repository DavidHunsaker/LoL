package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image picture;
    private String assetRoot = "src/ui/assets/";

    ImagePanel() { }

    ImagePanel(String imagePath, int width, int height) {
        setPicture(imagePath, width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (picture != null) {
            g.drawImage(picture, 0, 0, this);
        }
    }

    public void setPicture(String imagePath, int width, int height) {
        // Preempt programmer errors by removing the leading slash if it is provided.
        // Otherwise we might end up with double slashes like "ui/assets//portraits.teemo.png"
        if (imagePath.charAt(0) == '/') {
            imagePath = imagePath.substring(1);
        }
        String assetPath = assetRoot + imagePath;
        try {
            Image fullImage = ImageIO.read(new File(assetPath));
            this.picture = fullImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            this.setPreferredSize(new Dimension(width, height));
        } catch (IOException e) {
            System.err.println("Exception when reading in image using path: " + assetPath);
            e.printStackTrace();
        }
    }
}
