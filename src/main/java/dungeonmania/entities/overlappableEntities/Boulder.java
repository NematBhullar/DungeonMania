package dungeonmania.entities.overlappableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.overlappableEntities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends OverlappableEntity {

    public Boulder(Position position) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Spider) return false;
        if (entity instanceof Player && canPush(map, entity.getFacing())) return true;
        return false;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            map.moveTo(this, entity.getFacing());
        }
    }

    private boolean canPush(GameMap map, Direction direction) {
        Position newPosition = Position.translateBy(this.getPosition(), direction);
        for (Entity e : map.getEntities(newPosition)) {
            if (!e.canMoveOnto(map, this)) return false;
        }
        return true;
    }
}
