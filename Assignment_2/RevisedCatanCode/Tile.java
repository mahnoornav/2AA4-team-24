/**
 * Represents a hexagonal tile on the Catan board.
 * Each tile has a resource type, a dice number, and references to its adjacent board vertices.
 */


public class Tile {
    private final ResourceType resource;
    private final int number;
    private int[] adjacentVertices; // size 6

    public Tile(ResourceType resource, int number) {
        this.resource = resource;
        this.number = number;
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

    public void setVertices (int[] adjacentVertices) {
        this.adjacentVertices = adjacentVertices;

    }

}
