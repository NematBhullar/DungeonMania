package dungeonmania.entities.overlappableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class OverlappableEntity extends Entity {
    public OverlappableEntity(Position position) {
        super(position);
    }

    public abstract void onOverlap(GameMap map, Entity entity);
}
