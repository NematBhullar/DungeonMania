package dungeonmania.entities.overlappableEntities.enemies;

import dungeonmania.entities.overlappableEntities.enemies.movement.EnemyNextPosition;
import dungeonmania.entities.overlappableEntities.enemies.movement.NextPosController;
import dungeonmania.entities.overlappableEntities.enemies.movement.RandomNextPosition;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;
    private NextPosController nextPosController = new NextPosController();

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        setMovement(new RandomNextPosition());
    }

    private void setMovement(EnemyNextPosition movementType) {
        nextPosController.setEnemyMovement(movementType);
    }

    @Override
    public void move(GameMap map) {
        map.moveTo(this, nextPosController.position(map, getPosition(), this));
    }
}
