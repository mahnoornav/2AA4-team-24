package org.catanGen;

// Tile.java
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final ResourceType resource;
    private final int number;
    private final int[] adjacentVertices; // size 6

    public Tile(ResourceType resource, int number, int[] adjacentVertices) {
        if (adjacentVertices == null || adjacentVertices.length != 6) {
            throw new IllegalArgumentException("A tile must have exactly 6 adjacent vertices.");
        }
        this.resource = resource;
        this.number = number;
        this.adjacentVertices = adjacentVertices.clone();
    }

    public ResourceType getResource() {
        return resource;
    }

    public int getNumber() {
        return number;
    }

    public int[] getVertices() {
        return adjacentVertices.clone();
    }

    /**
     * NOTE: This method needs Board/topology knowledge to be truly correct.
     * For now, this stub returns an empty list.
     */
    public List<Tile> getAdjacentTiles(int vertex) {
        // TODO: implement using board topology (which tiles touch a given vertex)
        return new ArrayList<>();
    }

    public boolean touchesVertex(int vertex) {
        for (int v : adjacentVertices) {
            if (v == vertex) return true;
        }
        return false;
    }
}
