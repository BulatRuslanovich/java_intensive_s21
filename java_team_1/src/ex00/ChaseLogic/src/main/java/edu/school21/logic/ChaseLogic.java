package edu.school21.logic;

import edu.school21.common.Point;
import edu.school21.enums.Direction;
import edu.school21.model.EnemyEntity;
import edu.school21.model.MapEntity;
import edu.school21.model.PlayerEntity;
import edu.school21.model.TargetEntity;
import edu.school21.model.WallEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static edu.school21.enums.Direction.HORIZONTAL;
import static edu.school21.enums.Direction.VERTICAL;


public class ChaseLogic {
    private final int mapSize;
    private final Direction[] enemyDirections;
    private final Set<EnemyEntity> enemiesSet;
    private final Set<WallEntity> wallsSet;
    private final PlayerEntity player;
    private final TargetEntity target;

    public ChaseLogic(int mapSize,
                      int enemiesCount,
                      Set<EnemyEntity> enemiesSet,
                      Set<WallEntity> wallsSet,
                      PlayerEntity player,
                      TargetEntity target) {
        this.mapSize = mapSize;
        this.enemyDirections = new Direction[enemiesCount];
        this.enemiesSet = enemiesSet;
        this.wallsSet = wallsSet;
        this.player = player;
        this.target = target;
        initDirections();
    }

    public void updateEnemyPositions() {
        List<EnemyEntity> enemies = new ArrayList<>(enemiesSet);

        for (int enemyIndex = 0; enemyIndex < enemiesSet.size(); enemyIndex++) {
            enemiesSet.remove(enemies.get(enemyIndex));
            EnemyEntity newEnemyPosition = tryMoveEnemy(enemyIndex, enemies.get(enemyIndex));
            enemiesSet.add(newEnemyPosition);
        }
    }

    private void initDirections() {
        Arrays.fill(enemyDirections, HORIZONTAL);
    }

    private boolean detectCollision(MapEntity entity) {
        Point pos = entity.getPosition();
        int x = pos.getX();
        int y = pos.getY();

        return enemiesSet.contains(new EnemyEntity(x, y))
                || wallsSet.contains(new WallEntity(x, y))
                || pos.equals(target.getPosition())
                || x < 0 || x >= mapSize
                || y < 0 || y >= mapSize;
    }

    private EnemyEntity tryMoveEnemy(int enemyIndex,
                                     EnemyEntity enemy) {
        for (int attempt = 0; attempt < 2; attempt++) {
            EnemyEntity newEnemyPosition = calculateNewEnemyPosition(enemyIndex, enemy);

            if (!detectCollision(newEnemyPosition)) {
                return newEnemyPosition;
            }
        }
        return enemy;
    }

    private EnemyEntity calculateNewEnemyPosition(int enemyIndex, EnemyEntity currentEnemy) {
        Point position = currentEnemy.getPosition();
        int x = position.getX();
        int y = position.getY();
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();


        if (HORIZONTAL.equals(enemyDirections[enemyIndex])) {
            x += (playerX >= x) ? 1 : -1;
        } else {
            y += (playerY >= y) ? 1 : -1;
        }

        toggleDirection(enemyIndex);
        return new EnemyEntity(x, y);
    }

    private void toggleDirection(int enemyIndex) {
        enemyDirections[enemyIndex] = (enemyDirections[enemyIndex] == HORIZONTAL) ? VERTICAL : HORIZONTAL;
    }
}
