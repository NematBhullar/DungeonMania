package dungeonmania.task2b;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.*;
import dungeonmania.mvp.TestUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SceptreTest {
    @Test
    @Tag("Task2b-ii-1")
    @DisplayName("Test sceptre can be created with 1 wood + 1 key + 1 sun stone")
    public void buildSceptreTest1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest1", "c_sceptreTest_buildSceptreTest1");

        // Items are created and on the map
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Player moves picks up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Player moves picks up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // All items disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-2")
    @DisplayName("Test sceptre can be created with 2 arrows + 1 key + 1 sun stone")
    public void buildSceptreTest2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest2", "c_sceptreTest_buildSceptreTest2");

        // Sun stone is created and on the map
        assertEquals(2, TestUtils.getEntities(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "arrow").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Player moves picks up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Player moves picks up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // All items disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-3")
    @DisplayName("Test sceptre can be created with 1 wood + 1 treasure + 1 sun stone")
    public void buildSceptreTest3() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest3", "c_sceptreTest_buildSceptreTest3");

        // Items are created and on the map
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Player moves picks up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Player moves picks up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // All disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-4")
    @DisplayName("Test sceptre can be created with 2 arrows + 1 treasure + 1 sun stone")
    public void buildSceptreTest4() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest4", "c_sceptreTest_buildSceptreTest4");

        // Sun stone is created and on the map
        assertEquals(2, TestUtils.getEntities(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "arrow").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Player moves picks up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Player moves picks up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // All items disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-5")
    @DisplayName("Test sceptre can be created with 2 arrow + 2 sun stones")
    public void buildSceptreTest5() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest5", "c_sceptreTest_buildSceptreTest5");

        // Sun stone is created and on the map
        assertEquals(2, TestUtils.getEntities(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());

        assertEquals(2, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up arrows
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "arrow").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Player moves picks up sunstones
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Arrows and 1 sun stone disappear from inventory, 1 sun stone remains
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-6")
    @DisplayName("Test sceptre can be created with 1 wood + 2 sun stone")
    public void buildSceptreTest6() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptreTest6", "c_sceptreTest_buildSceptreTest6");

        // Items are created and on the map
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());

        assertEquals(2, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Player moves picks up wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Player moves picks up sunstones
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Wood and 1 sun stone disappear from inventory, 1 sun stone remains
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("Task2b-ii-7")
    @DisplayName("Testing a mercenary does not need to be bribed")
    public void mercenaryNotBribed() throws IllegalArgumentException, InvalidActionException {
        // Wall Wall Wall Wall Wall Wall Wall
        // P1 P2/Wood P3/Key P4/Sun Stone P5/Treasure P6 M6 M5 M4 M3 M2 M1 Wall
        // Wall Wall Wall Wall Wall Wall Wall

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mercenaryNotBribed",
                "c_sceptreTest_mercenaryNotBribed");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Obtain entities for spectre
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());

        // Player moves picks up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Player must interact with mercenary
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));

        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getMercPos(res));

        // Attempt bribe, but mercenary does not accept
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("Task2b-ii-8")
    @DisplayName("Testing a mercenary must be bribed once spectre effectiveness wears off after 5 ticks")
    public void mercenaryBribed() throws IllegalArgumentException, InvalidActionException {

        // P1 P2/Wood P3/Key P4/SunStone/P5 P6/Treasure P7 P8 P9 M9 M8 M7 M6 M5 M4 M3 M2
        // M1

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mercenaryBribed",
                "c_sceptreTest_mercenaryBribed");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Entities for spectre
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());

        // Player moves picks up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Player must interact with mercenary, sceptre gets used
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));

        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(12, 1), getMercPos(res));

        // Attempt bribe, but mercenary is allied and does not accept
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Meet the mercenary
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(9, 1), getMercPos(res));

        // Mercenary accepts bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("Task2b-ii-9")
    @DisplayName("Testing an assassin does not need to be bribed")
    public void assassinNotBribed() throws IllegalArgumentException, InvalidActionException {
        // Wall Wall Wall Wall Wall Wall Wall
        // P1 P2/Wood P3/Key P4/Sun Stone P5/Treasure P6 A6 A5 A4 A3 A2 A1 Wall
        // Wall Wall Wall Wall Wall Wall Wall

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_assassinNotBribed",
                "c_sceptreTest_assassinNotBribed");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // Obtain entities for spectre
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());

        // Player moves picks up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Player must interact with assassin
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));

        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssassinPos(res));

        // Attempt bribe, but assassin does not accept
        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("Task2b-ii-10")
    @DisplayName("Testing an assassin must be bribed once spectre effectiveness wears off after 5 ticks")
    public void assassinBribed() throws IllegalArgumentException, InvalidActionException {

        // P1 P2/Wood P3/Key P4/SunStone/P5 P6/Treasure P7 P8 P9 A9 A8 A7 A6 A5 A4 A3 A2
        // A1

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_assassinBribed",
                "c_sceptreTest_assassinBribed");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // Entities for spectre
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());

        // Player moves picks up items
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Spectre is created
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Player must interact with assassin, sceptre gets used
        res = dmc.tick(TestUtils.getFirstItemId(res, "sceptre"));

        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(12, 1), getAssassinPos(res));

        // Attempt bribe, but mercenary is allied and does not accept
        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Meet the mercenary
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(9, 1), getAssassinPos(res));

        // Assassin accepts bribe
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    private Position getMercPos(DungeonResponse res) {
        List<EntityResponse> mercs = TestUtils.getEntities(res, "mercenary");
        return mercs.get(0).getPosition();
    }

    private Position getAssassinPos(DungeonResponse res) {
        List<EntityResponse> assassins = TestUtils.getEntities(res, "assassin");
        return assassins.get(0).getPosition();
    }
}
