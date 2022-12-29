package com.example;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class BackgroundSubstract {

    public static void main(String[] args) {
        // Get the first available webcam
        Webcam webcam = Webcam.getWebcams().get(0);
        // Set the webcam resolution to 640x480
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // Create a WebcamPanel for the webcam
        WebcamPanel panel = new WebcamPanel(webcam);
        // Set the panel size to the webcam resolution
        panel.setPreferredSize(webcam.getViewSize());
        // Add the panel to a frame
        JFrame frame = new JFrame("Webcam Background Subtractor");
        // frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new panel to display the transformed image
        JPanel transformedPanel = new JPanel();
        transformedPanel.setPreferredSize(webcam.getViewSize());
        // Add the transformed panel to the frame
        frame.add(transformedPanel);
        frame.pack();

        // Get the first frame from the webcam
        BufferedImage referenceImage = webcam.getImage();

        // Continuously update the webcam panel
        while (true) {
            // Get the current frame from the webcam
            BufferedImage currentImage = webcam.getImage();
            // Create a blank image with the same size as the webcam frame
            BufferedImage foregroundImage = new BufferedImage(currentImage.getWidth(), currentImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // Loop through all the pixels in the current image
            for (int x = 0; x < currentImage.getWidth(); x++) {
                for (int y = 0; y < currentImage.getHeight(); y++) {
                    // Get the current pixel value
                    int currentPixel = currentImage.getRGB(x, y);
                    // Get the corresponding pixel value from the reference image
                    int referencePixel = referenceImage.getRGB(x, y);
                    // Compare the pixel values
                    if (currentPixel != referencePixel) {
                        // Set the pixel value in the foreground image
                        foregroundImage.setRGB(x, y, currentPixel);
                    }
                }
            }
            // Set the foreground image as the new reference image
            referenceImage = foregroundImage;

            // Set the foreground image as the panel image
            Graphics g = transformedPanel.getGraphics();
            // Draw the BufferedImage on the panel
            g.drawImage(referenceImage, 0, 0, null);

            // #######################

            referenceImage = webcam.getImage();
            // Update the panel
            panel.repaint();
        }
    }
}