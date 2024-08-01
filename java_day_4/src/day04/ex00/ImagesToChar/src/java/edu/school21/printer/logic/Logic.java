package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
    private final char whitePixel;
    private final char blackPixel;
    private final String filePath;

    public Logic(char whitePixel, char blackPixel, String filePath) {
        this.whitePixel = whitePixel;
        this.blackPixel = blackPixel;
        this.filePath = filePath;
    }


    public void printToConsole() {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int color = image.getRGB(j, i);

                    if (color == Color.BLACK.getRGB()) {
                        System.out.print(blackPixel);
                    } else if (color == Color.WHITE.getRGB()) {
                        System.out.print(whitePixel);
                    }
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
