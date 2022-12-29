package com.example;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
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
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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
            // Process the pixels (e.g., print the RGB values to the console)
            if (pixels != null) {
                for (int pixel : pixels) {
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    System.out.println("RGB: " + red + "," + green + "," + blue);
                }
            }
            // Sleep for a short period to allow the webcam to update
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
