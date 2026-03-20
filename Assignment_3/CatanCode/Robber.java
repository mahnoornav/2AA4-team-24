import java.util.Random;

/**
 * Represents the robber in the game.
 * The robber sits on one tile, blocks that tile's production,
 * and can steal one random resource from a victim.
 */
public class Robber {

    private Tile currentTile;
    private Random random;

    // robber starts on a given tile (usually desert)
    public Robber(Tile startingTile) {
        this.currentTile = startingTile;
        this.random = new Random();
    }


    // move robber to a different tile
    public void moveRobber(Tile newTile) {
        if (newTile != null && newTile != currentTile) {
            currentTile = newTile;
        }
    }

    // steal one random card from victim and give it to stealer
    public ResourceType stealCard(Player stealer, Player victim) {
        if (victim.getResources().isEmpty()) {
            return null;
        }

        int index = random.nextInt(victim.getResources().size());
        ResourceType stolen = victim.getResources().remove(index);
        stealer.addResources(stolen, 1);
        return stolen;
    }

    // check if robber is blocking this tile
    public boolean blocksProduction(Tile tile) {
        return currentTile == tile;
    }

    // getter
    public Tile getCurrentTile() {
        return currentTile;
    }
}