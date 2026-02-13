package org.catanGen;

// Tile.java
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final ResourceType resource;
    private final int number;
    private final int[] adjacentVertices; // size 6

    public Tile(ResourceType resource, int number, int[] adjacentVertices) {
        // for error checking/ testing to see if the tiles are being generated properly
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

}
