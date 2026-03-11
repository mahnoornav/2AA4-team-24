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
        player = new Player("Red");
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
        player.addResources(ResourceType.BRICK);
        assertTrue(player.hasResource(ResourceType.BRICK));
    }

    // Player DOES NOT HAVE resource
    @Test
    void hasResource_false() {
        assertFalse(player.hasResource(ResourceType.BRICK));
    }

    @Test
    void buildRoad_removesCorrectResources() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);

        player.buildRoad();

        assertFalse(player.hasResource(ResourceType.BRICK));
        assertFalse(player.hasResource(ResourceType.LUMBER));
    }

    @Test
    void buildSettlement_removesCorrectResources() {
        player.addResources(ResourceType.BRICK);
        player.addResources(ResourceType.LUMBER);
        player.addResources(ResourceType.WOOL);
        player.addResources(ResourceType.GRAIN);

        player.buildSettlement();

        assertFalse(player.hasResource(ResourceType.BRICK));
        assertFalse(player.hasResource(ResourceType.LUMBER));
        assertFalse(player.hasResource(ResourceType.WOOL));
        assertFalse(player.hasResource(ResourceType.GRAIN));
    }

    @Test
    void buildCity_removesCorrectResources() {
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.GRAIN);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);
        player.addResources(ResourceType.ORE);

        player.buildCity();

        assertFalse(player.hasResource(ResourceType.GRAIN));
        assertFalse(player.hasResource(ResourceType.ORE));
    }
    
    @Test
    void addResources_increasesResourceCount() {
        int before = player.getResources().size();

        player.addResources(ResourceType.BRICK);

        int after = player.getResources().size();

        assertEquals(before + 1, after);
    }

}