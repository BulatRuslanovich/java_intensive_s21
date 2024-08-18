package edu.school21.classes.impl;

import edu.school21.classes.Printer;
import edu.school21.classes.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer render;

    public PrinterWithDateTimeImpl(Renderer render) {
        this.render = render;
    }

    @Override
    public void print(String message) {
        render.render(LocalDateTime.now() + " " + message);
    }
}
