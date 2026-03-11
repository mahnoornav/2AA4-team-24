import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructureTest {

    private Player player;
    private Structure structure;

    // set up settlement test for blue player located at vertex point 10
    @BeforeEach
    void setUp() {
        player = new ComputerPlayer("Blue");
        structure = new Structure(player, 10) {
        };
    }

    // test if settlement is for the right player
    @Test
    void constructor_setsOwnerCorrectly() {
        assertEquals(player, structure.getOwner(), "Structure owner should match.");
    }

    // test if settlement is placed at the right location
    @Test
    void constructor_setsVertexCorrectly() {
        assertEquals(10, structure.getVertex(), "Structure vertex should match.");
    }

}