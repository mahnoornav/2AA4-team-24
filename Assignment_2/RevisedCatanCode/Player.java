import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Catan game.
 */

public abstract class Player {

    private String color;
    private int victoryPoints;
    private List<ResourceType> resources;

    public Player(String color) {
        this.color = color;
        this.victoryPoints = 2;
        this.resources = new ArrayList<>();
    }

    // method that executes this player's full turn
    // (TO BE REMOVED WHEN HUMAN AND COMPUTER PLAYER MADE, MOVE THIS INTO COMPUTER PLAYER)
    // NEEDS TO BE FIXED FOR STRUCTURE AND CITY FORMAT
    public void executeTurn(Board board, int roundNumber) {

        // attempt to build a settlement
        if (ValidateMove.canBuildSettlement(this)) {
            int vertex = board.firstValidVertex();
            if (vertex != -1) {
                buildSettlement();
                board.placeSettlement(this, vertex);
                System.out.println("[" + roundNumber + "] / [" + color + "]: Builds settlement at vertex " + vertex);
                return;
            }
        }

        // attempt to upgrade a settlement to city
        if (ValidateMove.canBuildCity(this)) {
            for (Integer vertex : board.getVertices().keySet()) {
                Structure s = board.getStructure(vertex);
                if (s != null && s.getOwner() == this && s instanceof Settlement) {

                    buildCity();
                    board.placeCity(this, vertex);
                    System.out.println("[" + roundNumber + "] / [" + color + "]: Upgrades settlement at vertex " + vertex + " to city");
                    return;
                }
            }
        }

        // attempt to build a road
        if (ValidateMove.canBuildRoad(this)) {
            int edge = board.firstValidEdge();
            if (edge != -1) {
                buildRoad();
                board.placeRoad(this, edge);
                System.out.println("[" + roundNumber + "] / [" + color + "]: Builds road at edge " + edge);
                return;
            }
        }

        // otherwise pass turn
        System.out.println("[" + roundNumber + "] / [" + color + "]: Passes turn");
    }
    
    // Checks if a player has a specific resource in their cards
    public boolean hasResource (ResourceType resource) {
        return resources.contains(resource);
    }

    // Removes resources required for road
    private void buildRoad() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
    }

    // Removes resources required for settlement
    private void buildSettlement() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
        resources.remove(ResourceType.WOOL);
        resources.remove(ResourceType.GRAIN);
    }

    // Removes resources required for city
    private void buildCity() {
        resources.remove(ResourceType.GRAIN);
        resources.remove(ResourceType.GRAIN);
        resources.remove(ResourceType.ORE);
        resources.remove(ResourceType.ORE);
        resources.remove(ResourceType.ORE);

    }

    // Adds victory points based on player move
    public void addVictoryPoints(int point) {
        victoryPoints += point;
    }

    // Adds resources that player draws based on roll
    public void addResources(ResourceType newResources) {
        resources.add(newResources);
    }

    // Returns victory player points
    public int getVictoryPoints() {
        return victoryPoints;
    }

    // Returns player color
    public String getPlayerColor() {
        return color;
    }

    // Returns player resources
    public List<ResourceType> getResources() {
        return resources;
    }



}
