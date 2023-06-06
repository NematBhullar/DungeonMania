package dungeonmania.entities.overlappableEntities.enemies.movement;

import dungeonmania.util.Position;

import java.io.Serializable;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class NextPosController implements Serializable {
    private EnemyNextPosition enemyNextPos;

    public void setEnemyMovement(EnemyNextPosition enemyNextPos) {
        this.enemyNextPos = enemyNextPos;
    }

    public Position position(GameMap map, Position currentPos, Entity entity) {
        return enemyNextPos.nextPosition(map, currentPos, entity);
    }
}
