import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player{

    public ComputerPlayer(String color) {
        super(color);
    }

    @Override
    public void executeTurn(Board board, int roundNumber) {
        CommandParser parser = new CommandParser();

        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn (Computer)");

        boolean rolled = false;
        boolean continueTurn = true;
        Random rand = new Random();

        while (continueTurn) {
            String command = "";

            // start by rolling
            if (!rolled) {
                command = "Roll";
                rolled = true;
            
            } 

            // build settlement if possible
            else if (ValidateMove.canBuildSettlement(this)) {
                int vertex = board.firstValidVertex();
                if (vertex != -1) {
                    command = "Build settlement " + vertex;
                    buildSettlement(); // consume resources
                }
                else {
                    command = "Go";
                }
                

            } 
            // upgrade settlement to city if possible
            else if (ValidateMove.canBuildCity(this)) {
                int vertexToUpgrade = -1;
                for (Map.Entry<Integer, Structure> entry : board.getVertices().entrySet()) {
                    Structure s = entry.getValue();
                    if (s instanceof Settlement && s.getOwner() == this) {
                        vertexToUpgrade = entry.getKey();
                        break;
                    }
                }

                if (vertexToUpgrade != -1) {
                    command = "Build City " + vertexToUpgrade;
                    buildCity(); 
                }
                else {
                    command = "Go";
                }
                

            } 
            // build road if possible
            else if (ValidateMove.canBuildRoad(this)) {
                int edge = new Dice().roll() % 10 + 1;
                command = "Build road " + edge;
                buildRoad(); // consume resources
            } 
            // trade if possible
            else if (!board.getOtherPlayers(this).isEmpty()) {
                List<Player> others = board.getOtherPlayers(this);
                Player target = others.get(rand.nextInt(others.size()));

                ResourceType[] resTypes = ResourceType.values();
                ResourceType give = resTypes[rand.nextInt(resTypes.length)];
                ResourceType receive = resTypes[rand.nextInt(resTypes.length)];

                command = "Trade " + target.getPlayerColor() + " " + give + " " + receive;
            }

            else {
                command = "Go"; // end turn
            }

            System.out.println("> " + command);
            continueTurn = parser.parse(command, this, board);

            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
    
    
}
