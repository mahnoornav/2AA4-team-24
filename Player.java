import java.util.ArrayList;
import java.util.List;

public class Player {

    private String userColor;
    private int victoryPoints;
    private List<ResourceType> resources;

    // Constructor
    public Player(String userColor) {
        this.userColor = userColor;
        this.victoryPoints = 2;
        this.resources = new ArrayList<>();
    }

    // Plays agent's turn based on roll
    public int turn (int roll) {
        int move = chooseMove();
        spend(move);
        return move;
    }

    // Chooses agent's move based on resources it has
    private int chooseMove() {
        if (canBuildSettlement()) return 1;
        else if (canBuildCity()) return 2;
        else if (canBuildRoad()) return 3;
        else return 0; // Pass
    }

    // Spends resource cards based on agent's move
    private void spend(int option) {
        switch (option) {
            case 1:
                buildSettlement();
                addVictoryPoints(1);
                //System.out.println(userColor + " builds a settlement");
                break;
            case 2:
                buildCity();
                addVictoryPoints(1);
                //System.out.println(userColor + " upgrades a settlement to city");
                break;
            case 3:
                buildRoad();
                //System.out.println(userColor + " builds a road");
                break;
            default:
                System.out.println(userColor + " passes turn");
        }
    }

    // Determines if resource available to build road
    private boolean canBuildRoad() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER);
    }

    // Determines if resource available to build settlement
    private boolean canBuildSettlement() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER) && resources.contains(ResourceType.WOOL) && resources.contains(ResourceType.GRAIN);
    }

    // Determines if resource available to build city
    private boolean canBuildCity() {
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
    public void addResources(List<ResourceType> newResources) {
        resources.addAll(newResources);
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public List<ResourceType> getResources() {
        return resources;
    }
}
