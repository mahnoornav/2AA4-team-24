public abstract class Structure {
    private Player owner;
    private int vertex;

    public Structure(Player player, int vertex) {
        this.owner = player;
        this.vertex = vertex;

    }

    public Player getOwner(){
        return this.owner;
    }

    public int getVertex() {
        return vertex;
    }
}