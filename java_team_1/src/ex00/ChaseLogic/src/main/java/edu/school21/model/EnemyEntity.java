package edu.school21.model;

import edu.school21.enums.MapEntityType;

public class EnemyEntity extends MapEntity {
    public EnemyEntity() {
        super(MapEntityType.ENEMY);
    }

    public EnemyEntity(int x, int y) {
        this();
        position.setX(x);
        position.setY(y);
    }
}
