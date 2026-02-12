import java.util.ArrayList;
import java.util.List;

public class Player {

    private String userColor;
    private int victoryPoints;
    private List<ResourceType> resources;

    public Player(String userColor) {
        this.userColor = userColor;
        this.victoryPoints = 0;
        this.resources = new ArrayList<>();
    }

    public int turn (int roll) {
        int move = chooseMove();
        spend(move);
        return move;
    }

    private int chooseMove() {
        if (canBuildSettlement()) return 1;
        else if (canBuildCity()) return 2;
        else if (canBuildRoad()) return 3;
        else return 0; // Pass
    }

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

    private boolean canBuildRoad() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER);
    }

    private boolean canBuildSettlement() {
        return resources.contains(ResourceType.BRICK) && resources.contains(ResourceType.LUMBER) && resources.contains(ResourceType.WOOL) && resources.contains(ResourceType.GRAIN);
    }

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

    public void buildRoad() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
    }

    public void buildSettlement() {
        resources.remove(ResourceType.BRICK);
        resources.remove(ResourceType.LUMBER);
        resources.remove(ResourceType.WOOL);
        resources.remove(ResourceType.GRAIN);
    }

    public void buildCity() {
        resources.remove(ResourceType.GRAIN);
        resources.remove(ResourceType.GRAIN);
        resources.remove(ResourceType.ORE);
        resources.remove(ResourceType.ORE);
        resources.remove(ResourceType.ORE);

    }

    public void addVictoryPoints(int point) {
        victoryPoints += point;
    }

    public void addResources(List<ResourceType> newResources) {
        resources.addAll(newResources);
    }

    public String getUserColor() {
        return userColor;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public List<ResourceType> getResources() {
        return resources;
    }
}
