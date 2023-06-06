package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.overlappableEntities.movedAwayEntities.Switch;
import dungeonmania.map.GameMap;

public class BoulderGoal implements Goal {
    private String type;

    public BoulderGoal() {
        this.type = "boulders";
    }

    @Override
    public boolean achieved(Game game) {
        GameMap map = game.getMap();
        return map.getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":" + type;
    }
}
