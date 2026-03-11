import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Catan game.
 */

// changed some visibility here
// added method removeResources
// modified method addResources

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
    protected void buildRoad() {
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

    // Remove a certain quantity of a resource
    public void removeResources(ResourceType type, int quantity) {
        for (int i = 0; i < quantity; i++) {
            resources.remove(type);
        }
    }

    // Add a certain quantity of a resource
    public void addResources(ResourceType type, int quantity) {
        for (int i = 0; i < quantity; i++) {
            resources.add(type);
        }
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
