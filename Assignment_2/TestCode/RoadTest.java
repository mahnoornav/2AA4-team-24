import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoadTest {

    private Player player;
    private Road road;

    // set up test for green player with a road located at edge 5
    @BeforeEach
    void setUp() {
        player = new ComputerPlayer("Green");
        road = new Road(player, 5);
    }

    // test if owner and road are initialized properly
    @Test
    void constructor_setsOwnerCorrectly() {
        assertEquals(player, road.getOwner(), "Road owner should match.");
    }

    // test if edge is set correctly to the road
    @Test
    void constructor_setsEdgeCorrectly() {
        assertEquals(5, road.getEdge(), "Road edge should match.");
    }
}
