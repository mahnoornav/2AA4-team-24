import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameExporter exports the current game state from Board.java into a JSON file called state.json.
 * The state.json is used by the visualizer to display the Catan Board with correct game states.
 */
public class GameExporter {

    public static void export(Board board) {

        try {
            FileWriter writer = new FileWriter("state.json");      // create state.json file and write to it

            writer.write("{\n");

            // Export roads
            writer.write("  \"roads\": [\n");
            boolean firstRoad = true;

            for (Road road : board.getEdges().values()) {

                if (!firstRoad) {
                    writer.write(",\n");
                }

                int edge = road.getEdge();

                // Convert edge into two nodes for visualizer mapping
                int node1 = edge;
                int node2 = edge + 1;

                writer.write("    { \"a\": " + node1 + ", \"b\": " + node2 + ", \"owner\": \"" +
                        road.getOwner().getPlayerColor().toUpperCase() + "\" }");

                firstRoad = false;
            }
            writer.write("\n  ],\n");

            // Export buildings
            writer.write("  \"buildings\": [\n");

            boolean firstBuilding = true;

            // Sort vertices so buildings export in order
            List<Integer> vertices = new ArrayList<>(board.getVertices().keySet());
            Collections.sort(vertices);

            for (Integer vertex : vertices) {
                Structure structure = board.getStructure(vertex);

                if (!firstBuilding) {
                    writer.write(",\n");
                }

                // Determine if structure is a settlement or city
                String structureType;

                if (structure instanceof City) {
                    structureType = "CITY";
                }
                else {
                    structureType = "SETTLEMENT";
                }

                writer.write("    { \"node\": " + vertex + ", \"owner\": \"" + structure.getOwner().getPlayerColor().toUpperCase() +
                        "\", \"type\": \"" + structureType + "\" }");

                firstBuilding = false;
            }
            writer.write("\n  ]\n");
            writer.write("}");

            writer.flush();
            writer.close();
        }

        // Handle any file errors
        catch (IOException e) {
            System.out.println("Error: Cannot export the game state.");
        }
    }
}