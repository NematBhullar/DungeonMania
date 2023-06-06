package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        String goal = jsonGoal.getString("goal");
        switch (goal) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new CompositeGoal(
                "AND",
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new CompositeGoal(
                "OR",
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new TreasureGoal(treasureGoal);
        case "exit":
            return new ExitGoal();
        case "boulders":
            return new BoulderGoal();
        case "enemies":
            int enemiesGoal = config.optInt("enemy_goal", 2);
            return new DestroyESGoal(enemiesGoal);
        default:
            return null;
        }
    }
}
