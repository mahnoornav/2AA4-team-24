import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * JUnit tests for the CommandParser class - Tests all terminal commands entered by player
 */
public class CommandParserTest {

    private CommandParser parser;
    private Player player;
    private Board board;

    @BeforeEach
    void setUp() {
        parser = new CommandParser();
        player = new ComputerPlayer("Red");

        // Board requires a list of players
        board = new Board(List.of(player));    }

    // Test roll command
    @Test
    void rollCommand_returnsTrue() {
        boolean result = parser.parse("Roll", player, board);
        assertTrue(result);
    }

    // Test Go command
    @Test
    void goCommand_returnsFalse() {
        boolean result = parser.parse("Go", player, board);
        assertFalse(result);
    }

    // Test List command
    @Test
    void listCommand_returnsTrue() {
        boolean result = parser.parse("List", player, board);
        assertTrue(result);
    }

    // Test build settlement command
    @Test
    void buildSettlementCommand_placesSettlement() {
        int vertex = board.firstValidVertex();

        parser.parse("Build settlement " + vertex, player, board);
        Structure structure = board.getStructure(vertex);

        assertNotNull(structure);
        assertEquals(player, structure.getOwner());
    }

    // Test build city command
    @Test
    void buildCityCommand_placesCity() {
        int vertex = board.firstValidVertex();
        board.placeSettlement(player, vertex);

        parser.parse("Build city " + vertex, player, board);
        Structure structure = board.getStructure(vertex);

        assertTrue(structure instanceof City);
    }

    // Test invalid command
    @Test
    void invalidCommand_returnsTrue() {
        boolean result = parser.parse("Build car", player, board);

        assertTrue(result);
    }

}