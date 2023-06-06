package dungeonmania.entities.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.entities.overlappableEntities.collectables.Arrow;
import dungeonmania.entities.overlappableEntities.collectables.Key;
import dungeonmania.entities.overlappableEntities.collectables.SunStone;
import dungeonmania.entities.overlappableEntities.collectables.Sword;
import dungeonmania.entities.overlappableEntities.collectables.Treasure;
import dungeonmania.entities.overlappableEntities.collectables.Wood;
import dungeonmania.entities.overlappableEntities.enemies.ZombieToast;
import dungeonmania.map.GameMap;

public class Inventory implements Serializable {
    private List<InventoryItem> items = new ArrayList<>();

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables(GameMap map) {

        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int keys = count(Key.class);
        int sunStones = count(SunStone.class);
        int swords = count(Sword.class);
        int zombies = map.getEntities(ZombieToast.class).size();
        List<String> result = new ArrayList<>();

        if (checkBowBuildable(wood, arrows)) {
            result.add("bow");
        }
        if (checkShieldBuildable(wood, treasure, keys)) {
            result.add("shield");
        }
        if (checkMidnightArmourBuildable(sunStones, swords, zombies)) {
            result.add("midnight_armour");
        }
        if (checkSceptreBuildable(wood, arrows, treasure, sunStones, keys)) {
            result.add("sceptre");
        }
        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove,
            String entity, EntityFactory factory, GameMap map) {
        List<ZombieToast> zombies = map.getEntities(ZombieToast.class);
        List<Wood> wood = getEntities(Wood.class);
        List<Arrow> arrows = getEntities(Arrow.class);
        List<Treasure> treasure = getEntities(Treasure.class);
        List<SunStone> sunStones = getEntities(SunStone.class);
        List<Key> keys = getEntities(Key.class);
        List<Sword> swords = getEntities(Sword.class);

        if (checkBowBuildable(wood.size(), arrows.size()) && entity.equals("bow")) {
            if (remove) {
                removeBowBuildables(wood, arrows);
            }
            return factory.buildBow();
        }

        if (checkShieldBuildable(wood.size(), treasure.size(), keys.size()) && entity.equals("shield")) {
            if (remove) {
                removeShieldBuildables(wood, treasure, keys);
            }
            return factory.buildShield();
        }

        if (checkMidnightArmourBuildable(sunStones.size(), swords.size(), zombies.size())
                && entity.equals("midnight_armour")) {
            if (remove) {
                removeMidnightArmourBuildables(sunStones, swords);
            }
            return factory.buildMidnightArmour();
        }

        if (checkSceptreBuildable(wood.size(), arrows.size(), treasure.size(), sunStones.size(), keys.size())
                && entity.equals("sceptre")) {
            if (remove) {
                removeSceptreBuildables(wood, arrows, treasure, keys, sunStones);
            }
            return factory.buildSceptre();
        }
        return null;
    }

    public boolean checkBowBuildable(int wood, int arrows) {
        return wood >= 1 && arrows >= 3;
    }

    public void removeBowBuildables(List<Wood> wood, List<Arrow> arrows) {
        items.remove(wood.get(0));
        items.remove(arrows.get(0));
        items.remove(arrows.get(1));
        items.remove(arrows.get(2));
    }

    public boolean checkShieldBuildable(int wood, int treasure, int keys) {
        return wood >= 2 && (treasure >= 1 || keys >= 1);
    }

    public void removeShieldBuildables(List<Wood> wood, List<Treasure> treasure, List<Key> keys) {
        items.remove(wood.get(0));
        items.remove(wood.get(1));
        if (treasure.size() >= 1) {
            removeTreasureItem(treasure);
        } else {
            items.remove(keys.get(0));
        }
    }

    public boolean checkSceptreBuildable(int wood, int arrows, int treasure, int sunStones, int keys) {
        return (wood >= 1 || arrows >= 2) && (keys >= 1 || treasure >= 2) && sunStones >= 1;
    }

    public void removeSceptreBuildables(List<Wood> wood, List<Arrow> arrows,
            List<Treasure> treasure, List<Key> keys, List<SunStone> sunStones) {
        // First requirement: wood or arrow
        if (wood.size() >= 1) {
            items.remove(wood.get(0));
        } else {
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
        }
        // Second requirement: treasure or keys
        if (keys.size() >= 1) {
            items.remove(keys.get(0));
        } else {
            removeTreasureItem(treasure);
        }
        // Third requirement: sun stone
        items.remove(sunStones.get(0));
    }

    public boolean checkMidnightArmourBuildable(int sunStones, int swords, int zombies) {
        return sunStones >= 1 && swords >= 1 && zombies == 0;
    }

    public void removeMidnightArmourBuildables(List<SunStone> sunStones, List<Sword> swords) {
        items.remove(sunStones.get(0));
        items.remove(swords.get(0));
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item)) return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item)) count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId)) return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

    public void removeTreasureItem(List<Treasure> treasure) {
        for (Integer i = 0; i < treasure.size(); i++) {
            if (!(treasure.get(i) instanceof SunStone)) {
                items.remove(treasure.get(i));
                break;
            }
        }
    }
}
