package dungeonmania.entities.overlappableEntities.enemies.movement;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.entities.overlappableEntities.Player;

public class ShadowingNextPosition implements EnemyNextPosition {
    @Override
    public Position nextPosition(GameMap map, Position currentPos, Entity entity) {
        Player player = map.getPlayer();
        return player.getPreviousPosition();
    }
}
