import java.util.HashMap;
import java.util.Map;

/**
 * The Board class represents the Catan game board. It stores all tiles, settlements, and roads, 
 * and ensures moves follow game rules such as settlement placement distance and road connectivity.
 */

public class Board {

    private Tile[] tiles;  // Array to store all 19 tiles

    private Map<Integer, Settlement> vertices;  // Map that stores settlements at each vertex

    private Map<Integer, Road> edges;    // Map that stores roads at each edge

    // Maximum number of vertices and edges in a Catan Board
    private final int MAX_Vertices = 54;
    private final int MAX_Edges = 72;

    // Constructor to initialize empty board with tiles, vertices, edges
    public Board() {
        tiles = new Tile[19]; // create array to store 19 tiles

        // Initialize settlement and road maps
        vertices = new HashMap<>();
        edges = new HashMap<>();

        // Initialize tiles setup
        initializeTiles();
    }

    // Initializes tiles with resource types, numbers, and vertices
    public void initializeTiles() {
        ResourceType[] resources = {
            ResourceType.BRICK, ResourceType.BRICK, ResourceType.GRAIN,             // Row 1
            ResourceType.LUMBER, ResourceType.WOOL, ResourceType.WOOL, ResourceType.DESERT, // Row 2
            ResourceType.GRAIN, ResourceType.ORE, ResourceType.LUMBER, ResourceType.WOOL, ResourceType.LUMBER, // Row 3
            ResourceType.ORE, ResourceType.BRICK, ResourceType.GRAIN, ResourceType.WOOL, // Row 4
            ResourceType.LUMBER, ResourceType.ORE, ResourceType.GRAIN              // Row 5
        };

        int[] numbers = {9, 8, 4, 5, 11, 5, 0, 9, 3, 10, 12, 2, 6, 8, 11, 10, 4, 6, 3};

        // Hardcoded mapping of Tile, 6 Red Node IDs (Clockwise from top)
        int[][] tileVertices = {
            {42, 43, 40, 17, 39, 41}, {44, 45, 43, 18, 17, 40}, {45, 47, 46, 19, 18, 43}, 
            {39, 17, 15, 14, 37, 38}, {17, 18, 16, 5, 15, 17}, {18, 19, 20, 0, 16, 18},
            {19, 46, 48, 49, 22, 20}, {37, 14, 13, 35, 36, 37}, {14, 15, 4, 3, 12, 13},
            {15, 5, 0, 1, 4, 15}, {5, 20, 22, 23, 1, 5}, {22, 49, 50, 51, 23, 22},
            {13, 12, 11, 33, 34, 35}, {12, 3, 2, 9, 10, 11}, {3, 4, 1, 6, 8, 2},
            {1, 23, 52, 53, 7, 6}, {11, 10, 29, 30, 31, 32}, {10, 9, 8, 27, 28, 29},
            {8, 6, 7, 24, 25, 26}};

        // Initialization Loop
        for (int i = 0; i < tiles.length; i++) {
            ResourceType resource = resources[i];
            int number = numbers[i];

            // Create tile and assign its specific vertex IDs
            tiles[i] = new Tile(resource, number);
            tiles[i].setVertices(tileVertices[i]);
        }
    }

    // Places settlement at given vertex if move is valid
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

    // Places road at given edge if move is valid
    public void placeRoad(Player player, int edge) {
        // Check if edge is in valid range
        if (edge < 0 || edge >= MAX_Edges) {
            return;
        }

        // Check if edge is empty
        if (!edgeOpen(edge)) {
            return;
        }

        // Connectivity rule - player must already have at least one settlement or road
        boolean connected = false;

        // Check if player owns any settlements
        for (int i = 0; i < MAX_Vertices; i++) {

            Settlement s = vertices.get(i);

            if (s != null && s.getOwner() == player) {
                connected = true;
                break;
            }
        }

        // if no settlement is found, check if player owns any road
        if (!connected) {
            for (int i = 0; i < MAX_Edges; i++) {
                Road r = edges.get(i);

                if (r != null && r.getOwner() == player) {
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
        // must belong to player
        if (settlement.getOwner() != player) {
            return;
        }
        // if already city, can't upgrade
        if (settlement.getLevel() == "CITY") {
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
