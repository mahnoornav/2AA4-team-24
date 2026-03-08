import java.util.Random;

/**
 * Simulates a dice object with a roll method that return the sum of two six sided dice rolls.
 */
public class Dice {
    private Random rand = new Random();

    public int roll() {
        return (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1); // 1–6
    }
}