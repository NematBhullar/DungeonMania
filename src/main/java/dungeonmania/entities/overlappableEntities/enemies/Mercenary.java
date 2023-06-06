package dungeonmania.entities.overlappableEntities.enemies;

// import java.util.List;
// import java.util.Random;
// import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.entities.overlappableEntities.collectables.SunStone;
import dungeonmania.entities.overlappableEntities.collectables.Treasure;
import dungeonmania.entities.overlappableEntities.enemies.movement.DijkstraNextPosition;
import dungeonmania.entities.overlappableEntities.enemies.movement.NextPosController;
import dungeonmania.entities.overlappableEntities.enemies.movement.ShadowingNextPosition;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;
    private boolean sceptreControlled = false;

    private boolean shadowing = false;

    private NextPosController nextPosController = new NextPosController();

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        nextPosController.setEnemyMovement(new DijkstraNextPosition());
    }

    public boolean isSceptreControlled() {
        return sceptreControlled;
    }

    public void setSceptreControl(boolean sceptreControlled) {
        this.sceptreControlled = sceptreControlled;
    }

    public void setAllied(boolean allied) {
        this.allied = allied;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied || sceptreControlled)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     *
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        Integer treasures = player.countEntityOfType(Treasure.class);
        Integer sunStones = player.countEntityOfType(SunStone.class);
        return bribeRadius >= 0 && (treasures - sunStones) >= bribeAmount && !sceptreControlled;
    }

    /**
     * bribe the merc
     */
    public void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.useTreasure();
        }
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
    }

    @Override
    public void move(GameMap map) {
        Player player = map.getPlayer();
        map.moveTo(this, nextPosController.position(map, getPosition(), this));
        if (Position.isAdjacent(getPosition(), player.getPosition())
                && !shadowing && allied) {
            nextPosController.setEnemyMovement(new ShadowingNextPosition());
            shadowing = true;
        }
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

}
