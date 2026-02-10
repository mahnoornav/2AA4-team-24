package org.catanGen;

// Settlement.java
public class Settlement {
    private String level;      // e.g., "SETTLEMENT" or "CITY"
    private final Player owner;
    private final int vertex;

    public Settlement(Player owner, int vertex) {
        this.owner = owner;
        this.vertex = vertex;
        this.level = "SETTLEMENT";
    }

    public void upgrade() {
        // TODO: enforce "can only upgrade if currently a settlement"
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
