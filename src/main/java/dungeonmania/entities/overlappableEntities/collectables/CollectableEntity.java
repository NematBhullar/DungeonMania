package dungeonmania.entities.overlappableEntities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.overlappableEntities.OverlappableEntity;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class CollectableEntity extends OverlappableEntity implements InventoryItem {
    public CollectableEntity(Position position) {
        super(position);
    }

    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this)) return;
            map.destroyEntity(this);
        }
    }
}
