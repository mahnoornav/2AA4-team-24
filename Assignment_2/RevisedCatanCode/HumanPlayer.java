import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer extends Player implements Trade {

    private Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String color){
        super(color);
    }

    public void receiveTrade(Player proposer, Map<ResourceType, Integer> requested, Map<ResourceType, Integer> offered) {
        System.out.println(proposer.getPlayerColor() + " offers:");
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
            accept();
        } else {
            reject();
        }
    }

    
    public void proposeTrade(Player receivesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered) {
        System.out.println("Proposing trade to " + receivesTrade.getPlayerColor() + ":");
        System.out.println("Offering: " + resourceOffered);
        System.out.println("Requesting: " + resourceRequested);

        receivesTrade.receiveTrade(this, resourceRequested, resourceOffered);
    }

    public void accept() {
        System.out.println(getPlayerColor() + " accepted the trade!");
    }

    public void reject() {
        System.out.println(getPlayerColor() + " rejected the trade!");
    }

    public void tradeWithBank(ResourceType offer, ResourceType receive) {
        if (Collections.frequency(getResources(), offer) >= 4) {
            removeResources(offer, 4);
            addResources(receive, 1);
            System.out.println(getPlayerColor() + " traded 4 " + offer + " for 1 " + receive + " from the bank.");
        } else {
            System.out.println(getPlayerColor() + " doesn't have enough resources to trade with the bank.");
        }
    }


    @Override
    public void executeTurn(Board board, int roundNumber) {

        CommandParser parser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn");

        boolean continueTurn = true;

        while (continueTurn) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            continueTurn = parser.parse(input, this, board);
        }

        //scanner.close();
    }

}
