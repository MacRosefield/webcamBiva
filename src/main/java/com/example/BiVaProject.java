package com.example;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class BiVaProject {

    static int buttonActive = 0;

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

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1));

        // Create four buttons
        JButton button1 = new JButton("red");
        JButton button2 = new JButton("grayscale");
        JButton button3 = new JButton("Invert");
        JButton button4 = new JButton("eightBitColor");
        JButton button5 = new JButton("pixeled");
        JButton button6 = new JButton("cartoon");
        JButton button7 = new JButton("sketch");
        JButton button8 = new JButton("blur");
        JButton button9 = new JButton("removeBackground");
        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(button5);
        buttonPanel.add(button6);
        buttonPanel.add(button7);
        buttonPanel.add(button8);
        buttonPanel.add(button9);
        // Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(transformedPanel, BorderLayout.EAST);

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
            button5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 5;
                }
            });
            button6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 6;
                }
            });
            button7.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 7;
                }
            });

            button8.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 8;
                }
            });
            button9.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // Code to be executed when the button is clicked
                    buttonActive = 9;
                }
            });

            switch (buttonActive) {
                case 1:
                    redFilter(pixels);
                    break;
                case 2:
                    grayScale(pixels);

                    // edgeDetection(pixels, webcam.getViewSize().width,
                    // webcam.getViewSize().height);
                    break;
                case 3:
                    invertColors(pixels);
                    break;
                case 4:
                    eightBitColor(pixels);

                    break;
                case 5:
                    pixeled(pixels, image, webcam.getViewSize().width, webcam.getViewSize().height);


                    break;
                case 6:
                    cartoon(pixels, image, webcam.getViewSize().width, webcam.getViewSize().height);

                    break;
                case 7:
                    sketch(pixels, image, webcam.getViewSize().width, webcam.getViewSize().height);

                    break;
                case 8:
                    blur(pixels, image, webcam.getViewSize().width, webcam.getViewSize().height);
                    break;
                case 9:
                    removeBackground(pixels, image, webcam.getViewSize().width, webcam.getViewSize().height);
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
            // Sleep for a short period to allow the webcam to update
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }

    public static void grayScale(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            int average = (red + green + blue) / 3;
            int grayscalePixel = (average << 16) | (average << 8) | average;
            pixels[i] = grayscalePixel;
        }
    }

    public static void redFilter(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            // Increase the value of the red channel by 50
            red += 100;
            // Clamp the value of the red channel to the range 0-255
            red = Math.min(255, red);
            // Combine the modified values of the red, green, and blue channels
            // into a single pixel value
            pixels[i] = (red << 16) | (green << 8) | blue;
        }
    }

  /*  public static int[] edgeDetection(int[] pixels, int width, int height) {
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

    }*/

    public static void invertColors(int[] pixels) {
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
    }

    public static void removeBackground(int[] pixels, BufferedImage img, int width, int height) {
        int tolerance = 20;

        Color lastColor;
        Color futureColor;
        Color lastColor2;
        Color futureColor2;

        for (int y = 0; y < height - 1; y++) {
            //tolerance=(int)(Math.abs(Method.asGray(Method.getPixelColor(img, 0, y)) - Method.asGray(Method.getPixelColor(img, width-1, y))));
            int startPixel = 0;
            int endPixel = 0;
            for (int x = 0; x < width - 1; x++) {
                Color currentColor = Method.getPixelColor(img, x, y);
                if (x == 0) {
                    for (int i = 1; i < width - 1; i++) {
                        currentColor = Method.getPixelColor(img, i, y);
                        lastColor = Method.getPixelColor(img, Math.max(i - 1, 0), y);
                        futureColor = Method.getPixelColor(img, Math.min(i + 1, width - 1), y);
                        lastColor2 = Method.getPixelColor(img, i, Math.max(y - 1, 0));
                        futureColor2 = Method.getPixelColor(img, i, Math.min(y + 1, height - 1));
                        if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor)) > tolerance) {
                            //if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance) {
                            startPixel = i + (y * (width));
                            break;
                        }
                    }
                    for (int i = width - 2; i > 0; i--) {
                        currentColor = Method.getPixelColor(img, i, y);
                        lastColor = Method.getPixelColor(img, Math.max(i - 1, 0), y);
                        futureColor = Method.getPixelColor(img, Math.min(i + 1, width - 1), y);
                        lastColor2 = Method.getPixelColor(img, i, Math.max(y - 1, 0));
                        futureColor2 = Method.getPixelColor(img, i, Math.min(y + 1, height - 1));
                        if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor)) > tolerance) {
                            //if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance) {
                            endPixel = i + (y * (width));
                            break;
                        }
                    }
                }

                if (!((x + (y * width)) >= startPixel && (x + (y * width)) < endPixel)) {
                    currentColor = Color.white;
                }
                int newIntColor = (currentColor.getAlpha() << 24) | (currentColor.getRed() << 16)
                        | (currentColor.getGreen() << 8) | currentColor.getBlue();
                pixels[x + (y * width)] = newIntColor;
            }
        }

    }

    public static void pixeled(int[] pixels, BufferedImage img, int width, int height) {
        int pixelSize = 10;
        Color currentColor = new Color(0, 0, 0);
        for (int y = 0; y < height; y++) {
            if (y % pixelSize == 0) {
                for (int x = 0; x < width; x++) {
                    if (x % pixelSize == 0) {
                        currentColor = Method.getPixelColor(img,
                                x + (pixelSize / 2) >= width ? width - 1 : x + (pixelSize / 2),
                                y + (pixelSize / 2) >= height ? height - 1 : y + (pixelSize / 2));
                    }

                    int newIntColor = (currentColor.getAlpha() << 24) | (currentColor.getRed() << 16)
                            | (currentColor.getGreen() << 8) | currentColor.getBlue();
                    pixels[x + (y * width)] = newIntColor;
                }
            } else {
                for (int x = 0; x < width; x++) {
                    pixels[x + (y * width)] = pixels[x + ((y - 1) * width)];
                }
            }
        }
    }

    public static void cartoon(int[] pixels, BufferedImage img, int width, int height) {
        int tolerance = 20;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Color currentColor = Method.getPixelColor(img, x, y);
                Color lastColor = Method.getPixelColor(img, x - 1, y);
                Color futureColor = Method.getPixelColor(img, x + 1, y);
                Color lastColor2 = Method.getPixelColor(img, x, y - 1);
                Color futureColor2 = Method.getPixelColor(img, x, y + 1);

                if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor)) > tolerance) {
                    currentColor = Color.black;
                }


                int newIntColor = (currentColor.getAlpha() << 24) | (currentColor.getRed() << 16)
                        | (currentColor.getGreen() << 8) | currentColor.getBlue();
                pixels[x + (y * width)] = newIntColor;
            }

        }
    }

    public static void sketch(int[] pixels, BufferedImage img, int width, int height) {
        int tolerance = 10;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Color currentColor;
                Color lastColor = Method.getPixelColor(img, x - 1, y);
                Color futureColor = Method.getPixelColor(img, x + 1, y);
                Color lastColor2 = Method.getPixelColor(img, x, y - 1);
                Color futureColor2 = Method.getPixelColor(img, x, y + 1);

                if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor2)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor)) > tolerance) {
                    //if (Math.abs(Method.asGray(lastColor) - Method.asGray(futureColor)) > tolerance || Math.abs(Method.asGray(lastColor2) - Method.asGray(futureColor2)) > tolerance) {
                    // currentColor = new Color((lastColor.getRed() + futureColor.getRed()) / 2,
                    // (lastColor.getGreen() + futureColor.getGreen()) / 2, (lastColor.getBlue() +
                    // futureColor.getBlue()) / 2);
                    currentColor = Color.black;
                } else {
                    currentColor = Color.white;
                }

                int newIntColor = (currentColor.getAlpha() << 24) | (currentColor.getRed() << 16)
                        | (currentColor.getGreen() << 8) | currentColor.getBlue();
                pixels[x + (y * width)] = newIntColor;
            }

        }
    }

    public static void blur(int[] pixels, BufferedImage img, int width, int height) {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int radius = 5;
                int left = x > radius ? x - radius : x;
                int right = x < width - radius - 1 ? x + radius : x;
                int top = y > radius ? y - radius : y;
                int bottom = y < height - radius - 1 ? y + radius : y;

                // average
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                int count = 0;
                for (int i = left; i <= right; i++) {
                    for (int j = top; j <= bottom; j++) {
                        int pixel = img.getRGB(i, j);
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = pixel & 0xff;
                        sumR += r;
                        sumG += g;
                        sumB += b;
                        count++;
                    }
                }
                int avgR = sumR / count;
                int avgG = sumG / count;
                int avgB = sumB / count;

                int newPixel = (avgR << 16) | (avgG << 8) | avgB;
                pixels[x + (y * width)] = newPixel;
            }
        }
    }

    public static void eightBitColor(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;
            red = red < 128 ? 0 : 255;
            green = green < 128 ? 0 : 255;
            blue = blue < 128 ? 0 : 255;

            Color currentColor = new Color(green, blue, red);

            int newIntColor = (currentColor.getAlpha() << 24) | (currentColor.getRed() << 16)
                    | (currentColor.getGreen() << 8) | currentColor.getBlue();
            pixels[i] = newIntColor;
        }


    }
}
