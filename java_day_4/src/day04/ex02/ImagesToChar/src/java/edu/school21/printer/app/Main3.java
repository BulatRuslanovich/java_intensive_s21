package edu.school21.printer.app;


import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.LogicLibs;

public class Main3 {
    public static void main(String[] args) {
        Args helpClass = new Args();
        JCommander.newBuilder()
                .addObject(helpClass)
                .build()
                .parse(args);

        new LogicLibs(helpClass).printToConsole();
    }
}
