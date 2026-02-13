import java.util.HashMap;
import java.util.Map;

/**
 * The Board class represent the Catan game board. It stores all tiles, settlements, and road, and ensures move follow
 * game rules such as the settlement placement distance and road connectivity
 *
 */

public class Board {

    private Tile[] tiles;  // Array to store all 19 tiles

    private Map<Integer, Settlement> vertices;  // Map that stores settlements at each vertex

    private Map<Integer, Road> edges;    // Map that stores settlements at each vertex

    // Maximum number of vertices and edges in a Catan Board
    private final int MAX_Vertices = 54;
    private final int MAX_Edges = 72;

    // Constructor to initialize empty board with tiles, vertices, edges
    public Board() {
        tiles = new Tile[19]; // create array to store 19 tiles

        // Initialize settlement and road maps
        vertices = new HashMap<>();
        edges = new HashMap<>();

        // Intialize tiles setup
        initializeTiles();
    }

    // Intializes tiles with resource types and numbers
    public void initializeTiles() {

        // Resource types assigned to tiles
        ResourceType[] resources = {
                ResourceType.BRICK,
                ResourceType.LUMBER,
                ResourceType.WOOL,
                ResourceType.WHEAT,
                ResourceType.ORE
        };

        // Dice numbers assigned to tiles
        int[] numbers = {
                5, 2, 6, 3, 8,
                10, 9, 12, 11, 4,
                8, 10, 9, 4, 5,
                6, 3, 11, 7
        };
        // Create each tile object and store it in the tiles array
        for (int i = 0; i < tiles.length; i++) {

            ResourceType resource = resources[i % resources.length];
            int number = numbers[i];

            tiles[i] = new Tile(resource, number);
        }
    }

    // Places settlement at give vertex if move is valid
    public void placeSettlement(Player player, int vertex) {

        // Check if vertex is in valid range
        if (vertex < 0 || vertex >= MAX_Vertices) {
            return;
        }
        // Check if vertex is empty
        if (!vertexOpen(vertex)) {
            return;
        }
        // Check distance rule
        if (!distanceRule(vertex)) {
            return;
        }

        // Create new settlement
        Settlement settlement = new Settlement(player, vertex);

        // Store settlement in map
        vertices.put(vertex, settlement);

        // Give player victory point for settlement
        player.addVictoryPoints(1);
    }

    // Places road at give edge if move is valid
    public void placeRoad(Player player, int edge) {

        // Check if edge is in valid range
        if (edge < 0 || edge >= MAX_Edges) {
            return;
        }

        // Check if edge is empty
        if (!edgeOpen(edge)) {
            return;
        }

        // Connectivity rule - player must already have at least one settlment or road
        boolean connected = false;

        // Check if player owns any settlements
        for (int i = 0; i < MAX_Vertices; i++) {

            Settlement s = vertices.get(i);

            if( s != null && s.getOwner() == player) {
                connected = true;
                break;
            }
        }

        // if no settlement is found, check if player owns any road
        if (!connected) {
            for (int i = 0; i < MAX_Edges; i++) {
                Road r = edges.get(i);

                if ( r!=null && r.getRoad() == player) {
                    connected = true;
                    break;
                }
            }
        }
        // if none, placement is invalid
        if (!connected) {
            return;
        }

        // Create new road
        Road road = new Road(player, edge);

        // Store road in edges map
        edges.put(edge, road);
    }

    // Upgrade settlement at given vertex to a city if valid
    public void upgradeCity(Player player, int vertex) {

        Settlement settlement = vertices.get(vertex);

        // Settlement must exist
        if (settlement == null) {
            return;
        }
        // must belong to a player
        if (settlement.getOwner() != player) {
            return;
        }
        // if already city, cant upgrade
        if (settlement.isCity()) {
            return;
        }

        // if valid, upgrade
        settlement.upgrade();
        player.addVictoryPoints(1);     // city gives additional victory point (total 2)

    }

    // Check if vertex has no settlement
    public boolean vertexOpen(int vertex) {
        return !vertices.containsKey(vertex);
    }

    // Check if edge has no road
    public boolean edgeOpen(int edge) {
        return !edges.containsKey(edge);
    }

    // Settlement distance rule - prevent adjacent settlements
    private boolean distanceRule(int vertex) {

        if (vertices.containsKey(vertex)) {
            return false;
        }
        if (vertices.containsKey(vertex - 1)) {
            return false;
        }
        if (vertices.containsKey(vertex + 1)) {
            return false;
        }

        return true;
    }


    // Return tile at a given index
    public Tile getTile(int index) {

        if (index < 0 || index >= tiles.length) {
            return null;
        }

        return tiles[index];
    }

    // Returns first valid vertex for settlement placement
    public int getVertex() {

        for (int i = 0; i < MAX_Vertices; i++) {
            if (vertexOpen(i) && distanceRule(i)) {
                return i;
            }
        }

        return -1;
    }

    // Returns first valid vertex for edge placement
    public int getEdge() {
        for (int i = 0; i < MAX_Edges; i++) {
            if (edgeOpen(i)) {
                return i;
            }
        }

        return -1;
    }

    // Returns settlement at a given vertex
    public Settlement getSettlement(int vertex) {
        return vertices.get(vertex);
    }

    // Returns road at a given edge
    public Road getRoad(int edge) {
        return edges.get(edge);
    }

    // Returns all tiles on the Board
    public Tile[] getTiles() {
        return tiles;
    }

    // Returns all settlements on the Board
    public Map<Integer, Settlement> getVertices() {
        return vertices;
    }

    // Returns all roads on the Board
    public Map<Integer, Road> getEdges() {
        return edges;
    }

}