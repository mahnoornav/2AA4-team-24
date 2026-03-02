import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Red");
    }

    // Has required resources
    @Test
    void canBuildRoad_trueWithBrickAndLumber() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);

        assertTrue(player.canBuildRoad(), "Player can build road.");
    }

    // Missing required resource
    @Test
    void canBuildRoad_falseWithMissingResource() {
        player.addResources(ResourceType.BRICK);

        assertFalse(player.canBuildRoad(), "Player can't build road.");
    }

    // All settlement resources present
    @Test
    void canBuildSettlement_trueWithAllResources() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);
        player.addResources(ResourceType.WOOL);
        player.addResources(ResourceType.GRAIN);

        assertTrue(player.canBuildSettlement(), "Player can build settlement.");
    }

    // Exactly 3 ORE and 2 GRAIN
    @Test
    void canBuildCity_trueAtExactBoundary() {
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.GRAIN);

        assertTrue(player.canBuildCity(), "Player can build city.");
    }

    // Just below requirement
    @Test
    void canBuildCity_falseAtBelowBoundary() {
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.GRAIN);

        assertFalse(player.canBuildCity(), "Player can't build city.");
    }

    @Test
    void canBuildSettlement_falseWithMissingOneResource() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);
        player.addResources(ResourceType.WOOL);

        assertFalse(player.canBuildSettlement(), "Player is missing resource.");
    }

    // Removes correct resources
    @Test
    void buildRoad_removesBrickAndLumber() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);

        player.buildRoad();

        assertFalse(player.getResources().contains(ResourceType.BRICK), "Removed Brick.");
        assertFalse(player.getResources().contains(ResourceType.LUMBER), "Removed Lumber.");
    }

    // Updates player instance variables
    @Test
    void addVictoryPoints_updatesCorrectly() {
        player.addVictoryPoints(3);
        assertEquals(5, player.getVictoryPoints(), "Victory Points updated."); // starts at 2
    }

    @Test
    void addResources_addsResourceToList() {
        player.addResources(ResourceType.WOOL);
        assertTrue(player.getResources().contains(ResourceType.WOOL), "Player gained resource.");
    }
}
