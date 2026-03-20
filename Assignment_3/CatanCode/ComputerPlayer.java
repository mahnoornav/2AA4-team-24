/* */
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player {

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

            // roll first
            if (!rolled) {
                command = "Roll";
                rolled = true;

            } 
            // build settlement if possible
            else if (ValidateMove.canBuildSettlement(this)) {
                int vertex = board.firstValidVertex();
                if (vertex != -1) {
                    command = "Build settlement " + vertex;

                } else {
                    command = "Go"; // no valid place
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

                } else {
                    command = "Go";
                }

            } 
            // build road if possible
            else if (ValidateMove.canBuildRoad(this)) {
                int edge = rand.nextInt(10) + 1; // random edge 1-10
                command = "Build road " + edge;


            } 
            // trade with bank if possible
            else {
                ResourceType bankOffer = null;
                for (ResourceType r : ResourceType.values()) {
                    if (Collections.frequency(getResources(), r) >= 4) {
                        bankOffer = r;
                        break;
                    }
                }

                if (bankOffer != null) {
                    ResourceType request = ResourceType.values()[rand.nextInt(ResourceType.values().length)];
                    System.out.println(getPlayerColor() + " trades with the bank: 4 " + bankOffer + " for 1 " + request);
                    tradeBank(bankOffer, request);
                    command = "Go"; // end turn after bank trade

                } 
                // trade with other players if any
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
                        ResourceType offer = getResources().get(0); // offer first resource
                        ResourceType request = ResourceType.values()[rand.nextInt(ResourceType.values().length)];
                        command = "Trade " + target.getPlayerColor() + " " + offer + " " + request;
                    } else {
                        command = "Go"; // no trade possible
                    }

                } 
                // end turn
                else {
                    command = "Go";
                }
            }

            System.out.println("> " + command);
            continueTurn = parser.parse(command, this, board);

            //try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public void receiveTrade(Player proposer, Map<ResourceType,Integer> requested, Map<ResourceType,Integer> offered) {
        // accept if it has the requested resources
        boolean canAccept = true;
        for (Map.Entry<ResourceType,Integer> entry : requested.entrySet()) {
            if (Collections.frequency(getResources(), entry.getKey()) < entry.getValue()) {
                canAccept = false;
                break;
            }
        }

        if (canAccept) {
            accept(getPlayerColor());
            // exchange resources
            for (Map.Entry<ResourceType,Integer> entry : offered.entrySet()) {
                removeResources(entry.getKey(), entry.getValue());
                proposer.addResources(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<ResourceType,Integer> entry : requested.entrySet()) {
                proposer.removeResources(entry.getKey(), entry.getValue());
                addResources(entry.getKey(), entry.getValue());
            }
            System.out.println(getPlayerColor() + " completed the trade!");
        } else {
            reject(getPlayerColor());
            System.out.println(getPlayerColor() + " rejected the trade!");
        }
    }

    public void proposeTrade(Player receivesTrade, Map<ResourceType,Integer> requested, Map<ResourceType,Integer> offered) {
        System.out.println(getPlayerColor() + " proposes a trade to " + receivesTrade.getPlayerColor());
        System.out.println("Offering: " + offered);
        System.out.println("Requesting: " + requested);

        // accept if it has the requested resources
        boolean canAccept = true;
        for (Map.Entry<ResourceType,Integer> entry : requested.entrySet()) {
            if (Collections.frequency(getResources(), entry.getKey()) < entry.getValue()) {
                canAccept = false;
                break;
            }
        }

        if (canAccept) {
            accept(getPlayerColor());
            // exchange resources
            for (Map.Entry<ResourceType,Integer> entry : offered.entrySet()) {
                removeResources(entry.getKey(), entry.getValue());
                receivesTrade.addResources(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<ResourceType,Integer> entry : requested.entrySet()) {
                receivesTrade.removeResources(entry.getKey(), entry.getValue());
                addResources(entry.getKey(), entry.getValue());
            }
            System.out.println(getPlayerColor() + " completed the trade!");
        } 
        else {
            reject(getPlayerColor());
            System.out.println(getPlayerColor() + " rejected the trade!");
        }
    }

}


    