import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SettlementTest {

    private Player player;
    private Settlement settlement;

    // set up settlement test for blue player located at vertex point 10
    @BeforeEach
    void setUp() {
        player = new Player("Blue");
        settlement = new Settlement(player, 10);
    }

    // test if settlement is for the right player
    @Test
    void constructor_setsOwnerCorrectly() {
        assertEquals(player, settlement.getOwner(), "Settlement owner should match.");
    }

    // test if settlement is placed at the right location
    @Test
    void constructor_setsVertexCorrectly() {
        assertEquals(10, settlement.getVertex(), "Settlement vertex should match.");
    }

    // test if settlement has default value of "SETTLEMENT"
    @Test
    void constructor_defaultLevelIsSettlement() {
        assertEquals("SETTLEMENT", settlement.getLevel(), "Default level should be SETTLEMENT.");
    }

    // test if upgrading works successfully
    @Test
    void upgrade_changesLevelToCity() {
        settlement.upgrade();
        assertEquals("CITY", settlement.getLevel(), "Level should change to CITY.");
    }
}