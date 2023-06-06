package dungeonmania.task2b;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
// import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
// import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MidnightArmourTest {
    @Test
    @Tag("Task2b-i-ma2")
    @DisplayName("Test midnight armour is created when there are no zombies around")
    public void buildItem() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildItem",
            "c_midnightArmourTest_buildItem");

        // Sun stone is created and on the map
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "zombie_toast").size());

        // Sword is created and on the map
        assertEquals(1, TestUtils.getEntities(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sword").size());

        // Pick up item and check they are in player's inventory
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Build Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("Task2b-i-ma3")
    @DisplayName("Test midnight armour is not created when there are zombies around")
    public void buildItemWithZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildItemWithZombies",
            "c_midnightArmourTest_buildItem");

        // Sun stone is created and on the map
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast").size());

        // Sword is created and on the map
        assertEquals(1, TestUtils.getEntities(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sword").size());

        // Pick up item and check they are in player's inventory
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Build Armour
        assertThrows(InvalidActionException.class, () ->
                dmc.build("midnight_armour")
        );
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
    }
}
