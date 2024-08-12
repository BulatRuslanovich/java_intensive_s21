package edu.school21.model;

import edu.school21.common.Point;
import edu.school21.enums.MapEntityType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MapEntity {
    protected Point position;
    private final MapEntityType type;

    MapEntity(MapEntityType type) {
        position = new Point(0, 0);
        this.type = type;
    }
}
