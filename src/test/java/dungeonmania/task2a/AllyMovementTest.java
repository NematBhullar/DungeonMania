package dungeonmania.task2a;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllyMovementTest {

    @Test
    @Tag("Task2_aii_1")
    @DisplayName("Test ally not close to player")
    public void testDijkstraStrat() {
        // Make mercenary and interact, check that its strategy is dijkstra
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_AllyMovementTest_testDijkstraStrat",
                "c_AllyMovementTest_alltests");

        String mercId = TestUtils.getEntities(res, "mercenary").get(0).getId();
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "treasure"));

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.countEntityOfTypeInInventory(res, "treasure"));

        // Ally them up
        res = assertDoesNotThrow(() -> controller.interact(mercId));

        Position mercPos = TestUtils.getEntityPos(res, "mercenary");
        Position playerPos = TestUtils.getPlayerPos(res);

        // Takes shortest route to get to player
        assertEquals(3, mercPos.getX());
        // assertTrue(mercPos.getX() == playerPos.getX() + 1);
        assertTrue(mercPos.getY() == playerPos.getY());
    }

    @Test
    @Tag("Task2_aii_3")
    @DisplayName("Test ally is shadowing player")
    public void testShadowingStrat() {
        // Make mercenary next to player, check that strategy is shadowing
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_AllyMovementTest_testShadowingStrat",
                "c_AllyMovementTest_alltests");

        String mercId = TestUtils.getEntities(res, "mercenary").get(0).getId();

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Ally them up
        res = assertDoesNotThrow(() -> controller.interact(mercId));

        // Achieve adjacency
        res = controller.tick(Direction.RIGHT);

        for (int i = 0; i < 3; i++) {
            Position playerPos = TestUtils.getPlayerPos(res);
            res = controller.tick(Direction.RIGHT);
            Position mercPos = TestUtils.getEntityPos(res, "mercenary");
            assertTrue(mercPos.equals(playerPos));
        }
        for (int i = 0; i < 3; i++) {
            Position playerPos = TestUtils.getPlayerPos(res);
            res = controller.tick(Direction.UP);
            Position mercPos = TestUtils.getEntityPos(res, "mercenary");
            mercPos.equals(playerPos);
        }

    }
}
