package com.example;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class LiveWebcamGUI {

    public static void main(String[] args) {
        // Get the first available webcam
        Webcam webcam = Webcam.getWebcams().get(0);
        // Set the webcam resolution to 640x480
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // Create a WebcamPanel for the webcam
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        // Set the panel size to the webcam resolution
        webcamPanel.setPreferredSize(webcam.getViewSize());

        // Create a new panel to display the transformed image
        JPanel transformedPanel = new JPanel();
        transformedPanel.setPreferredSize(webcam.getViewSize());

        // Create a panel to hold the two image panels and the buttons
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Add the webcam panel and the transformed panel to the main panel
        mainPanel.add(webcamPanel, BorderLayout.WEST);
        mainPanel.add(transformedPanel, BorderLayout.EAST);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        // Create four buttons
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");
        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        // Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create a frame to hold the main panel
        JFrame frame = new JFrame("Webcam Viewer");
        // Add the main panel to the frame
        frame.add(mainPanel);
        // Set the size of the frame to fit the panels
        frame.pack();
        // Show the frame
        frame.setVisible(true);
        // Set the default close operation of the frame to exit the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Continuously update the webcam panel
        while (true) {
            // Get the current image from the webcam
            BufferedImage image = webcam.getImage();

            // Create a pixel grabber for the image
            PixelGrabber grabber = new PixelGrabber(image, 0, 0, -1, -1, true);
            // Grab the pixels from the image
            int[] pixels = null;
            try {
                if (grabber.grabPixels()) {
                    pixels = (int[]) grabber.getPixels();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Process the pixels and change the value
            if (pixels != null) {
                for (int i = 0; i < pixels.length; i++) {
                    int pixel = pixels[i];
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    // Increase the value of the red channel by 50
                    red += 100;
                    // Clamp the value of the red channel to the range 0-255
                    red = Math.min(255, Math.max(0, red));
                    // Combine the modified values of the red, green, and blue channels
                    // into a single pixel value
                    pixels[i] = (red << 16) | (green << 8) | blue;
                }
            }

            // Create a new BufferedImage using the modified pixel values
            BufferedImage modifiedImage = new BufferedImage(webcam.getViewSize().width,
                    webcam.getViewSize().height,
                    BufferedImage.TYPE_INT_RGB);

            modifiedImage.setRGB(0, 0, webcam.getViewSize().width,
                    webcam.getViewSize().height, pixels, 0,
                    webcam.getViewSize().width);
            // Set the modified image as the image displayed in the WebcamPanel

            // Get the Graphics object for the transformed panel
            Graphics g = transformedPanel.getGraphics();
            // Draw the BufferedImage on the panel
            g.drawImage(modifiedImage, 0, 0, null);

            // #######################

            modifiedImage = webcam.getImage();

            // Sleep for a short period to allow the webcam to update
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}