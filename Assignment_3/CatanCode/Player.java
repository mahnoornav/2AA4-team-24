import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player in the Catan game.
 */

// changed some visibility here
// added method removeResources
// added method getResourcesCount to help with trading
// modified method addResources

public abstract class Player implements Trade {

    private String color;
    private int victoryPoints;
    private List<ResourceType> resources;

    public Player(String color) {
        this.color = color;
        this.victoryPoints = 2;
        this.resources = new ArrayList<>();
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

    protected void buyDevelopmentCard(){
        resources.remove(ResourceType.ORE);
        resources.remove(ResourceType.WOOL);
        resources.remove(ResourceType.GRAIN);
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

    public int getResourceCount(ResourceType type) {
        return Collections.frequency(resources, type);
    }

    // Shared trade mechanics
    public void tradeBank(ResourceType offer, ResourceType receive) {
        if (getResourceCount(offer) >= 4) {
            removeResources(offer, 4);
            addResources(receive, 1);
            System.out.println(getPlayerColor() + " traded 4 " + offer + " for 1 " + receive + " from the bank.");
        } else {
            System.out.println(getPlayerColor() + " doesn't have enough resources to trade with the bank.");
        }
    }

    // abstract method: each player handles their turn differently
    public abstract void executeTurn(Board board, int roundNumber);
    
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

    public boolean canAffordRoad() {
        return getResourceCount(ResourceType.BRICK) >= 1 &&
                getResourceCount(ResourceType.LUMBER) >= 1;
    }

    public boolean canAffordSettlement() {
        return getResourceCount(ResourceType.BRICK) >= 1 &&
                getResourceCount(ResourceType.LUMBER) >= 1 &&
                getResourceCount(ResourceType.WOOL) >= 1 &&
                getResourceCount(ResourceType.GRAIN) >= 1;
    }

    public boolean canAffordCity() {
        return getResourceCount(ResourceType.GRAIN) >= 2 &&
                getResourceCount(ResourceType.ORE) >= 3;
    }


}
