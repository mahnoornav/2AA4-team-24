import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class interprets the command line prompts entered by the Human Player */

public class CommandParser {
    private Dice dice = new Dice();

    public boolean parse(String input, Player player, Board board) {
        input = input.trim();

        // Roll command
        if (input.equalsIgnoreCase("Roll")) {
            int result = dice.roll();
            System.out.println(player.getUserColor() + " rolled: " + result);
            return true;
        }

        // Go command
        if (input.equalsIgnoreCase("Go")) {
            System.out.println(player.getUserColor() + " ends their turn.");
            return false;
        }

        // List player's information
        if (input.equalsIgnoreCase("List")) {
            System.out.println("Player: " + player.getUserColor());
            System.out.println("Victory Points: " + player.getVictoryPoints());
            System.out.println("Resources: " + player.getResources());
            return true;
        }

        // Build settlement (vertex)
        Matcher settlement = Pattern.compile("Build settlement (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);

        if (settlement.matches()) {
            int vertex = Integer.parseInt(settlement.group(1));
            board.placeSettlement(player, vertex);
            System.out.println(player.getUserColor() + " built a settlement at " + vertex);
            return true;
        }

        // Build city (vertex)
        Matcher city = Pattern.compile("Build city (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);

        if (city.matches()) {
            int vertex = Integer.parseInt(city.group(1));
            board.placeCity(player, vertex);
            System.out.println(player.getUserColor() + " built a city at " + vertex);
            return true;
        }

        // Build road (edge)
        Matcher road = Pattern.compile("Build road (\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);

        if (road.matches()) {
            int edge = Integer.parseInt(road.group(1));
            board.placeRoad(player, edge);
            System.out.println(player.getUserColor() + " built a road at " + edge);
            return true;
        }

        // Invalid command
        System.out.println("Invalid command.");
        return true;
    }
}