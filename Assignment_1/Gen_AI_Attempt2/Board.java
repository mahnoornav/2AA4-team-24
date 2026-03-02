
// Board.java
import java.util.*;

public class Board {
    private final List<Tile> tiles; // Tile[*]
    private final Map<Integer, Settlement> vertices; // vertexId -> Settlement
    private final Map<Integer, Road> edges;          // edgeId -> Road

    public Board() {
        this.tiles = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public void placeSettlement(Player player, int vertex) {
        if (!vertexOpen(vertex)) {
            throw new IllegalStateException("Vertex " + vertex + " is not open.");
        }

        // TODO: enforce Catan placement rules (distance rule, etc.)
        Settlement s = new Settlement(player, vertex);
        vertices.put(vertex, s);
        player.addVictoryPoints(1);
    }

    public void placeRoad(Player player, int edge) {
        if (!edgeOpen(edge)) {
            throw new IllegalStateException("Edge " + edge + " is not open.");
        }

        // TODO: enforce road connectivity rule (must connect to player's structure/road)
        Road r = new Road(player, edge);
        edges.put(edge, r);
    }

    public boolean vertexOpen(int vertex) {
        return !vertices.containsKey(vertex);
    }

    public boolean edgeOpen(int edge) {
        return !edges.containsKey(edge);
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    public Settlement getSettlementAtVertex(int vertex) {
        return vertices.get(vertex);
    }

    public Road getRoadAtEdge(int edge) {
        return edges.get(edge);
    }

    /**
     * Returns tiles that touch a given vertex.
     * Useful for resource collection.
     */
    public List<Tile> getAdjacentTiles(int vertex) {
        List<Tile> result = new ArrayList<>();
        for (Tile t : tiles) {
            if (t.touchesVertex(vertex)) {
                result.add(t);
            }
        }
        return result;
    }

    // Helper for setup (you can hard-code the map)
    public void addTile(Tile tile) {
        tiles.add(tile);
    }
}
