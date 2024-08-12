package edu.school21.model;

import edu.school21.common.Point;
import edu.school21.enums.MapEntityType;

import java.util.Set;

public class PlayerEntity extends MapEntity {
    public PlayerEntity() {
        super(MapEntityType.PLAYER);
    }

    public void move(String move, int fieldSize, Set<WallEntity> walls) {

        Point point = this.getPosition();
        int originalX = point.getX();
        int originalY = point.getY();

        switch (move) {
            case "w":
                point.setY(originalY - 1);
                break;
            case "s":
                point.setY(originalY + 1);
                break;
            case "a":
                point.setX(originalX - 1);
                break;
            case "d":
                point.setX(originalX + 1);
                break;
            default:
                break;
        }

        if (walls.contains(new WallEntity(point.getX(), point.getY()))
                || point.getX() < 0
                || point.getX() >= fieldSize
                || point.getY() < 0
                || point.getY() >= fieldSize) {
            point.setX(originalX);
            point.setY(originalY);
        }
    }
}
