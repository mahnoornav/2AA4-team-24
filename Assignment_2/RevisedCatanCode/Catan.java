import java.util.Arrays;

/**
 * 
 * Simulates a simplified version of the board game Catan.
 * Manages players, rounds, dice rolls, resource distribution, and turn execution until a winner is determined.
 * 
 */

public class Catan {

    private Player[] players;
    private Board board;
    private Dice dice;
    private int maxRounds;
    private Robber robber;

    public Catan(int maxRounds) {
        this.players = new Player[4];
        this.dice = new Dice();

        players[0] = new ComputerPlayer("Red");
        players[1] = new ComputerPlayer("Blue");
        players[2] = new ComputerPlayer("Green");
        players[3] = new HumanPlayer("Yellow");

        this.board = new Board(Arrays.asList(players));

        this.maxRounds = maxRounds;

        // place robber on desert tile at start
        for (Tile tile : board.getTiles()) {
            if (tile.getResource() == ResourceType.DESERT) {
                robber = new Robber(tile);
                break;
            }
        }

        // Initialize starting settlements and roads
        initializePlayers();

        // Export initial board state for visualizer
        GameExporter.export(board);
    }

    public void play() {
        for (int round = 1; round <= maxRounds; round++) {
            // roll dice and display result
            int roll = dice.roll();
            System.out.println("\n--- Round " + round + ", Roll: " + roll + " ---");

            if (roll == 7) {
                robberTurn(roll);
            } else {
                distributeResources(roll, round);
            }

            for (Player player : players) {
                player.executeTurn(board, round);

                // Export the board state after each round for visualizer
                GameExporter.export(board);

                if (player.getVictoryPoints() >= 10) {
                    displayWinner();
                    return;
                }
            }
        }
        displayWinner();
    }

    // method that initializes players
    // each player initially starts with 2 settlements and 2 roads
    private void initializePlayers() {

        // iterate through every player
        for (Player player : players) {

            // place settlements only if location is valid
            for (int i = 0; i < 2; i++) {
                int vertex = board.firstValidVertex();
                if (vertex != -1) {
                    board.placeSettlement(player, vertex);
                }
            }
            
            // place roads only if location is valid
            for (int i = 0; i < 2; i++) {
                int edge = board.firstValidEdge();
                if (edge != -1) {
                    board.placeRoad(player, edge);
                }
            }
        }
    }

    // method that distributes resources 
    // since tile number = dice roll, resources will be distributed according to the dice roll
    private void distributeResources(int roll, int roundNumber) {

        // shuffle through board tiles
        for (Tile tile : board.getTiles()) {
            if (tile.getNumber() == roll &&
                    tile.getResource() != ResourceType.DESERT &&
                    !robber.blocksProduction(tile)) {

                // goes through every vertex for tile
                for (int vertex : tile.getVertices()) {
                    Structure s = board.getStructure(vertex);
                    if (s != null) {
                        Player owner = s.getOwner();

                        // cities produce 2 resources
                        if (s instanceof City) {
                            owner.addResources(tile.getResource(), 2);
                        }
                        else {
                            owner.addResources(tile.getResource(), 1);
                        }

                        System.out.println("[" + roundNumber + "] / [" + owner.getPlayerColor() + "]: Receives " + tile.getResource());
                    }
                }
            }
        }
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
            System.out.println("\nGame Over! Winner is " + winner.getPlayerColor() + " with " + winner.getVictoryPoints() + " victory points.");
        } 
        
        else {
            System.out.println("\nGame Over! No winner.");
        }
    }

    private void robberTurn(int roll) {

        // robber only activates on a roll of 7
        if (roll != 7) {
            return;
        }

        // players with more than 7 resource cards discard half, rounded down
        for (Player player : players) {
            int totalResources = player.getResources().size();

            if (totalResources > 7) {
                int cardsToDiscard = totalResources / 2;

                // simple version: discard from front of hand
                for (int i = 0; i < cardsToDiscard; i++) {
                    player.getResources().remove(0);
                }
            }
        }

        
        // move robber to first different tile
        Tile currentTile = robber.getCurrentTile();
        Tile newTile = null;

        for (Tile tile : board.getTiles()) {
            if (tile != currentTile) {
                newTile = tile;
                break;
            }
        }

        if (newTile != null) {
            robber.moveRobber(newTile);
        }

        // steal from first valid adjacent player with resources
        if (newTile != null) {
            for (int vertex : newTile.getVertices()) {
                Structure s = board.getStructure(vertex);

                if (s != null) {
                    Player victim = s.getOwner();

                    if (victim != null && !victim.getResources().isEmpty()) {
                        // simple version: first player steals
                        robber.stealCard(players[0], victim);
                        break;
                    }
                }
            }
        }
    }

}
