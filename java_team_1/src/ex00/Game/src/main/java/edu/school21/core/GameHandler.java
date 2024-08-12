package edu.school21.core;

import com.beust.jcommander.ParameterException;
import edu.school21.common.Point;
import edu.school21.service.EntityService;
import edu.school21.render.RenderMap;
import edu.school21.utils.PropertiesUtil;
import lombok.experimental.ExtensionMethod;

import java.util.Scanner;

import static edu.school21.utils.PropertiesUtil.DEVELOPMENT_PROFILE;

@ExtensionMethod(PropertiesUtil.class)
public class GameHandler {
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String CLEAR_SCREEN = "\033[H\033[J";

    private final EntityService service;
    private final Scanner sc = new Scanner(System.in);
    private final String profile;

    public GameHandler(int mapSize, int enemiesCount, int wallsCount, String profile) {
        checkParameters(mapSize, enemiesCount, wallsCount);
        service = new EntityService(mapSize, enemiesCount, wallsCount);
        this.profile = profile;
        profile.loadProperties();
    }

    private static void checkParameters(int mapSize, int enemiesCount, int wallsCount) {
        if (mapSize * mapSize < (enemiesCount + wallsCount + 2)) {
            throw new ParameterException("Not valid");
        }
    }

    public void start() {
        clearConsole();
        render();
        runGameLoop(DEVELOPMENT_PROFILE.equals(profile));
    }

    private void runGameLoop(boolean isDevelopmentMode) {
        while (true) {
            System.out.print(ANSI_GREEN + " -> " + ANSI_RESET);
            String input = sc.next();

            processPlayerMove(input);

            if (isDevelopmentMode) {
                if (handleDevelopmentMode()) {
                    continue;
                }

            } else {
                updateEnemyPositions();
                clearConsole();
            }

            render();
            checkWin();
            checkFail();
        }
    }

    private boolean handleDevelopmentMode() {
        System.out.print(ANSI_MAGENTA + " Enter '8' to continue or any other key to skip enemy movement... " + ANSI_RESET);
        if ("8".equals(sc.next())) {
            updateEnemyPositions();
            return false;
        }
        return true;
    }

    private void processPlayerMove(String direction) {
        direction = direction.toLowerCase();
        switch (direction) {
            case "w":
            case "a":
            case "s":
            case "d":
                service.getPlayer().move(direction, service.getMapSize(), service.getWalls());
                break;
            case "9":
                printGameOver();
                break;
            default:
                System.out.println("Invalid move. Use W, A, S, D, or 9.");
                break;
        }
    }

    private void clearConsole() {
        System.out.print(CLEAR_SCREEN);
    }

    private void printGameOver() {
        System.out.println(ANSI_BOLD + ANSI_RED + "GAME OVER!" + ANSI_RESET);
        System.exit(0);
    }

    private void printWin() {
        System.out.println(ANSI_BOLD + ANSI_MAGENTA + "YOU WIN!" + ANSI_RESET);
        System.exit(0);
    }

    private void checkWin() {
        Point playerPosition = service.getPlayer().getPosition();
        Point targetPosition = service.getTarget().getPosition();

        if (playerPosition.equals(targetPosition)) {
            printWin();
        }
    }

    private void checkFail() {
        Point playerPosition = service.getPlayer().getPosition();

        if (service.getEnemies().stream().anyMatch(e -> e.getPosition().equals(playerPosition))) {
            printGameOver();
        }
    }

    private void updateEnemyPositions() {
        service.updateEnemyPositions();
    }

    private void render() {
        RenderMap.renderMap(service.getMapSize(), service.getEnemies(), service.getWalls(), service.getPlayer(), service.getTarget());
    }
}
