package edu.school21.model;

import edu.school21.enums.MapEntityType;

public class WallEntity extends MapEntity {
    public WallEntity() {
        super(MapEntityType.WALL);
    }

    public WallEntity(int x, int y) {
        this();

        position.setX(x);
        position.setY(y);
    }
}
