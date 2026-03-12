import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameExporter {

    public static void export(Board board) {
        try (FileWriter writer = new FileWriter("state.json")) {

            writer.write("{\n");

            // --- Export buildings ---
            writer.write("  \"buildings\": [\n");

            boolean firstBuilding = true;

            // Get all vertices sorted
            List<Integer> vertices = new ArrayList<>(board.getVertices().keySet());
            Collections.sort(vertices);

            for (Integer node : vertices) {
                Structure s = board.getStructure(node);
                if (s == null) continue;

                if (!firstBuilding) writer.write(",\n");

                String type = (s instanceof City) ? "CITY" : "SETTLEMENT";

                writer.write("    { \"node\": " + node + ", \"owner\": \"" + 
                             s.getOwner().getPlayerColor().toUpperCase() + 
                             "\", \"type\": \"" + type + "\" }");

                firstBuilding = false;
            }
            writer.write("\n  ],\n");

            // --- Export roads ---
            writer.write("  \"roads\": [\n");

            boolean firstRoad = true;
            for (Map.Entry<Integer, Road> entry : board.getEdges().entrySet()) {
                Road road = entry.getValue();
                if (road == null) continue;

                if (!firstRoad) writer.write(",\n");

                int edge = entry.getKey();

                // Map edge to its two nodes (for visualizer)
                int nodeA = edge;        // simplistic mapping; adjust if your visualizer expects different
                int nodeB = edge + 1;    // same as above

                writer.write("    { \"a\": " + nodeA + ", \"b\": " + nodeB + ", \"owner\": \"" +
                             road.getOwner().getPlayerColor().toUpperCase() + "\" }");

                firstRoad = false;
            }
            writer.write("\n  ]\n");

            writer.write("}");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error: Cannot export the game state.");
        }
    }
}

/**
 * GameExporter generates a valid state.json for the visualizer.

public class GameExporter {

    public static void export(Board board) {

        try (FileWriter writer = new FileWriter("state.json")) {

            writer.write("{\n");

            // Export buildings first (settlements/cities)
            writer.write("  \"buildings\": [\n");

            Map<Integer, Structure> vertices = board.getVertices();
            boolean firstBuilding = true;

            for (Map.Entry<Integer, Structure> entry : vertices.entrySet()) {
                int node = entry.getKey();
                Structure structure = entry.getValue();
                String owner = structure.getOwner().getPlayerColor().toUpperCase();
                String type = (structure instanceof City) ? "CITY" : "SETTLEMENT";

                if (!firstBuilding) writer.write(",\n");
                writer.write("    { \"node\": " + node + ", \"owner\": \"" + owner + "\", \"type\": \"" + type + "\" }");
                firstBuilding = false;
            }

            writer.write("\n  ],\n");

            // Export roads
            writer.write("  \"roads\": [\n");
            Map<Integer, Road> edges = board.getEdges();
            boolean firstRoad = true;

            for (Map.Entry<Integer, Road> entry : edges.entrySet()) {
                int edgeId = entry.getKey();
                Road road = entry.getValue();
                String owner = road.getOwner().getPlayerColor().toUpperCase();

                // Map edgeId to the actual vertices of the edge
                // Use board logic: edge connects its two vertices from tileVertices
                // We can approximate by using edgeId and firstValidEdge() as placeholders
                int node1 = edgeId;        // valid as long as it's already placed on board
                int node2 = edgeId + 1;    // placeholder

                if (!firstRoad) writer.write(",\n");
                writer.write("    { \"a\": " + node1 + ", \"b\": " + node2 + ", \"owner\": \"" + owner + "\" }");
                firstRoad = false;
            }

            writer.write("\n  ]\n");
            writer.write("}\n");

        } catch (IOException e) {
            System.out.println("Error: Cannot export the game state.");
        }
    }
}

*/