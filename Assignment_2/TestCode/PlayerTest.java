import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Red");
    }

    // Checks is player starts with correct number of points
    @Test
    void testInitialVictoryPoints() {
        assertEquals(2, player.getVictoryPoints(), "Player should start with 2 victory points");
    }

    // Confirms that adding victory points updates the total correctly.
    @Test
    void testAddVictoryPoints() {
        player.addVictoryPoints(3);
        assertEquals(5, player.getVictoryPoints(), "Victory points should be incremented correctly");
    }

    // Ensures that added resources are correctly tracked and can be checked.
    @Test
    void testAddAndCheckResources() {
        player.addResources(ResourceType.BRICK);
        assertTrue(player.hasResource(ResourceType.BRICK), "Player should have BRICK after adding it");
        assertFalse(player.hasResource(ResourceType.LUMBER), "Player should not have LUMBER");
    }

    // Validates that building a road removes the required resources.
    @Test
    void testBuildRoadRemovesResources() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);
        player.buildRoad();
        assertFalse(player.hasResource(ResourceType.BRICK), "BRICK should be removed after building road");
        assertFalse(player.hasResource(ResourceType.LUMBER), "LUMBER should be removed after building road");
    }

    // Validates that building a settlement removes the required resources.
    @Test
    void testBuildSettlementRemovesResources() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);
        player.addResources(ResourceType.WOOL);
        player.addResources(ResourceType.GRAIN);
        player.buildSettlement();
        List<ResourceType> remaining = player.getResources();
        assertFalse(remaining.contains(ResourceType.BRICK));
        assertFalse(remaining.contains(ResourceType.LUMBER));
        assertFalse(remaining.contains(ResourceType.WOOL));
        assertFalse(remaining.contains(ResourceType.GRAIN));
    }

    // Validates that building a city removes the required resources.
    @Test
    void testBuildCityRemovesResources() {
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.buildCity();
        List<ResourceType> remaining = player.getResources();
        assertFalse(remaining.contains(ResourceType.GRAIN));
        assertFalse(remaining.contains(ResourceType.ORE));
    }
}