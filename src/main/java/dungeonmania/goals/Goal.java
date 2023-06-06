package dungeonmania.goals;

import java.io.Serializable;

import dungeonmania.Game;

public interface Goal extends Serializable {
    public boolean achieved(Game game);
    public String toString(Game game);
}
