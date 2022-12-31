package com.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class Test {
    public static void main(String[] args) {
        // Get the default webcam
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        // Open the webcam
        webcam.open();

        // Set the frame rate to 30 FPS
        //webcam.setFPS(30);

        // Create a WebcamPanel to display the webcam video
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);

        // Add the panel to a JFrame
        JFrame frame = new JFrame("Webcam");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // Continuously capture and process images from the webcam
        while (true) {
            // Get the current image from the webcam
            BufferedImage image = webcam.getImage();

            // Loop through all the pixels in the image
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get the pixel at (x, y)
                    int pixel = image.getRGB(x, y);

                    // Change the pixel color to blue
                    pixel = new Color(0, 0, 255).getRGB();

                    // Set the pixel color in the image
                    image.setRGB(x, y, pixel);
                }
            }

            // Update the panel with the new image
            //panel.set(image);
        }
    }
}
