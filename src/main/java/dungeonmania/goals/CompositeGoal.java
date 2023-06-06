package dungeonmania.goals;

import dungeonmania.Game;

public class CompositeGoal implements Goal {
    private String type;
    private Goal goal1;
    private Goal goal2;

    public CompositeGoal(String type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean achieved(Game game) {
        switch (type) {
            case "AND":
                return goal1.achieved(game) && goal2.achieved(game);
            case "OR":
                return goal1.achieved(game) || goal2.achieved(game);
            default:
                return false;
        }
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        switch (type) {
            case "AND":
                return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
            case "OR":
                if (achieved(game))
                    return "";
                else
                    return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
            default:
                return ":" + type;
        }
    }

}
