package dungeonmania.task2b;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class PersistenceTest {
    @Test
    @Tag("Task2b-iii-1")
    @DisplayName("TestSuccessfulSave")
    public void testSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_PersistenceTest_testSuccess", "c_PersistenceTest_testSuccess");

        // Treasure based stuff
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());

        // Pick up treasure
        res = controller.tick(Direction.LEFT);

        // Get goals
        String goalString = TestUtils.getGoals(res);

        assertFalse(goalString.contains(":treasure"));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // No treasure left on map
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        // Zombie still on map
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast").size());
        // Zombie spawner still on map
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
        // Sword hasnt been picked up
        assertEquals(1, TestUtils.getEntities(res, "sword").size());

        // Get positions
        Position playerPos = TestUtils.getPlayerPos(res);
        Position zombiePosition = TestUtils.getEntityPos(res, "zombie_toast");
        Position zombieSpawnerPosition = TestUtils.getEntityPos(res, "zombie_toast_spawner");
        Position swordPosition = TestUtils.getEntityPos(res, "sword");

        // Get state of inventory, how many treasure are inside
        int numInventoryTreasure = TestUtils.getInventory(res, "treasure").size();

        // Get number of entities
        int numEntities = TestUtils.getEntities(res).size();

        // Save Game
        res = assertDoesNotThrow(() -> controller.saveGame("gameSave"));

        // String path = "bin/main/saves/gameSave";
        String gameSavePath = System.getProperty("user.dir") + "/saves/" + "gameSave";
        File newGameSave = new File(gameSavePath);
        assertTrue(newGameSave.exists());

        // Load Game
        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse res2 = assertDoesNotThrow(() -> controller2.loadGame("gameSave"));

        // Get loaded game goals
        String goalString2 = TestUtils.getGoals(res2);

        // Get positions
        Position playerPos2 = TestUtils.getPlayerPos(res2);
        Position zombiePosition2 = TestUtils.getEntityPos(res2, "zombie_toast");
        Position zombieSpawnerPosition2 = TestUtils.getEntityPos(res2, "zombie_toast_spawner");
        Position swordPosition2 = TestUtils.getEntityPos(res2, "sword");

        // Get state of inventory, how many treasure are inside
        int numInventoryTreasure2 = TestUtils.getInventory(res2, "treasure").size();

        // Get number of entities
        int numEntities2 = TestUtils.getEntities(res2).size();

        // Assert equals
        assertEquals(goalString, goalString2);
        assertEquals(playerPos, playerPos2);
        assertEquals(zombiePosition, zombiePosition2);
        assertEquals(zombieSpawnerPosition, zombieSpawnerPosition2);
        assertEquals(swordPosition, swordPosition2);
        assertEquals(numInventoryTreasure, numInventoryTreasure2);
        assertEquals(numEntities, numEntities2);
    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    @Test
    @Tag("Task2b-iii-2")
    @DisplayName("TestNoDirectory")
    public void testNoDirectory() {
        String path = System.getProperty("user.dir") + "/saves";
        File dir3 = new File(path);

        assertTrue(dir3.exists());

        // Delete directory if exists, also deletes internal stuff
        if (dir3.exists()) {
            deleteDir(dir3);
        }

        // Assert that directory does not exist anymore
        assertFalse(dir3.exists());

        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_PersistenceTest_testNoDirectory",
                "c_PersistenceTest_testNoDirectory");

        // Treasure based stuff
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());

        // Save Game
        res = assertDoesNotThrow(() -> controller.saveGame("gameSave"));

        // assert that directory now exists
        assertTrue(dir3.exists());
    }

    @Test
    @Tag("Task2b-iii-3")
    @DisplayName("TestNoGameSave")
    public void testNoGameSave() {
        String path = System.getProperty("user.dir") + "/saves/" + "bruh";
        File dir3 = new File(path);

        // Confirm that gamesave does not exist
        assertFalse(dir3.exists());

        DungeonManiaController controller = new DungeonManiaController();

        // Should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("bruh"));
    }
}
