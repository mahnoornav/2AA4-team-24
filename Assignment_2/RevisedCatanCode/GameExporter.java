import java.io.FileWriter;
import java.io.IOException;

/**
 * This exports the current game state from Board.java into a JSON file required for
 * the visualizer so it can display the Catan Board correctly.
 */
public class GameExporter {

    public static void export(Board board) {

        try {
            FileWriter writer = new FileWriter("state.json");

            writer.write("{\n");

            // Roads
            writer.write("  \"roads\": [\n");
            boolean firstRoad = true;

            for (Road road : board.getEdges().values()) {

                if (!firstRoad) {
                    writer.write(",\n");
                }

                int edge = road.getEdge();

                // Edge conversion to two separate nodes
                int node1 = edge;
                int node2 = edge + 1;

                writer.write("    { \"a\": " + node1 + ", \"b\": " + node2 + ", \"owner\": \"" +
                        road.getOwner().getPlayerColor().toUpperCase() + "\" }");

                firstRoad = false;
            }
            writer.write("\n  ],\n");

            // Building
            writer.write("  \"buildings\": [\n");

            boolean firstBuilding = true;

            for (Integer vertex : board.getVertices().keySet()) {
                if (!firstBuilding) {
                    writer.write(",\n");
                }

                Structure structure = board.getStructure(vertex);
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

            writer.close();
        }

        catch (IOException e) {
            System.out.println("Error: Cannot export the game state.");
        }
    }
}