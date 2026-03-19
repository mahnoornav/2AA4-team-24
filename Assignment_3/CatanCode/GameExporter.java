import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameExporter generates a valid state.json file for the visualizer.
 * Observer of Board class.
 */

public class GameExporter implements Observer {

    // Called automatically when board state changes
    @Override
    public void update(Board board) {
        export(board);
    }

    private void export(Board board) {
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