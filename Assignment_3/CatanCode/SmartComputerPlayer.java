import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartComputerPlayer extends ComputerPlayer {

    public SmartComputerPlayer(String color) {
        super(color);
    }

    @Override
    public void executeTurn(Board board, int roundNumber) {
        CommandParser parser = new CommandParser();
        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn (Smart Computer)");

        boolean rolled = false;
        boolean continueTurn = true;

        while (continueTurn) {
            String command = "";

            // Roll first
            if (!rolled) {
                command = "Roll";
                rolled = true;
            } else {
                // Determine next action considering constraints and values
                String action = chooseAction(board);

                // Build settlement if chosen
                if (action.equals("Build settlement")) {
                    int vertex = board.firstValidVertex();
                    if (vertex != -1) {
                        command = "Build settlement " + vertex;
                        buildSettlement();
                    } else {
                        command = "Go"; // no valid spot
                    }

                } 
                // Upgrade settlement to city
                else if (action.equals("Build city")) {
                    int vertexToUpgrade = -1;
                    for (Map.Entry<Integer, Structure> entry : board.getVertices().entrySet()) {
                        Structure s = entry.getValue();
                        if (s instanceof Settlement && s.getOwner() == this) {
                            vertexToUpgrade = entry.getKey();
                            break;
                        }
                    }
                    if (vertexToUpgrade != -1) {
                        command = "Build city " + vertexToUpgrade;
                        buildCity();
                    } else {
                        command = "Go";
                    }

                } 

                // Build road if chosen
                else if (action.equals("Build road")) {
                    int edge = chooseRoadEdge(board); // smart choice using your helper methods
                    if (edge != -1) {
                        command = "Build road " + edge;
                        buildRoad();
                    } else {
                        command = "Go";
                    }

                } 

                // Buy development card if chosen
                else if (action.equals("Buy development card")) {
                    command = "Buy development card";
                    buyDevelopmentCard();
                }

                // Spend excess cards if >7
                else if (getResources().size() > 7) {
                    action = chooseSpendingAction();

                    // build road to spend
                    if (action.equals("Build road")) {
                        int edge = chooseRoadEdge(board);
                        if (edge != -1) {
                            command = "Build road " + edge;
                            buildRoad();
                        } else {
                            command = "Go";
                        }
                    
                    // build settlement to spend
                    } else if (action.equals("Build settlement")) {
                        int vertex = board.firstValidVertex();
                        if (vertex != -1) {
                            command = "Build settlement " + vertex;
                            buildSettlement();
                        } else {
                            command = "Go";
                        }

                    // build city to spend
                    } else if (action.equals("Build city")) {
                        int vertexToUpgrade = -1;
                        for (Map.Entry<Integer, Structure> entry : board.getVertices().entrySet()) {
                            Structure s = entry.getValue();
                            if (s instanceof Settlement && s.getOwner() == this) {
                                vertexToUpgrade = entry.getKey();
                                break;
                            }
                        }
                        if (vertexToUpgrade != -1) {
                            command = "Build city " + vertexToUpgrade;
                            buildCity();
                        } else {
                            command = "Go";
                        }
                    } else {
                        command = "Go";
                    }
                } 

                // If no value move, attempt trades like superclass
                else if (getResources().size() >= 4) {
                    ResourceType offer = null;
                    for (ResourceType r : ResourceType.values()) {
                        if (Collections.frequency(getResources(), r) >= 4) {
                            offer = r;
                            break;
                        }
                    }
                    if (offer != null) {
                        ResourceType request = ResourceType.values()[rand.nextInt(ResourceType.values().length)];
                        System.out.println(getPlayerColor() + " trades with the bank: 4 " + offer + " for 1 " + request);
                        tradeBank(offer, request);
                        command = "Go";
                    } else {
                        command = "Go";
                    }
                } 

                // Trade with other players if possible
                else if (!board.getOtherPlayers(this).isEmpty()) {
                    List<Player> others = board.getOtherPlayers(this);
                    Player target = null;
                    for (Player p : others) {
                        if (p instanceof Trade) {
                            target = p;
                            break;
                        }
                    }
                    if (target != null && !getResources().isEmpty()) {
                        ResourceType offer = getResources().get(0);
                        ResourceType request = ResourceType.values()[rand.nextInt(ResourceType.values().length)];
                        command = "Trade " + target.getPlayerColor() + " " + offer + " " + request;
                    } else {
                        command = "Go";
                    }
                } 

                // End turn if no moves
                else {
                    command = "Go";
                }
            }

            System.out.println("> " + command);
            continueTurn = parser.parse(command, this, board);
        }
    }

    // Chooses computer's next action based on any move contraints and most valuable move to be made
    public String chooseAction(Board board) {
        String constraintAction = handleConstraints(board);
        if (constraintAction != null) return constraintAction;
        return evaluateActions();
    }

    // Checks constraints on move and returns according move (null if no constraint)
    private String handleConstraints(Board board) {
        if (this.getResources().size() > 7) return chooseSpendingAction();
        if (shouldConnectRoads(board) && ValidateMove.canBuildRoad(this)) return "Build road";
        if (protectLongestRoad(board) && ValidateMove.canBuildRoad(this)) return "Build road";
        return null;
    }

    // Calculates move valuable move to be make my player
    public String evaluateActions() {
        Map<String, Double> actionValues = new HashMap<>();
        int totalCards = this.getResources().size();

        // determines values
        if (ValidateMove.canBuildSettlement(this)) {
            double value = 1.0;
            if (totalCards - 4 < 5) value += 0.5;
            actionValues.put("Build settlement", value);
        }
        if (ValidateMove.canBuildCity(this)) {
            double value = 1.0;
            if (totalCards - 5 < 5) value += 0.5;
            actionValues.put("Build city", value);
        }
        if (ValidateMove.canBuildRoad(this)) {
            double value = 0.8;
            if (totalCards - 2 < 5) value += 0.5;
            actionValues.put("Build road", value);
        }
        if (ValidateMove.canBuyDevelopmentCard(this)) {
            double value = 0.8;
            if (totalCards - 3 < 5) value += 0.5;
            actionValues.put("Buy development card", value);
        }

        // skips turn if no valuable move available
        if (actionValues.isEmpty()) return "Go";

        // determines best move
        double bestValue = Collections.max(actionValues.values());
        List<String> topActions = new ArrayList<>();
        for (Map.Entry<String, Double> entry : actionValues.entrySet()) {
            if (entry.getValue() == bestValue) topActions.add(entry.getKey());
        }

        // chooses random move from best moves
        return topActions.get(rand.nextInt(topActions.size()));
    }

    // Determines best way to spend cards if more then 7
    private String chooseSpendingAction() {
        if (ValidateMove.canBuildCity(this)) return "Build city";
        if (ValidateMove.canBuildSettlement(this)) return "Build settlement";
        if (ValidateMove.canBuildRoad(this)) return "Build road";
        if (ValidateMove.canBuyDevelopmentCard(this)) return "Buy development card";
        return "Go";
    }

    // Collect all road numbers
    private List<Integer> getAllRoads(Board board) {
        List<Integer> roads = new ArrayList<>();
        for (Map.Entry<Integer, Road> entry : board.getEdges().entrySet()) {
            if (entry.getValue().getOwner() == this) roads.add(entry.getKey());
        }
        return roads;
    }

    // Calculates length of connected road
    private int getLongestRoadLength(Board board) {
        List<Integer> roads = getAllRoads(board);
        if (roads.isEmpty()) return 0;

        Collections.sort(roads);
        int maxLen = 1;
        int currentLen = 1;

        for (int i = 1; i < roads.size(); i++) {
            // increment connected roads length
            if (roads.get(i) == roads.get(i - 1) + 1) currentLen++;

            // reset to 1 is broken road segment
            else currentLen = 1;

            // update longest length
            if (currentLen > maxLen) maxLen = currentLen;
        }
        return maxLen;
    }

    // Determines if any two roads segments are less then or 2 apart and should be connected
    private boolean shouldConnectRoads(Board board) {
        List<Integer> roads = getAllRoads(board);

        for (int i = 0; i < roads.size(); i++) {
            int edge1 = roads.get(i);

            for (int j = i + 1; j < roads.size(); j++) {
                int edge2 = roads.get(j);

                // Check if edges share a vertex or are at most 2 apart
                if (Math.abs(edge1 - edge2) <= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    // Determines if other players' roads are close to having longest road
    private boolean protectLongestRoad(Board board) {
        int myLongest = getLongestRoadLength(board);

        // loops through other players
        for (Player p : board.getOtherPlayers(this)) {
            int otherCount = 0;

            // loop through all roads on the board
            for (Map.Entry<Integer, Road> entry : board.getEdges().entrySet()) {
                Road r = entry.getValue();
                if (r.getOwner() == p) {
                    otherCount++;
                }
            }

            // if they are close to my longest road, return true (protect it)
            if (otherCount >= myLongest - 1) {
                return true;
            }
        }

        return false;
    }

    // Chooses the best edge number to build, prioritizing connecting segments or protecting longest road
    private int chooseRoadEdge(Board board) {
        List<Integer> roads = getAllRoads(board); // all owned edges

        // try to connect road segments
        if (shouldConnectRoads(board)) {
            // loop through all edges on board
            for (int edge = 0; edge < board.getEdges().size() + 1; edge++) {
                if (board.edgeOpen(edge)) {
                    // Check if this edge touches one of exisiting roads
                    for (int r : roads) {
                        if (Math.abs(r - edge) <= 2) {
                            return edge;
                        }
                    }
                }
            }
        }

        // try to extend longest road
        if (protectLongestRoad(board)) {
            // loop through all edges on board
            for (int edge = 0; edge < board.getEdges().size() + 1; edge++) {
                if (board.edgeOpen(edge)) {
                    // Place next to one of existing roads to extend
                    for (int myEdge : roads) {
                        if (edge == myEdge - 1 || edge == myEdge + 1) {
                            return edge;
                        }
                    }
                }
            }
        }

        // pick first open edge
        return board.firstValidEdge();
    }
        
}