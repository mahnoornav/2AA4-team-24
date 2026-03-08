public abstract class Structure {
    private Player owner;
    private int vertex;

    public Structure(Player player, int vertex) {
        this.player = player;
        this.vertex = vertex;

    }

    public Player getPlayer(){
        return this.player; 
    }
}