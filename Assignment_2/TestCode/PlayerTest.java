import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Additional tests for Player class.
 * Includes partition testing and boundary testing.
 */

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new ComputerPlayer("Red");
    }

    // Player starts with minimum victory points
    @Test
    void initialVictoryPoints() {
        assertEquals(2, player.getVictoryPoints());
    }

    // Adding 0 victory points should not change score
    @Test
    void addZeroVictoryPoints() {
        player.addVictoryPoints(0);
        assertEquals(2, player.getVictoryPoints());
    }

    // Player with no resources
    @Test
    void playerHasNoResources() {
        assertTrue(player.getResources().isEmpty());
    }

    // Player HAS resource
    @Test
    void hasResource_true() {
        player.addResources(ResourceType.BRICK, 1);
        assertTrue(player.hasResource(ResourceType.BRICK));
    }

    // Player DOES NOT HAVE resource
    @Test
    void hasResource_false() {
        assertFalse(player.hasResource(ResourceType.BRICK));
    }

    @Test
    void buildRoad_removesCorrectResources() {
        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.LUMBER, 1);

        player.buildRoad();

        assertFalse(player.hasResource(ResourceType.BRICK));
        assertFalse(player.hasResource(ResourceType.LUMBER));
    }

    @Test
    void buildSettlement_removesCorrectResources() {
        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.LUMBER, 1);
        player.addResources(ResourceType.WOOL, 1);
        player.addResources(ResourceType.GRAIN, 1);

        player.buildSettlement();

        assertFalse(player.hasResource(ResourceType.BRICK));
        assertFalse(player.hasResource(ResourceType.LUMBER));
        assertFalse(player.hasResource(ResourceType.WOOL));
        assertFalse(player.hasResource(ResourceType.GRAIN));
    }

    @Test
    void buildCity_removesCorrectResources() {
        player.addResources(ResourceType.GRAIN, 2);
        player.addResources(ResourceType.ORE, 3);

        player.buildCity();

        assertFalse(player.hasResource(ResourceType.GRAIN));
        assertFalse(player.hasResource(ResourceType.ORE));
    }
    
    @Test
    void addResources_increasesResourceCount() {
        int before = player.getResources().size();

        player.addResources(ResourceType.BRICK, 1);

        int after = player.getResources().size();

        assertEquals(before + 1, after);
    }

}