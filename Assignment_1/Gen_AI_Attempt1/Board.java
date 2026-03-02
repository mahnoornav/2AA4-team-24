import java.util.HashMap;
import java.util.Map;

public class Board {

    private Tile[] tiles;
    private Map<Integer, Settlement> vertices;
    private Map<Integer, Road> edges;

    public Board() {
        tiles = new Tile[19];
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }

    public void placeSettlement(Player player, int vertex) {
        if (vertexOpen(vertex)) {
            Settlement settlement = new Settlement(player, vertex);
            vertices.put(vertex, settlement);
            player.addVictoryPoints(1);
        }
    }

    public void placeRoad(Player player, int edge) {
        if (edgeOpen(edge)) {
            Road road = new Road(player, edge);
            edges.put(edge, road);
        }
    }

    public boolean vertexOpen(int vertex) {
        return !vertices.containsKey(vertex);
    }

    public boolean edgeOpen(int edge) {
        return !edges.containsKey(edge);
    }

    public Tile getTile(int index) {
        return tiles[index];
    }

    public int getVertex(int vertex) {
        return vertex;
    }

    public int getEdge(int edge) {
        return edge;
    }
}

