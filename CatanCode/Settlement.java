/**
 * Represents a settlement placed by a player on a board vertex.
 * A settlement belongs to one player and can be upgraded from a settlement to a city.
 */

public class Settlement {
    private String level;      // defines status of the settlement, can be either SETTLEMENT (default beginning house) or CITY
    private final Player owner;
    private final int vertex;

    public Settlement(Player owner, int vertex) {
        this.owner = owner;
        this.vertex = vertex;
        this.level = "SETTLEMENT"; // by default each player starts with a settlement
    }

    // this method is called when we update the status of the settlement
    // to a city
    public void upgrade() {
        this.level = "CITY";
    }

    public Player getOwner() {
        return owner;
    }

    public String getLevel() {
        return level;
    }

    public int getVertex() {
        return vertex;
    }
}
