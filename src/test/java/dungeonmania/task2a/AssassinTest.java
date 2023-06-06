package dungeonmania.task2a;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssassinTest {
    @Test
    @Tag("Task2_aiii_1")
    @DisplayName("tests that bribes can fail, still hostile and treasure used will be wasted")
    public void testBribeFail() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_AssassinTest_testBribeFail",
                "c_AssassinTest_testBribeFail");

        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Bribe attempt
        res = assertDoesNotThrow(() -> controller.interact(assId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Battles since failed allied
        assertEquals(1, res.getBattles().size());

    }

    @Test
    @Tag("Task2_aiii_2")
    @DisplayName("tests thats battles wont occur when the player is invisible")
    public void testNoInvisibleBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_AssassinTest_testNoInvisibleBattle",
                "c_AssassinTest_testNoInvisibleBattle");

        String potionId = TestUtils.getEntities(res, "invisibility_potion").get(0).getId();

        assertEquals(1, TestUtils.getEntities(res, "invisibility_potion").size());
        assertEquals(0, TestUtils.getInventory(res, "invisibility_potion").size());

        // Pick up Potion
        res = controller.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "invisibility_potion").size());
        assertEquals(1, TestUtils.getInventory(res, "invisibility_potion").size());

        // consume invisibility_potion
        res = assertDoesNotThrow(() -> controller.tick(potionId));

        res = controller.tick(Direction.NONE);

        // No battles since invisible
        assertEquals(0, res.getBattles().size());
    }

    @Test
    @Tag("Task2_aiii_3")
    @DisplayName("Assassian becomes an ally")
    public void testBribeSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_AssassinTest_testBribeSuccess",
                "c_AssassinTest_testBribeSuccess");

        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        res = assertDoesNotThrow(() -> controller.interact(assId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }
}
