import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for the Board Class - Tests settlement placements, road rules and city upgrades
 */

public class BoardTest {

    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        board = new Board();
        player1 = new Player("Red");
        player2 = new Player("Blue");
    }

    // Settlement should be placed on a valid vertex and player should gain 1 VP
    @Test
    void placeSettlement_validPlacement() {
        int vertex = board.getVertex();
        int initialVP = player1.getVictoryPoints();

        board.placeSettlement(player1, vertex);
        Settlement settlement = board.getSettlement(vertex);

        assertNotNull(settlement);
        assertEquals(player1, settlement.getOwner());
        assertEquals(initialVP + 1, player1.getVictoryPoints());

    }

    // Nothing should happen when placing a settlement on a occupied vertex
    @Test
    void placeSettlement_onOccupiedVertex() {
        int vertex = board.getVertex();
        board.placeSettlement(player1, vertex);
        int vpBefore = player1.getVictoryPoints();

        // Try again at the same vertex
        board.placeSettlement(player1, vertex);
        int vpAfter = player1.getVictoryPoints();

        assertEquals(vpBefore, vpAfter);
    }

    @Test
    void placeSettlement_invalidVertex() {
        int initialVP = player1.getVictoryPoints();
        board.placeSettlement(player1, -1);

        assertTrue(board.getVertices().isEmpty());
        assertEquals(initialVP, player1.getVictoryPoints());
    }

    // Road should not be placed if player has no connection
    @Test
    void placeRoad_withoutConnection() {
        int edge = board.getEdge();

        board.placeRoad(player1, edge);
        Road road = board.getRoad(edge);

        assertNull(road);
    }

    // Road should only be placed when player has a settlement connection
    @Test
    void placeRoad_withConnection() {
        int vertex = board.getVertex();
        board.placeSettlement(player1, vertex);

        int edge = board.getEdge();
        board.placeRoad(player1, edge);
        Road road = board.getRoad(edge);

        assertNotNull(road);
        assertEquals(player1, road.getOwner());

    }

    // Upgrading a settlement should turn it into a city and give an additional VP
    @Test
    void upgradeCity_validSettlement() {
        int vertex = board.getVertex();

        board.placeSettlement(player1, vertex);
        int vpAfterSettlement = player1.getVictoryPoints();

        board.upgradeCity(player1, vertex);

        Settlement settlement = board.getSettlement(vertex);

        assertEquals("CITY", settlement.getLevel());
        assertEquals(vpAfterSettlement + 1, player1.getVictoryPoints());

    }

    // Player should not be able to upgrade another player's settlements
    @Test
    void upgradeCity_wrongOwner() {
        int vertex = board.getVertex();
        board.placeSettlement(player2, vertex);
        int player1_VPBefore = player1.getVictoryPoints();

        board.upgradeCity(player1, vertex);
        Settlement settlement = board.getSettlement(vertex);

        assertEquals("SETTLEMENT", settlement.getLevel());
        assertEquals(player1_VPBefore, player1.getVictoryPoints());
    }


}
