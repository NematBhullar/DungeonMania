package dungeonmania.task2a;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DestroyESGoalTest {

    @Test
    @Tag("Task2_ai_1")
    @DisplayName("Test fail when not enough enemies are destroyed but no spawners left")
    public void testSomeEnemiesNoSpawners() {
        // 2 enemy goal, 2 enemies, 0 destroyed, no spawners
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testSomeEnemiesNoSpawners",
                "c_DestroyESGoal_testSomeEnemiesNoSpawners");
        List<EntityResponse> entities = res.getEntities();

        assertTrue(TestUtils.countEntityOfType(entities, "zombie_toast") == 2);
        assertNotEquals("", TestUtils.getGoals(res));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("Task2_ai_2")
    @DisplayName("Test fail when enough enemies destroyed, but spawners are left")
    public void testEnoughEnemiesSomeSpawners() {
        // 2 enemy goal, 2 enemies, 2 destroyed, 1 spawner
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testEnoughEnemiesSomeSpawners",
                "c_DestroyESGoal_testEnoughEnemiesSomeSpawners");

        // Initial enemies
        assertTrue(TestUtils.countEntityOfType(res.getEntities(), "zombie_toast") == 2);
        assertTrue(TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner") == 1);

        // DESTROY THEM ENEMIESSSSSS
        for (int i = 0; i < 3; i++) {
            res = controller.tick(Direction.RIGHT);
            int battlesHeld = res.getBattles().size();
            if (battlesHeld != 0) {
                assertEquals(2 - battlesHeld, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));
            }
        }

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("Task2_ai_3")
    @DisplayName("Test fail when not enough enemies destroyed, spawners left")
    public void testNeither() {
        // 2 enemy goal, 2 enemies, none destroyed, 1 spawner left
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testNeither", "c_DestroyESGoal_testNeither");

        // Initial enemies
        assertTrue(TestUtils.countEntityOfType(res.getEntities(), "zombie_toast") == 2);
        assertTrue(TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner") == 1);

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("Task2_ai_4")
    @DisplayName("Test success when more than enough enemies destroyed, no spawners left")
    public void testMoreThanEnoughEnemiesNoSpawners() {
        // 1 enemy goal, 3 enemies, 2 destroyed, no spawners
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testMoreThanEnoughEnemiesNoSpawners",
                "c_DestroyESGoal_testMoreThanEnoughEnemiesNoSpawners");

        // DESTROY THEM ENEMIESSSSSS
        for (int i = 0; i < 3; i++) {
            res = controller.tick(Direction.RIGHT);
            int battlesHeld = res.getBattles().size();
            if (battlesHeld != 0) {
                assertEquals(3 - battlesHeld, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));
            }
        }

        res = controller.tick(Direction.LEFT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2_ai_5")
    @DisplayName("Test success when just enough enemies have been destroyed, no spawners are left")
    public void testSingleGoal() {
        // 2 enemy goal, 3 enemies, 2 destroyed, 1 spawner, 1 spawner destroyed
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testSingleGoal", "c_DestroyESGoal_testSingleGoal");

        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // DESTROY THEM ENEMIESSSSSS
        for (int i = 0; i < 4; i++) {
            res = controller.tick(Direction.RIGHT);
            int battlesHeld = res.getBattles().size();
            if (battlesHeld != 0) {
                assertEquals(3 - battlesHeld, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));
            }
        }

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner"));

        // Destroy spawner
        res = assertDoesNotThrow(() -> controller.interact(spawnerId));

        // Spawner should be gone
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner"));

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2_ai_6")
    @DisplayName("Test success when enemies destroyed, no spawners left then need to head to exit")
    public void testExitLast() {
        // 1 enemy goal, 1 enemies, 1 destroyed, 1 spawners, 1 destroyed
        // Then exit, check that exiting before destroying doesnt do anything
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testExitLast", "c_DestroyESGoal_testExitLast");

        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // Try exiting first, should still have both
        res = controller.tick(Direction.LEFT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        // Move back
        res = controller.tick(Direction.RIGHT);

        // No more zombie
        res = controller.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));

        // Pick up sword
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Destroy spawner
        res = assertDoesNotThrow(() -> controller.interact(spawnerId));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner"));

        // Move to Exit
        for (int i = 0; i < 3; i++) {
            res = controller.tick(Direction.LEFT);
        }

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2_ai_7")
    @DisplayName("Test success when DestroyESGoal and treasure")
    public void testConjunction() {
        // 1 enemy goal, 1 enemies, 1 destroyed, 1 spawners, 1 destroyed
        // And boulders then check goals
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testConjunction", "c_DestroyESGoal_testConjunction");

        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // Get treasure
        res = controller.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Move back
        res = controller.tick(Direction.RIGHT);

        // No more zombie
        res = controller.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));

        // Pick up sword
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Destroy spawner
        res = assertDoesNotThrow(() -> controller.interact(spawnerId));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner"));

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2_ai_8")
    @DisplayName("Test success when DestroyESGoal or treasure")
    public void testDisjunction() {
        // 1 enemy goal, 1 enemies, 1 destroyed, 1 spawners, 1 destroyed
        // Or treasure, check that can win with treasure first
        // Getting treasure first
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testDisjunction", "c_DestroyESGoal_testDisjunction");

        // Get treasure
        res = controller.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2_ai_9")
    @DisplayName("Test success when DestroyESGoal or treasure")
    public void testDisjunction2() {
        // 1 enemy goal, 1 enemies, 1 destroyed, 1 spawners, 1 destroyed
        // Or treasure, check that can win with treasure first
        // Killing enemies first
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_DestroyESGoal_testDisjunction", "c_DestroyESGoal_testDisjunction");

        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // No more zombie
        res = controller.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast"));

        // Pick up sword
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Destroy spawner
        res = assertDoesNotThrow(() -> controller.interact(spawnerId));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "zombie_toast_spawner"));

        assertEquals("", TestUtils.getGoals(res));
    }
}
