
// Catan.java
import java.util.*;

public class Catan {
    private final List<Player> players; // Player[*]
    private final Board board;          // Board[1]
    private int currentTurn;
    private int maxRounds;

    public Catan() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.currentTurn = 0;
        this.maxRounds = 100; // default; set from config later
    }

    public void play() {
        startGame();

        for (int round = 1; round <= maxRounds; round++) {
            playRound(round);
            if (checkWin()) {
                displayWinner();
                return;
            }
        }

        // No winner by maxRounds
        displayWinner();
    }

    private boolean checkWin() {
        for (Player p : players) {
            if (p.getVictoryPoints() >= 10) return true;
        }
        return false;
    }

    private void displayWinner() {
        Player best = null;
        for (Player p : players) {
            if (best == null || p.getVictoryPoints() > best.getVictoryPoints()) {
                best = p;
            }
        }

        if (best == null) {
            System.out.println("No players in the game.");
        } else {
            System.out.println("Winner: " + best.getUserColor() + " with " + best.getVictoryPoints() + " VP");
        }
    }

    private void playRound(int roundNumber) {
        // One round = each player takes one turn
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(currentTurn);

            // Example output format: [Round] / [PlayerID or color]: [Action]
            int action = p.takeTurn();
            System.out.println("[" + roundNumber + "] / " + p.getUserColor() + ": action=" + action);

            // advance turn
            currentTurn = (currentTurn + 1) % players.size();

            if (checkWin()) return;
        }

        // End of round: print VP summary (assignment requirement)
        StringBuilder sb = new StringBuilder("End of Round " + roundNumber + " VPs: ");
        for (Player p : players) {
            sb.append(p.getUserColor()).append("=").append(p.getVictoryPoints()).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    private void startGame() {
        // TODO: create 4 players (random agents)
        if (players.isEmpty()) {
            players.add(new Player("P1"));
            players.add(new Player("P2"));
            players.add(new Player("P3"));
            players.add(new Player("P4"));
        }

        // TODO: setup fixed valid map tiles/adjacency
        // Example tile add:
        // board.addTile(new Tile(ResourceType.BRICK, 8, new int[]{0,1,2,3,4,5}));

        currentTurn = 0;
    }

    // Helpers to configure game externally
    public void setMaxRounds(int maxRounds) {
        if (maxRounds < 1 || maxRounds > 8192) {
            throw new IllegalArgumentException("maxRounds must be in [1, 8192]");
        }
        this.maxRounds = maxRounds;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public static void main(String[] args) {
        Catan c = new Catan();
        c.play();
    }
}
