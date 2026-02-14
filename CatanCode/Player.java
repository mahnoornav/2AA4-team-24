import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Catan game.
 */

public class Player {

    private String userColor;
    private int victoryPoints;
    private List<ResourceType> resources;

    public Player(String userColor) {
        this.userColor = userColor;
        this.victoryPoints = 2;
        this.resources = new ArrayList<>();
    }

    // Determines if resource available to build road
    public boolean canBuildRoad() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER);
    }

    // Determines if resource available to build settlement
    public boolean canBuildSettlement() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER) && resources.contains(ResourceType.WOOL) && resources.contains(ResourceType.GRAIN);
    }

    // Determines if resource available to build city
    public boolean canBuildCity() {
        int oreCount = 0;
        int wheatCount = 0;

        // Count resources
        for (ResourceType r : resources) {
            if (r == ResourceType.ORE) {
                oreCount++;
            } else if (r == ResourceType.GRAIN) {
                wheatCount++;
            }
        }

    // Need at least 3 ORE and 2 WHEAT to build a city
    return oreCount >= 3 && wheatCount >= 2;
}

    // Removes resources required for road
    public void buildRoad() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
    }

    // Removes resources required for settlement
    public void buildSettlement() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
        resources.remove(ResourceType.WOOL);
        resources.remove(ResourceType.GRAIN);
    }

    // Removes resources required for city
    public void buildCity() {
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

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public List<ResourceType> getResources() {
        return resources;
    }

    public String getUserColor () {
        return userColor;
    }
}
