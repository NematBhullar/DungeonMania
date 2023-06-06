package dungeonmania.entities.overlappableEntities;

import dungeonmania.map.GameMap;
import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.overlappableEntities.collectables.Key;
import dungeonmania.entities.overlappableEntities.collectables.SunStone;
import dungeonmania.entities.overlappableEntities.enemies.Spider;
import dungeonmania.util.Position;

public class Door extends OverlappableEntity {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player && (hasKey((Player) entity) || hasSunStone((Player) entity)));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        if (hasKey(player)) {
            inventory.remove(key);
            open();
        } else if (hasSunStone(player)) {
            open();
        }
    }

    private boolean hasKey(Player player) {
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        return (key != null && key.getnumber() == number);
    }

    private boolean hasSunStone(Player player) {
        Inventory inventory = player.getInventory();
        SunStone sunStone = inventory.getFirst(SunStone.class);

        return (sunStone != null);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }
}
