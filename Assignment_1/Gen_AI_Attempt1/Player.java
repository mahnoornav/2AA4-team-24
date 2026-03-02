import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

    private String userColor;
    private int victoryPoints;
    private List<ResourceType> resources;
    private Random random;

    public Player(String userColor) {
        this.userColor = userColor;
        this.victoryPoints = 0;
        this.resources = new ArrayList<>();
        this.random = new Random();
    }

    public int takeTurn() {
        int roll = rollDice();
        collectResource(roll);
        int option = chooseMove();
        spend(option);
        return option;
    }

    public int rollDice() {
        return random.nextInt(6) + 1 + random.nextInt(6) + 1;
    }

    public void collectResource(int roll) {
        // Assignment: if roll == 7, no resources produced
        if (roll == 7) {
            return;
        }

        // Resource logic handled by Board normally
        // TODO: Implement board-based resource distribution
    }

    public int chooseMove() {
        return random.nextInt(3); // 0 = road, 1 = settlement, 2 = city
    }

    public boolean canBuildRoad() {
        return resources.size() >= 2;
    }

    public boolean canBuildSettlement() {
        return resources.size() >= 4;
    }

    public boolean canBuildCity() {
        return resources.size() >= 5;
    }

    public int options() {
        return random.nextInt(3);
    }

    public void spend(int option) {

        if (option == 0 && canBuildRoad()) {
            resources.remove(0);
            resources.remove(0);
        }

        if (option == 1 && canBuildSettlement()) {
            for (int i = 0; i < 4; i++) {
                resources.remove(0);
            }
            addVictoryPoints(1);
        }

        if (option == 2 && canBuildCity()) {
            for (int i = 0; i < 5; i++) {
                resources.remove(0);
            }
            addVictoryPoints(1);
        }
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addVictoryPoints(int point) {
        victoryPoints += point;
    }

    public String getUserColor() {
        return userColor;
    }
}

