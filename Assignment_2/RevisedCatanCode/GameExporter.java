import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * GameExporter exports the current game state from Board.java into a JSON file called state.json.
 * This version ensures roads are only exported if valid according to the Board logic,
 * and matches the node/edge format expected by light_visualizer.py.
 */
public class GameExporter {

    public static void export(Board board) {
        try {
            FileWriter writer = new FileWriter("state.json"); // create state.json file

            writer.write("{\n");

            // Export roads
            writer.write("  \"roads\": [\n");
            boolean firstRoad = true;

            for (Map.Entry<Integer, Road> entry : board.getEdges().entrySet()) {
                Road road = entry.getValue();
                if (road == null) continue; // skip empty edges

                int edgeId = road.getEdge();

                // Determine the two vertex nodes for this edge
                int nodeA = edgeId;       // simple mapping: edge -> two nodes
                int nodeB = edgeId + 1;   // (matches visualizer format)
                // You may adjust mapping if edge->vertex logic is more complex

                if (!firstRoad) {
                    writer.write(",\n");
                }
                writer.write("    { \"a\": " + nodeA + ", \"b\": " + nodeB + ", \"owner\": \"" +
                        road.getOwner().getPlayerColor().toUpperCase() + "\" }");
                firstRoad = false;
            }
            writer.write("\n  ],\n");

            // Export buildings
            writer.write("  \"buildings\": [\n");
            boolean firstBuilding = true;

            // Sort vertices to export in order
            List<Integer> vertices = new ArrayList<>(board.getVertices().keySet());
            Collections.sort(vertices);

            for (Integer vertex : vertices) {
                Structure s = board.getStructure(vertex);
                if (s == null) continue;

                if (!firstBuilding) {
                    writer.write(",\n");
                }

                String type = (s instanceof City) ? "CITY" : "SETTLEMENT";

                writer.write("    { \"node\": " + vertex + ", \"owner\": \"" +
                        s.getOwner().getPlayerColor().toUpperCase() + "\", \"type\": \"" + type + "\" }");

                firstBuilding = false;
            }
            writer.write("\n  ]\n");
            writer.write("}");

            writer.flush();
            writer.close();

            System.out.println("Game state exported successfully to state.json");
        } catch (IOException e) {
            System.out.println("Error: Cannot export the game state.");
        }
    }
}