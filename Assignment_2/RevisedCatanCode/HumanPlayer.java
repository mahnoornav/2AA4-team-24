import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String color){
        super(color);
    }

    @Override
    public void executeTurn(Board board, int roundNumber) {

        CommandParser parser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn");

        boolean continueTurn = true;

        while (continueTurn) {
            System.out.print("> ");
            String input = scanner.nextLine();
            continueTurn = parser.parse(input, this, board);
        }

        scanner.close();
    }

}
