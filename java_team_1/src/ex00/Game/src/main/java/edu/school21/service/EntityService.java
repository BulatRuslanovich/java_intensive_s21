package edu.school21.service;

import edu.school21.common.Point;
import edu.school21.exceptions.NotMapEntityClassException;
import edu.school21.logic.ChaseLogic;
import edu.school21.model.EnemyEntity;
import edu.school21.model.MapEntity;
import edu.school21.model.PlayerEntity;
import edu.school21.model.TargetEntity;
import edu.school21.model.WallEntity;
import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class EntityService {
    @Getter
    private final int mapSize;
    @Getter
    private final Set<EnemyEntity> enemies;
    @Getter
    private final Set<WallEntity> walls;
    @Getter
    private final PlayerEntity player;
    @Getter
    private final TargetEntity target;

    private final Random random;
    private final ChaseLogic logic;


    public EntityService(int mapSize, int enemyCount, int wallCount) {
        this.mapSize = mapSize;
        this.random = new Random();
        this.walls = new HashSet<>();
        this.enemies = new HashSet<>();
        this.player = createPlayer();
        this.target = createTarget();
        createSet(wallCount, WallEntity.class, this.walls);
        createSet(enemyCount, EnemyEntity.class, this.enemies);
        this.logic = new ChaseLogic(mapSize, enemyCount, enemies, walls, player, target);
    }

    private int getRandomCoordinate() {
        return random.nextInt(mapSize);
    }

    private boolean isPositionOccupied(int x, int y) {
        boolean inTarget = false;
        boolean inPlayer = false;

        if (target != null) {
            inTarget = target.getPosition().equals(new Point(x, y));
        }

        if (player != null) {
            inPlayer = player.getPosition().equals(new Point(x, y));
        }

        return walls.contains(new WallEntity(x, y))
                || enemies.contains(new EnemyEntity(x, y))
                || inTarget
                || inPlayer;
    }

    private <T extends MapEntity> T createEntity(Class<T> clazz) {
        int x;
        int y;
        do {
            x = getRandomCoordinate();
            y = getRandomCoordinate();
        } while (isPositionOccupied(x, y));
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            entity.setPosition(new Point(x, y));
            return entity;
        } catch (Exception e) {
            throw new NotMapEntityClassException("Failed to create entity");
        }
    }

    public PlayerEntity createPlayer() {
        return createEntity(PlayerEntity.class);
    }

    public TargetEntity createTarget() {
        return createEntity(TargetEntity.class);
    }

    public <T extends MapEntity> void createSet(int count,
                                    Class<T> clazz, Set<T> points) {

        for (int i = 0; i < count; i++) {
            points.add(createEntity(clazz));
        }
    }

    public void updateEnemyPositions() {
        logic.updateEnemyPositions();
    }
}
