package dungeonmania.entities.overlappableEntities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends DestroyableEntity implements Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        game.getEntityFactory().spawnZombie(game, this);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }

    @Override
    public void interact(Player player, Game game) {
        player.getInventory().getWeapon().use(game);
        GameMap map = game.getMap();
        map.destroyEntity(this);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        return;
    }
}
