package edu.school21;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.core.GameHandler;
import edu.school21.exceptions.IllegalParametersException;
import edu.school21.utils.ArgsUtil;

public class ConsoleGameApplication {
    private static final String ERROR_MESSAGE = "Error: invalid parameters passed to the method";

    public static void main(String[] args) {
        try {
            ArgsUtil argsUtil = new ArgsUtil();
            JCommander commander = new JCommander(argsUtil);
            commander.parse(args);
            GameHandler game = new GameHandler(argsUtil.getMapSize(),
                    argsUtil.getEnemiesCount(),
                    argsUtil.getWallsCount(),
                    argsUtil.getProfile());
            game.start();
        } catch (ParameterException e) {
            throw new IllegalParametersException(ERROR_MESSAGE);
        }
    }
}
