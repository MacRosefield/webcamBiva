package com.example;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class LiveWebcamGUI {

    static int buttonActive = 0;
    public static int[] filteredPixels;

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
        JButton button1 = new JButton("Grayscale");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Invert");
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

            // // Process the pixels and change the value
            // if (pixels != null) {
            // for (int i = 0; i < pixels.length; i++) {
            // int pixel = pixels[i];
            // int red = (pixel >> 16) & 0xff;
            // int green = (pixel >> 8) & 0xff;
            // int blue = (pixel) & 0xff;
            // // Increase the value of the red channel by 50
            // red += 100;
            // // Clamp the value of the red channel to the range 0-255
            // red = Math.min(255, Math.max(0, red));
            // // Combine the modified values of the red, green, and blue channels
            // // into a single pixel value
            // pixels[i] = (red << 16) | (green << 8) | blue;
            // }
            // }

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 1;
                }
            });
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 2;
                }
            });
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 3;
                }
            });
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 4;
                }
            });

            switch (buttonActive) {
                case 0:
                    redFilter(pixels);
                    break;
                case 1:
                    grayScale(pixels);
                    break;
                case 2:
                    edgeDetection(pixels, webcam.getViewSize().width, webcam.getViewSize().height);

                    break;
                case 3:
                    invertColors(pixels);
                    break;
                case 4:
                    grayScale(pixels);
                    break;
                default:
                    break;
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

    public static int[] grayScale(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            int average = (red + green + blue) / 3;
            int grayscalePixel = (average << 16) | (average << 8) | average;
            pixels[i] = grayscalePixel;
        }
        return pixels;
    }

    public static int[] redFilter(int[] pixels) {
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
        return pixels;
    }

    public static int[] edgeDetection(int[] pixels, int width, int height) {
        // Create a new array to store the filtered pixels
        filteredPixels = new int[pixels.length];

        // Apply the Sobel operator to each pixel in the image
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // Get the pixel values of the 3x3 neighborhood around the current pixel
                int p1 = pixels[(y - 1) * width + (x - 1)];
                int p2 = pixels[(y - 1) * width + x];
                int p3 = pixels[(y - 1) * width + (x + 1)];
                int p4 = pixels[y * width + (x - 1)];
                int p5 = pixels[y * width + x];
                int p6 = pixels[y * width + (x + 1)];
                int p7 = pixels[(y + 1) * width + (x - 1)];
                int p8 = pixels[(y + 1) * width + x];
                int p9 = pixels[(y + 1) * width + (x + 1)];

                // Calculate the gradient in the x direction
                int gx = (p3 + 2 * p6 + p9) - (p1 + 2 * p4 + p7);
                // Calculate the gradient in the y direction
                int gy = (p1 + 2 * p2 + p3) - (p7 + 2 * p8 + p9);
                // Calculate the gradient magnitude
                int g = (int) Math.sqrt(gx * gx + gy * gy);

                // Clamp the gradient magnitude to the range 0-255
                g = Math.min(255, Math.max(0, g));

                // Set the filtered pixel value to the gradient magnitude
                filteredPixels[y * width + x] = (g << 16) | (g << 8) | g;
            }
        }

        return filteredPixels;

    }

    public static int[] invertColors(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            // Invert the values of the red, green, and blue channels
            red = 255 - red;
            green = 255 - green;
            blue = 255 - blue;
            // Combine the inverted values of the red, green, and blue channels into a
            // single pixel value
            pixels[i] = (red << 16) | (green << 8) | blue;
        }
        return pixels;
    }
}