package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;

class Method {

    static int tolerance=100;
    static int tolerance2=100;

    static int getMid(Color color) {
        int value = (int) (((color.getRed() + color.getGreen() + color.getBlue()) / 3) + 0.5);
        return value;
    }

    static Color setColor(Color deleteColor) {
        int red, green, blue, alpha;
        red = deleteColor.getRed();
        green = deleteColor.getGreen();
        blue = deleteColor.getBlue();
        alpha = deleteColor.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    static Color setColorGrey(Color currentColor) {
        int value, alpha;
        value = ((currentColor.getRed() + currentColor.getGreen() + currentColor.getGreen() + currentColor.getBlue()) / 4);
        value = value <= 0 ? 0 : value;
        value = value >= 255 ? 255 : value;
        alpha = currentColor.getAlpha();
        return new Color(value, value, value, alpha);
    }

    static Color getPixelColor(BufferedImage image, int x, int y) {
        int newImage = image.getRGB(x, y);
        int alpha = (newImage >> 24) & 0x0000FF;
        int red = (newImage >> 16) & 0x0000FF;
        int green = (newImage >> 8) & 0x0000FF;
        int blue = (newImage) & 0x0000FF;
        return new Color(red, green, blue, alpha);
    }

    static boolean isWhite(Color currentColor) {
        if ((tolerance <= currentColor.getRed() && currentColor.getRed() <= tolerance2) && (tolerance <= currentColor.getGreen() && currentColor.getGreen() <= tolerance2) && (tolerance <= currentColor.getBlue() && currentColor.getBlue() <= tolerance2)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isRightOnlyWhite(BufferedImage image, int x, int y) {
        int width = image.getWidth();
        for (int i = x, k = 0; i < width; i++, k++) {
            Color currentColor = getPixelColor(image, x + k >= width ? width - 1 : x + k, y);
            if (!isWhite(currentColor)) {
                return false;
            }
        }
        return true;
    }
    static void setTolerance(int newTolerance,int newTolerance2){
        tolerance=newTolerance;
        tolerance2=newTolerance2;
    }

    static int asGray(Color c){
        return (c.getRed()+c.getGreen()+c.getGreen()+c.getBlue())/4;
    }
}