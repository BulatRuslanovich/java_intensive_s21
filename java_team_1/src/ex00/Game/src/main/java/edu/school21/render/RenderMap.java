package edu.school21.render;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.model.EnemyEntity;
import edu.school21.model.PlayerEntity;
import edu.school21.model.TargetEntity;
import edu.school21.model.WallEntity;
import edu.school21.utils.PropertiesUtil;

import java.util.Set;

public final class RenderMap {
    private static final ColoredPrinter PRINTER = new ColoredPrinter();
    private static final String EMPTY_CHAR = PropertiesUtil.getProperty("empty.char");
    private static final String EMPTY_COLOR = PropertiesUtil.getProperty("empty.color");
    private static final String WALL_CHAR = PropertiesUtil.getProperty("wall.char");
    private static final String WALL_COLOR = PropertiesUtil.getProperty("wall.color");
    private static final String ENEMY_CHAR = PropertiesUtil.getProperty("enemy.char");
    private static final String ENEMY_COLOR = PropertiesUtil.getProperty("enemy.color");
    private static final String PLAYER_CHAR = PropertiesUtil.getProperty("player.char");
    private static final String PLAYER_COLOR = PropertiesUtil.getProperty("player.color");
    private static final String TARGET_CHAR = PropertiesUtil.getProperty("target.char");
    private static final String TARGET_COLOR = PropertiesUtil.getProperty("target.color");

    private RenderMap() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void renderMap(int fieldSize,
                                 Set<EnemyEntity> enemiesSet,
                                 Set<WallEntity> wallsSet,
                                 PlayerEntity player,
                                 TargetEntity target) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                String cellChar = EMPTY_CHAR;
                String backColor = EMPTY_COLOR;

                if (wallsSet.contains(new WallEntity(j, i))) {
                    cellChar = WALL_CHAR;
                    backColor = WALL_COLOR;
                } else if (enemiesSet.contains(new EnemyEntity(j, i))) {
                    cellChar = ENEMY_CHAR;
                    backColor = ENEMY_COLOR;
                } else if (player.getPosition().getX() == j
                        && player.getPosition().getY() == i) {
                    cellChar = PLAYER_CHAR;
                    backColor = PLAYER_COLOR;
                } else if (target.getPosition().getX() == j
                        && target.getPosition().getY() == i) {
                    cellChar = TARGET_CHAR;
                    backColor = TARGET_COLOR;
                }

                PRINTER.print(cellChar, Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(backColor));
            }

            System.out.println();
        }
    }
}
