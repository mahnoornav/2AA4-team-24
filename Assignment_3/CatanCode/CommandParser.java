import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class interprets the command line prompts entered by the Human Player */
public class CommandParser {
    private Dice dice = new Dice();

    // one shared undo/redo history
    private static CommandManager manager = new CommandManager();

    public boolean parse(String input, Player player, Board board) {
        input = input.trim();

        // Roll command
        if (input.equalsIgnoreCase("Roll")) {
            int result = dice.roll();
            System.out.println(player.getPlayerColor() + " rolled: " + result);
            return true;
        }

        // Go command
        if (input.equalsIgnoreCase("Go")) {
            System.out.println(player.getPlayerColor() + " ends their turn.");
            return false;
        }

        // List player's information
        if (input.equalsIgnoreCase("List")) {
            System.out.println("Player: " + player.getPlayerColor());
            System.out.println("Victory Points: " + player.getVictoryPoints());
            System.out.println("Resources: " + player.getResources());
            return true;
        }

        // Undo command
        if (input.equalsIgnoreCase("Undo")) {
            manager.undo();
            return true;
        }

        // Redo command
        if (input.equalsIgnoreCase("Redo")) {
            manager.redo();
            return true;
        }

        // Build settlement (vertex)
        Matcher settlement = Pattern.compile("Build settlement (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (settlement.matches()) {
            int vertex = Integer.parseInt(settlement.group(1));

            Command command = new BuildSettlementCommand(board, player, vertex);
            boolean success = manager.executeCommand(command);

            if (success) {
                System.out.println(player.getPlayerColor() + " built a settlement at " + vertex);
            } else {
                System.out.println("Cannot build a settlement at " + vertex);
            }
            return true;
        }

        // Build city (vertex)
        Matcher city = Pattern.compile("Build city (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (city.matches()) {
            int vertex = Integer.parseInt(city.group(1));

            Command command = new BuildCityCommand(board, player, vertex);
            boolean success = manager.executeCommand(command);

            if (success) {
                System.out.println(player.getPlayerColor() + " built a city at " + vertex);
            } else {
                System.out.println("Cannot build a city at " + vertex);
            }
            return true;
        }

        // Build road (edge)
        Matcher road = Pattern.compile("Build road (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (road.matches()) {
            int edge = Integer.parseInt(road.group(1));

            Command command = new BuildRoadCommand(board, player, edge);
            boolean success = manager.executeCommand(command);

            if (success) {
                System.out.println(player.getPlayerColor() + " built a road at " + edge);
            } else {
                System.out.println("Cannot build a road at " + edge);
            }
            return true;
        }

        // Trade with another player
        Matcher tradePlayer = Pattern.compile("Trade (\\w+) (\\w+) (\\w+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (tradePlayer.matches()) {
            String targetColor = tradePlayer.group(1);
            String giveRes = tradePlayer.group(2).toUpperCase();
            String receiveRes = tradePlayer.group(3).toUpperCase();

            Player targetPlayer = board.getPlayerByColor(targetColor);
            if (targetPlayer == null || !(targetPlayer instanceof Trade)) {
                System.out.println("Invalid player to trade with: " + targetColor);
                return true;
            }

            Map<ResourceType, Integer> offered = Map.of(ResourceType.valueOf(giveRes), 1);
            Map<ResourceType, Integer> requested = Map.of(ResourceType.valueOf(receiveRes), 1);

            Trade proposer = (Trade) player;
            proposer.proposeTrade(targetPlayer, requested, offered);

            return false;
        }

        // Trade with bank
        Matcher tradeBank = Pattern.compile("Bank (\\w+) (\\w+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (tradeBank.matches()) {
            String offerResStr = tradeBank.group(1).toUpperCase();
            String receiveResStr = tradeBank.group(2).toUpperCase();

            ResourceType offer = ResourceType.valueOf(offerResStr);
            ResourceType receive = ResourceType.valueOf(receiveResStr);

            if (player.getResourceCount(offer) < 4) {
                System.out.println(player.getPlayerColor() + " doesn't have enough resources to trade with the bank.");
                return true;
            }

            player.tradeBank(offer, receive);
            return true;
        }

        // Invalid command
        System.out.println("Invalid command.");
        return true;
    }
}