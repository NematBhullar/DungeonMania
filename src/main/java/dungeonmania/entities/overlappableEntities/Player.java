package dungeonmania.entities.overlappableEntities;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.PlayerState;
import dungeonmania.entities.buildables.MidnightArmour;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.overlappableEntities.collectables.Bomb;
import dungeonmania.entities.overlappableEntities.collectables.SunStone;
import dungeonmania.entities.overlappableEntities.collectables.Treasure;
import dungeonmania.entities.overlappableEntities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.overlappableEntities.collectables.potions.Potion;
import dungeonmania.entities.overlappableEntities.enemies.Enemy;
import dungeonmania.entities.overlappableEntities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends OverlappableEntity implements Battleable {
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 5.0;
    private BattleStatistics battleStatistics;
    private Inventory inventory;
    private Queue<Potion> queue = new LinkedList<>();
    private Potion inEffective = null;
    private int nextTrigger = 0;
    private int enemyKills = 0;
    private Sceptre inEffectSceptre = null;
    private int endTriggerSceptre = 0;

    private PlayerState state;

    public Player(Position position, double health, double attack) {
        super(position);
        battleStatistics = new BattleStatistics(
                health,
                attack,
                0,
                BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_PLAYER_DAMAGE_REDUCER);
        inventory = new Inventory();
        state = new PlayerState(this, false, false); // Base-state is both non invincible, non invisible
    }

    public void incrementEnemyKills() {
        enemyKills++;
    }

    public int getEnemyKills() {
        return enemyKills;
    }

    public boolean hasWeapon() {
        return inventory.hasWeapon();
    }

    public BattleItem getWeapon() {
        return inventory.getWeapon();
    }

    public double getHealth() {
        return battleStatistics.getHealth();
    }

    public List<String> getBuildables(GameMap map) {
        return inventory.getBuildables(map);
    }

    public boolean build(String entity, EntityFactory factory, GameMap map) {
        InventoryItem item = inventory.checkBuildCriteria(this, true, entity, factory, map);
        if (item == null)
            return false;
        return inventory.add(item);
    }

    public void move(GameMap map, Direction direction) {
        this.setFacing(direction);
        map.moveTo(this, Position.translateBy(this.getPosition(), direction));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            map.getGame().battle(this, (Enemy) entity);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public Entity getEntity(String itemUsedId) {
        return inventory.getEntity(itemUsedId);
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return inventory.getEntities(clz);
    }

    public boolean pickUp(Entity item) {
        return inventory.add((InventoryItem) item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public <T extends InventoryItem> void use(Class<T> itemType) {
        T item = inventory.getFirst(itemType);
        if (item != null)
            inventory.remove(item);
    }

    public void useTreasure() {
        List<Treasure> treasure = inventory.getEntities(Treasure.class);
        for (Integer i = 0; i < treasure.size(); i++) {
            if (!(treasure.get(i) instanceof SunStone)) {
                inventory.remove(treasure.get(i));
                break;
            }
        }
    }

    // Bomb Implementation
    public void use(Bomb bomb, GameMap map) {
        inventory.remove(bomb);
        bomb.onPutDown(map, getPosition());
    }

    public void use(MidnightArmour midnightArmour) {
        inventory.getFirst(MidnightArmour.class);
    }

    // Potion Implementation
    public Potion getEffectivePotion() {
        return inEffective;
    }

    public void triggerNext(int currentTick) {
        if (queue.isEmpty()) {
            inEffective = null;
            state.transitionBase();
            return;
        }
        inEffective = queue.remove();
        if (inEffective instanceof InvincibilityPotion) {
            state.transitionInvincible();
        } else {
E        }
        nextTrigger = currentTick + inEffective.getDuration();
    }

    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    public void use(Potion potion, int tick) {
        inventory.remove(potion);
        queue.add(potion);
        if (inEffective == null) {
            triggerNext(tick);
        }
    }

    public void onTick(int tick) {
        if (inEffective == null || tick == nextTrigger) {
            triggerNext(tick);
        }
    }

    // Sceptre Implementation
    public Sceptre getEffectiveSceptre() {
        return inEffectSceptre;
    }

    public void use(Sceptre sceptre, int tick, GameMap map) {
        remove(sceptre);
        inEffectSceptre = sceptre;
        endTriggerSceptre = tick + inEffectSceptre.getDuration();
        setSceptreControl(map, true);
    }

    public void onTickSceptre(int tick, GameMap map) {
        if (tick == endTriggerSceptre) {
            setSceptreControl(map, false);
            inEffectSceptre = null;
        }
    }

    public void setSceptreControl(GameMap map, boolean sceptreControl) {
        map.getEntities(Mercenary.class).stream().forEach(mercenary -> {
            mercenary.setSceptreControl(sceptreControl);
        });
    }

    public void remove(InventoryItem item) {
        inventory.remove(item);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public <T extends InventoryItem> int countEntityOfType(Class<T> itemType) {
        return inventory.count(itemType);
    }

    public BattleStatistics applyBuff(BattleStatistics origin) {
        if (state.isInvincible()) {
            return BattleStatistics.applyBuff(origin, new BattleStatistics(
                    0,
                    0,
                    0,
                    1,
                    1,
                    true,
                    true));
        } else if (state.isInvisible()) {
            return BattleStatistics.applyBuff(origin, new BattleStatistics(
                    0,
                    0,
                    0,
                    1,
                    1,
                    false,
                    false));
        }
        return origin;
    }
}
