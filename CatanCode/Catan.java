import java.util.Random;

/**
 * 
 * Simulates a simplified version of the board game Catan.
 * Manages players, rounds, dice rolls, resource distribution, and turn execution until a winner is determined.
 * 
 */

public class Catan {

    private Player[] players;
    private Board board;
    private int maxRounds;

    public Catan(int maxRounds) {
        this.board = new Board();
        this.players = new Player[4];

        players[0] = new Player("Red");
        players[1] = new Player("Blue");
        players[2] = new Player("Green");
        players[3] = new Player("Yellow");

        this.maxRounds = maxRounds;

        // Initialize starting settlements and roads
        initializePlayers();
    }

    // method that initializes players
    // each player initially starts with 2 settlements and 2 roads
    private void initializePlayers() {

        // iterate through every player
        for (Player player : players) {

            // place settlements only if location is valid
            for (int i = 0; i < 2; i++) {
                int vertex = board.getVertex();
                if (vertex != -1) {
                    board.placeSettlement(player, vertex);
                }
            }
            
            // place roads only if location is valid
            for (int i = 0; i < 2; i++) {
                int edge = board.getEdge();
                if (edge != -1) {
                    board.placeRoad(player, edge);
                }
            }
        }
    }

    // main method that simulates the game
    private void play() {
        for (int round = 1; round <= maxRounds; round++) {
            int roll = rollDice();

            distributeResources(roll, round);

            for (Player player : players) {
                executePlayerTurn(player, round);
                if (player.getVictoryPoints() >= 10) {
                    displayWinner();
                    return;
                }
            }
        }
        displayWinner();
    }

    // method that rolls 2 six-sided dice
    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1 + rand.nextInt(6) + 1;
    }

    // method that distributes resources 
    // since tile number = dice roll, resources will be distributed according to the dice roll
    private void distributeResources(int roll, int roundNumber) {

        // shuffle through board tiles
        for (Tile tile : board.getTiles()) {
            if (tile.getNumber() == roll && tile.getResource() != ResourceType.DESERT) {

                // goes through every vertex for tile
                for (int vertex : tile.getVertices()) {
                    Settlement s = board.getSettlement(vertex);
                    if (s != null) {
                        Player owner = s.getOwner();
                        
                        // cities produce 2 resources
                        if (s.getLevel().equals("CITY")) {
                            owner.addResources(tile.getResource());
                        }

                        owner.addResources(tile.getResource());
                        System.out.println("[" + roundNumber + "] / [" + owner.getUserColor() + "]: Receives " + tile.getResource());
                    }
                }
            }
        }
    }

    // method that executes the full turn of a single player
    private void executePlayerTurn(Player player, int roundNumber) {

        // attempt to build a settlement
        if (player.canBuildSettlement()) {
            int vertex = board.getVertex();
            if (vertex != -1) {
                player.buildSettlement();
                board.placeSettlement(player, vertex);
                System.out.println("[" + roundNumber + "] / [" + player.getUserColor() + "]: Builds settlement at vertex " + vertex);
                return;
            }
        }

        // attempt to upgrade a settlement to city
        if (player.canBuildCity()) {
            for (Integer vertex : board.getVertices().keySet()) {
                Settlement s = board.getSettlement(vertex);
                if (s != null &&
                    s.getOwner() == player &&
                    s.getLevel().equals("SETTLEMENT")) {

                    player.buildCity();
                    board.upgradeCity(player, vertex);
                    System.out.println("[" + roundNumber + "] / [" + player.getUserColor() + "]: Upgrades settlement at vertex " + vertex + " to city");
                    return;
                }
            }
        }

        // attempt to build a road
        if (player.canBuildRoad()) {
            int edge = board.getEdge();
            if (edge != -1) {
                player.buildRoad();
                board.placeRoad(player, edge);
                System.out.println("[" + roundNumber + "] / [" + player.getUserColor() + "]: Builds road at edge " + edge);
                return;
            }
        }

        // otherwise, pass turn
        System.out.println("[" + roundNumber + "] / [" + player.getUserColor() + "]: Passes turn");
    }

    // display the winner of the game
    private void displayWinner() {
        Player winner = null;
        int maxPoints = -1;
        
        for (Player player : players) {
            if (player.getVictoryPoints() > maxPoints) {
                maxPoints = player.getVictoryPoints();
                winner = player;
            }
        }
        if (winner != null) {
            System.out.println("\nGame Over! Winner is " + winner.getUserColor() + " with " + winner.getVictoryPoints() + " victory points.");
        } 
        
        else {
            System.out.println("\nGame Over! No winner.");
        }
    }

    // main method that runs the game
    public static void main(String[] args) {
        Catan game = new Catan(20);
        game.play();
    }
}
