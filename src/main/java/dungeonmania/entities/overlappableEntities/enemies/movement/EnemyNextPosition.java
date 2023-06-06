package dungeonmania.entities.overlappableEntities.enemies.movement;

import dungeonmania.util.Position;

import java.io.Serializable;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface EnemyNextPosition extends Serializable {
    /**
     * Returns the enemy's next Position according to chosen strategy
     * @param map
     * @param currentPos
     * @param entity
     */
    public Position nextPosition(GameMap map, Position currentPos, Entity entity);
}
