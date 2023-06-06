package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.overlappableEntities.collectables.SunStone;
import dungeonmania.entities.overlappableEntities.collectables.Treasure;
import dungeonmania.map.GameMap;

public class TreasureGoal implements Goal {
    private String type;
    private int target;

    public TreasureGoal(int target) {
        this.type = "treasure";
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        GameMap map = game.getMap();
        Integer treasures = map.getEntities(Treasure.class).size();
        Integer sunStones = map.getEntities(SunStone.class).size();
        return game.getInitialTreasureCount() - (treasures + sunStones) >= target;
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":" + type;
    }
}
