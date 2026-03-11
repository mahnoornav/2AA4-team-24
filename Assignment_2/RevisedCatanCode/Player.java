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

    public void executeTurn(Board board, int roundNumber) {
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
    protected void buildSettlement() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
        resources.remove(ResourceType.WOOL);
        resources.remove(ResourceType.GRAIN);
    }

    // Removes resources required for city
    protected void buildCity() {
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
