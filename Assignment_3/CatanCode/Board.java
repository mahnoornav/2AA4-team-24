import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Board class represents the Catan game board. It stores all tiles, settlements, and roads,
 * and ensures moves follow game rules such as settlement placement distance and road connectivity.
 */

// added getPlayerByColor to help with commandParser
// added getOtherPlayers to help with trading for computer. this helps with randomly picking one player to trade with
// implemented board as subject for observer pattern

public class Board {

    private Tile[] tiles;  // Array to store all 19 tiles

    private Map<Integer, Structure> vertices;  // Map that stores structures at each vertex

    private Map<Integer, Road> edges;    // Map that stores roads at each edge

    private List<Player> players; // store all players

    // Observer List
    private List<Observer> observers = new ArrayList<>();

    // Maximum number of vertices and edges in a Catan Board
    private final int MAX_Vertices = 54;
    private final int MAX_Edges = 72;

    // Constructor to initialize empty board with tiles, vertices, edges
    public Board(List<Player> players) {
        tiles = new Tile[19]; // create array to store 19 tiles

        // Initialize settlement and road maps
        vertices = new HashMap<>();
        edges = new HashMap<>();

        this.players = players;
        initializeTiles();     // Initialize tiles setup
    }

    public List<Player> getOtherPlayers(Player current) {
        List<Player> others = new ArrayList<>();
        for (Player p : players) {
            if (!p.equals(current)) {
                others.add(p);
            }
        }
        return others;
    }

    public Player getPlayerByColor(String color) {
        for (Player p : players) {
            if (p.getPlayerColor().equalsIgnoreCase(color)) {
                return p;
            }
        }
        return null;
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
    public boolean placeSettlement(Player player, int vertex) {

        // Check if vertex is in valid range
        if (vertex < 0 || vertex >= MAX_Vertices) {
            return false;
        }

        // Check if vertex is empty
        if (!vertexOpen(vertex)) {
            return false;
        }
        // Check distance rule
        if (!distanceRule(vertex)) {
            return false;
        }

        // Create new settlement
        Structure settlement = new Settlement(player, vertex);

        // Store settlement in map
        vertices.put(vertex, settlement);

        // Give player victory point for settlement
        player.addVictoryPoints(1);

        notifyObservers(); // Notifies observers

        return true;
    }

    // Places a city by upgrading an existing settlement
    public boolean placeCity(Player player, int vertex) {
        Structure structure = vertices.get(vertex);

        if (structure == null) {
            return false;
        }

        if(!(structure instanceof Settlement)) {
            return false;
        }

        if (structure.getOwner() != player) {
            return false;
        }

        City city = new City(player,vertex);
        vertices.put(vertex,city);
        player.addVictoryPoints(1);
        notifyObservers();
        return true;
    }

    public boolean placeRoad(Player player, int edge) {
        // Check if edge is in valid range
        if (edge < 0 || edge >= MAX_Edges) {
            return false;
        }

        // Check if edge is empty
        if (!edgeOpen(edge)) {
            return false;
        }

        boolean connected = false;

        // Check nearby vertices
        for (int v = edge - 1; v <= edge + 1; v++) {

            if (v < 0 || v >= MAX_Vertices) {
                continue;
            }

            Structure s = vertices.get(v);

            if (s != null && s.getOwner() == player) {
                connected = true;
                break;
            }
        }

        // If not connected to players structure - invalid
        if (!connected) {
            return false;
        }

        // Create new road
        Road road = new Road(player, edge);

        // Store road in edges map
        edges.put(edge, road);

        notifyObservers();
        return true;
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
    public int firstValidVertex() {
        for (int i = 0; i < MAX_Vertices; i++) {
            if (vertexOpen(i) && distanceRule(i)) {
                return i;
            }
        }
        return -1;
    }

    // Returns first valid vertex for edge placement
    public int firstValidEdge() {
        for (int i = 0; i < MAX_Edges; i++) {
            if (edgeOpen(i)) {
                return i;
            }
        }
        return -1;
    }



    // Returns structure at a given vertex
    public Structure getStructure(int vertex) {
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
    public Map<Integer, Structure> getVertices() {
        return vertices;
    }

    // Returns all roads on the Board
    public Map<Integer, Road> getEdges() {
        return edges;
    }

    // Observer Methods
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // Undo/redo command manager methods
    public void removeSettlement(Player player, int vertex) {
        Structure s = vertices.get(vertex);
        if (s instanceof Settlement && s.getOwner() == player) {
            vertices.remove(vertex);
            player.addVictoryPoints(-1);
            notifyObservers();
        }
    }

    public void removeRoad(Player player, int edge) {
        Road road = edges.get(edge);
        if (road != null && road.getOwner() == player) {
            edges.remove(edge);
            notifyObservers();
        }
    }

    public void downgradeCity(Player player, int vertex) {
        Structure s = vertices.get(vertex);
        if (s instanceof City && s.getOwner() == player) {
            vertices.put(vertex, new Settlement(player, vertex));
            player.addVictoryPoints(-1);
            notifyObservers();
        }
    }


}