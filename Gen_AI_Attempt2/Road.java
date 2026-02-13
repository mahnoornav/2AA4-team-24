
// Road.java
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

