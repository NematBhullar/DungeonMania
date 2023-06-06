package dungeonmania.entities.overlappableEntities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.overlappableEntities.Player;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final double DEFAULT_BRIBE_FAIL_RATE = 0.5;

    private double bribeFailRate;

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double bribeFailRate) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeFailRate = bribeFailRate;
    }

    @Override
    public void interact(Player player, Game game) {
        bribe(player);
        if (new Random().nextInt(100) + 1 >= 100 * bribeFailRate) {
            setAllied(true);
        }
    }
}
