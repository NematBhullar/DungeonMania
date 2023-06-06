package dungeonmania.task2b;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SunStoneTest {

    @Test
    @Tag("Task2b-i-1")
    @DisplayName("Test sun stone is picked up by player, added to the player's inventory and removed from the map")
    public void pickUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_pickUp", "c_sunStoneTest_pickUp");

        // Sun stone is created and on the map
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up sun stone and check it is in player's inventory
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-i-2")
    @DisplayName("Test check treasure goal is met with just sun stones")
    public void treasureGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_treasureGoal", "c_sunStoneTest_treasureGoal");

        // Set goals for exit and treasure
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // move player onto exit, assert that treasure goal is not met
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // move player onto sun stone; assert that treasure goal is met
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // move back onto exit; assert all goals are met
        res = dmc.tick(Direction.LEFT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Task2b-i-3")
    @DisplayName("Test sun stone can be used to open a door, check it remains in the inventory")
    public void openDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_openDoor", "c_sunStoneTest_openDoor");

        // Sun stone and door are created, both on the map
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getEntities(res, "door").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Player should be able to open the door and go through
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(pos, TestUtils.getEntities(res, "door").get(0).getPosition());

        // Check that the sun stone remains in inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-i-4")
    @DisplayName("Test sun stone to be used to build a shield, check it remains in the inventory")
    public void buildShield() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_buildShield", "c_sunStoneTest_buildShield");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Sun Stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Wood materials used in construction disappear from inventory, sun stone remains
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-i-5")
    @DisplayName("Test that sun stone cannot be used to bribe mercenaries")
    public void cannotBribeMercenary() {
        //                    Wall        Wall        Wall        Wall
        //       P1       P2/Sun Stone     M2          M1         Wall
        //                    Wall        Wall        Wall        Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_cannotBribeMercenary", "c_sunStoneTest_cannotBribeMercenary");

        // Obtain the mercenary info
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Pick up sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(3, 1), getMercPos(res));

        // Attempt bribe, but mercenary does not accept and sun stone remains in inventory
        assertThrows(InvalidActionException.class, () ->
                dmc.interact(mercId)
        );
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-i-6")
    @DisplayName("Test that sun stone cannot be used to bribe assassins")
    public void cannotBribeAssassin() {
        //                    Wall        Wall        Wall        Wall
        //       P1       P2/Sun Stone     A2          A1         Wall
        //                    Wall        Wall        Wall        Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_cannotBribeAssassin", "c_sunStoneTest_cannotBribeAssassin");

        // Obtain the mercenary info
        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // Pick up sun stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(3, 1), getAssassinPos(res));

        // Attempt bribe, but mercenary does not accept and sun stone remains in inventory
        assertThrows(InvalidActionException.class, () ->
                dmc.interact(assassinId)
        );
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    private Position getAssassinPos(DungeonResponse res) {
        List<EntityResponse> assassins = TestUtils.getEntities(res, "assassin");
        return assassins.get(0).getPosition();
    }

    private Position getMercPos(DungeonResponse res) {
        List<EntityResponse> mercs = TestUtils.getEntities(res, "mercenary");
        return mercs.get(0).getPosition();
    }
}


