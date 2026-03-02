public class Road {

    private Player owner;
    private int edge;

    public Road(Player owner, int edge) {
        this.owner = owner;
        this.edge = edge;
    }

    public Player getRoad() {
        return owner;
    }

    public int getEdge() {
        return edge;
    }
}

