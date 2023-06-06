package dungeonmania.entities.overlappableEntities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.overlappableEntities.OverlappableEntity;
import dungeonmania.util.Position;

public abstract class DestroyableEntity extends OverlappableEntity {
    public DestroyableEntity(Position position) {
        super(position);
    }

    public void onDestroy(Game game) {
        game.unsubscribe(getId());
    }
}
