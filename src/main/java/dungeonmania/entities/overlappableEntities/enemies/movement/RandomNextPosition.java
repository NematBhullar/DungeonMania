package dungeonmania.entities.overlappableEntities.enemies.movement;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;

public class RandomNextPosition implements EnemyNextPosition {
    @Override
    public Position nextPosition(GameMap map, Position currentPos, Entity entity) {
        Random randGen = new Random();
        List<Position> pos = currentPos.getCardinallyAdjacentPositions();
        pos = pos
                .stream()
                .filter(p -> map.canMoveTo(entity, p)).collect(Collectors.toList());
        if (pos.size() != 0) {
            return pos.get(randGen.nextInt(pos.size()));
        } else { // If no positions are available to randomise to, then stay on spot
            return currentPos;
        }
    }
}
