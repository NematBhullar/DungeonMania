package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.entities.overlappableEntities.enemies.ZombieToastSpawner;
import dungeonmania.map.GameMap;

public class DestroyESGoal implements Goal {
    private String type;
    private int enemiesGoal;

    public DestroyESGoal(int enemiesGoal) {
        this.type = "enemies";
        this.enemiesGoal = enemiesGoal;
    }

    @Override
    public boolean achieved(Game game) {
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        List<ZombieToastSpawner> numSpawners = map.getEntities(ZombieToastSpawner.class);
        return numSpawners.size() == 0 && player.getEnemyKills() >= enemiesGoal;
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":" + type;
    }
}
