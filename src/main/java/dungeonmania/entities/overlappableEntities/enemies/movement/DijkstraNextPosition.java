package dungeonmania.entities.overlappableEntities.enemies.movement;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;

public class DijkstraNextPosition implements EnemyNextPosition {
    @Override
    public Position nextPosition(GameMap map, Position currentPos, Entity entity) {
        return map.dijkstraPathFind(currentPos, map.getPlayer().getPosition(), entity);
    }
}
