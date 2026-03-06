import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class CatanTest {

    private Catan game;
    private Board board;
    private Player[] players;

    // set up a Catan game before each test
    @BeforeEach
    void setUp() throws Exception {

        game = new Catan(10);

        // access private fields using reflection
        Field boardField = Catan.class.getDeclaredField("board");
        boardField.setAccessible(true);
        board = (Board) boardField.get(game);

        Field playersField = Catan.class.getDeclaredField("players");
        playersField.setAccessible(true);
        players = (Player[]) playersField.get(game);
    }

    // test if game initializes with 4 players
    @Test
    void constructor_setsPlayersCorrectly() {
        assertEquals(4, players.length, "Game should initialize four players.");
    }

    // test if board is initialized
    @Test
    void constructor_setsBoardCorrectly() {
        assertNotNull(board, "Board should be initialized when game starts.");
    }

    // test if players start with settlements placed on the board
    @Test
    void initializePlayers_placesStartingSettlements() {
        // 4 players with 2 settlements each = 8 settlements
        assertEquals(8, board.getVertices().size(), "Board should contain starting settlements.");
    }

    // test if players receive victory points from starting settlements
    @Test
    void initializePlayers_givesPlayersVictoryPoints() {

        for (Player p : players) {
            assertEquals(4, p.getVictoryPoints(),
                    "Each player should start with 4 victory points (2 initial + 2 settlements).");
        }
    }

    // test if play() runs without crashing
    @Test
    void play_runsWithoutErrors() {
        assertDoesNotThrow(() -> game.play(),
                "Game play should run without throwing errors.");
    }
}
