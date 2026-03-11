import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer extends Player {

    private Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String color){
        super(color);
    }
    
    @Override
    public void executeTurn(Board board, int roundNumber) {
        CommandParser parser = new CommandParser();

        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn");

        // Display player resources
        System.out.println("Your resources:");
        Map<ResourceType, Integer> counts = new HashMap<>();
        for (ResourceType r : getResources()) {
            counts.put(r, counts.getOrDefault(r, 0) + 1);
        }
        for (Map.Entry<ResourceType,Integer> entry : counts.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }

        // Display other players info
        System.out.println("Other players:");
        for (Player p : board.getOtherPlayers(this)) {
            System.out.println("- " + p.getPlayerColor() + ": " + p.getResources().size() + " total resources");
        }

        displayMenu();

        boolean continueTurn = true;

        while (continueTurn) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            continueTurn = parser.parse(input, this, board);
        }

    }

    public void proposeTrade(Player receivesTrade, Map<ResourceType, Integer> requested, Map<ResourceType, Integer> offered) {
        System.out.println(getPlayerColor() + " proposes a trade to " + receivesTrade.getPlayerColor());
        System.out.println("Offering:");
        for (Map.Entry<ResourceType, Integer> entry : offered.entrySet()) {
            System.out.println("- " + entry.getValue() + " " + entry.getKey());
        }
        System.out.println("Requesting:");
        for (Map.Entry<ResourceType, Integer> entry : requested.entrySet()) {
            System.out.println("- " + entry.getValue() + " " + entry.getKey());
        }

        // let the other player handle acceptance/rejection
        if (receivesTrade instanceof Trade) {
            ((Trade) receivesTrade).receiveTrade(this, requested, offered); 
        }
    }

    public void receiveTrade(Player proposer, Map<ResourceType, Integer> requested, Map<ResourceType, Integer> offered) {
        System.out.println(getPlayerColor());
        for (Map.Entry<ResourceType, Integer> entry : offered.entrySet()) {
            System.out.println("- " + entry.getValue() + " " + entry.getKey());
        }
        System.out.println("for your resources:");
        for (Map.Entry<ResourceType, Integer> entry : requested.entrySet()) {
            System.out.println("- " + entry.getValue() + " " + entry.getKey());
        }

        System.out.print("Do you accept? (yes/no): ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("yes")) {
            accept(getPlayerColor());
        } else {
            reject(getPlayerColor());
        }
    }

    private void displayMenu() {
        // Show available commands
        System.out.println("Available commands:");
        System.out.println("Roll                                 - roll the dice");
        System.out.println("Go                                   - end your turn");
        System.out.println("List                                 - list your resources and points");
        System.out.println("Build settlement <vertex>            - build a settlement");
        System.out.println("Build city <vertex>                  - upgrade a settlement to city");
        System.out.println("Build road <edge>                    - build a road");
        System.out.println("Trade <player> <offer> <request>     - propose a trade to a player");
        System.out.println("Bank <offer> <receive>               - trade 4 resources for 1 with the bank");
    }

}
