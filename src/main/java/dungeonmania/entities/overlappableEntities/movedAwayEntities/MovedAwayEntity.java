package dungeonmania.entities.overlappableEntities.movedAwayEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.overlappableEntities.OverlappableEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class MovedAwayEntity extends OverlappableEntity {
    public MovedAwayEntity(Position position) {
        super(position);
    }

    public abstract void onMovedAway(GameMap map, Entity entity);
}
