package dungeonmania.goals;

import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;

import java.util.List;

import dungeonmania.Game;

public class ExitGoal implements Goal {
    private String type;

    public ExitGoal() {
        this.type = "exit";
    }

    @Override
    public boolean achieved(Game game) {
        Player character = game.getPlayer();
        if (character == null)
            return false;
        GameMap map = game.getMap();
        Position pos = character.getPosition();
        List<Exit> es = map.getEntities(Exit.class);
        if (es == null || es.size() == 0)
            return false;
        return es
                .stream()
                .map(Entity::getPosition)
                .anyMatch(pos::equals);
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":" + type;
    }
}
