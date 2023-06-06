package dungeonmania.entities;

import java.io.Serializable;

import dungeonmania.entities.overlappableEntities.Player;

public class PlayerState implements Serializable {
    private Player player;
    private boolean isInvincible;
    private boolean isInvisible;

    public PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.player = player;
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };

    public Player getPlayer() {
        return player;
    }

    public void transitionInvisible() {
        isInvisible = isInvisible ? false : true;
        player.changeState(this);
    };

    public void transitionInvincible() {
        isInvincible = isInvincible ? false : true;
        player.changeState(this);
    };

    public void transitionBase() {
        isInvincible = isInvisible = false;
        player.changeState(this);
    }
}
