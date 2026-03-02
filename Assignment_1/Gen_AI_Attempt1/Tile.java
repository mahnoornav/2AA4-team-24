import java.util.ArrayList;
import java.util.List;

public class Tile {

    private ResourceType resource;
    private int number;
    private int[] adjacentVertices;

    public Tile(ResourceType resource, int number) {
        this.resource = resource;
        this.number = number;
        this.adjacentVertices = new int[6];
    }

    public int[] getVertices() {
        return adjacentVertices;
    }

    public ResourceType getResource() {
        return resource;
    }

    public int getNumber() {
        return number;
    }

    public List<Tile> getAdjacentTiles(int vertex) {
        return new ArrayList<>();
    }
}


