
// Player.java
import java.util.*;

public class Player {
    private final String userColor;
    private int victoryPoints;
    private final List<ResourceType> resources;

    public Player(String userColor) {
        this.userColor = userColor;
        this.victoryPoints = 0;
        this.resources = new ArrayList<>();
    }

    public int takeTurn() {
        int roll = rollDice();
        collectResource(roll);

        // Player decides what to do (build road/settlement/city or pass)
        int choice = chooseMove();
        spend(choice);
        return choice;
    }

    private int rollDice() {
        // 2d6
        int d1 = 1 + (int)(Math.random() * 6);
        int d2 = 1 + (int)(Math.random() * 6);
        return d1 + d2;
    }

    /**
     * This needs Board information to be correct (who has settlements on what vertices, etc.).
     * So we keep it as a stub that the Game (Catan) can drive with Board context.
     */
    private void collectResource(int roll) {
        // TODO: resource distribution should be handled with Board:
        // - find tiles with number == roll
        // - for each adjacent vertex with a settlement/city, give resource(s) to that owner
    }

    private int chooseMove() {
        // TODO: choose among legal moves (random agent)
        // For now: always pass (0)
        return 0;
    }

    public boolean canBuildRoad() {
        // TODO: check required resources for a road (usually BRICK + LUMBER)
        return true;
    }

    public boolean canBuildSettlement() {
        // TODO: check required resources for a settlement (BRICK + LUMBER + WOOL + WHEAT)
        return true;
    }

    public boolean canBuildCity() {
        // TODO: check required resources for a city (ORE + ORE + ORE + WHEAT + WHEAT)
        return true;
    }

    public int options() {
        // TODO: return an encoded set of options (assignment-specific)
        return 0;
    }

    public void spend(int option) {
        // TODO: remove resources based on chosen action
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addVictoryPoints(int point) {
        this.victoryPoints += point;
    }

    public String getUserColor() {
        return userColor;
    }

    public List<ResourceType> getResources() {
        return Collections.unmodifiableList(resources);
    }

    public void addResource(ResourceType r) {
        resources.add(r);
    }
}
