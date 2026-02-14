/**
 * Represents a road placed by a player on a specific edge of the Catan board. 
 * Each road is owned by exactly one player.
 */

public class Road {
    private final Player owner;
    private final int edge;

    public Road(Player owner, int edge) {
        this.owner = owner;
        this.edge = edge;
    }

    public Player getOwner() {
        return owner;
    }

    public int getEdge() {
        return edge;
    }

}

