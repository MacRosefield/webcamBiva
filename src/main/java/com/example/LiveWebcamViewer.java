package com.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

public class LiveWebcamViewer {

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
        JFrame frame = new JFrame("Webcam Viewer");
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
            // // Process the pixels (e.g., print the RGB values to the console)
            // if (pixels != null) {
            // for (int pixel : pixels) {
            // int red = (pixel >> 16) & 0xff;
            // int green = (pixel >> 8) & 0xff;
            // int blue = (pixel) & 0xff;
            // System.out.println("RGB: " + red + "," + green + "," + blue);
            // }
            // }

            // Process the pixels and change the value
            if (pixels != null) {
                for (int i = 0; i < pixels.length; i++) {
                    int pixel = pixels[i];
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    // Increase the value of the red channel by 50
                    red += 200;
                    // Clamp the value of the red channel to the range 0-255
                    red = Math.min(255, Math.max(0, red));
                    // Combine the modified values of the red, green, and blue channels
                    // into a single pixel value
                    pixels[i] = (red << 16) | (green << 8) | blue;
                }
            }

            // #######################

            // Create a new BufferedImage using the modified pixel values
            BufferedImage modifiedImage = new BufferedImage(webcam.getViewSize().width, webcam.getViewSize().height,
                    BufferedImage.TYPE_INT_RGB);

            modifiedImage.setRGB(0, 0, webcam.getViewSize().width, webcam.getViewSize().height, pixels, 0,
                    webcam.getViewSize().width);
            // Set the modified image as the image displayed in the WebcamPanel

            // Get the Graphics object for the panel
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
