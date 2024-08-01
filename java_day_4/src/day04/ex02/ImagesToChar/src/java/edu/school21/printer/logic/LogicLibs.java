package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LogicLibs {
    private final String whitePixel;
    private final String blackPixel;

    public LogicLibs(Args args) {
        this.whitePixel = args.getWhiteColor();
        this.blackPixel = args.getBlackColor();
    }


    public void printToConsole() {
        try {
            ColoredPrinter printer = new ColoredPrinter();
            BufferedImage image = ImageIO.read(Objects.requireNonNull(LogicLibs.class.getResource("/resources/it.bmp")));

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int color = image.getRGB(j, i);

                    if (color == Color.BLACK.getRGB()) {
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(blackPixel));
                    } else if (color == Color.WHITE.getRGB()) {
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(whitePixel));
                    }
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
