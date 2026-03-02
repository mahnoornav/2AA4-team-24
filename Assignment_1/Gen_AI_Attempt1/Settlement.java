public class Settlement {

    private String level;
    private Player owner;
    private int vertex;

    public Settlement(Player owner, int vertex) {
        this.owner = owner;
        this.vertex = vertex;
        this.level = "SETTLEMENT";
    }

    public void upgrade() {
        if (level.equals("SETTLEMENT")) {
            level = "CITY";
            owner.addVictoryPoints(1); // city gives +1 extra point (settlement already gave 1)
        }
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

