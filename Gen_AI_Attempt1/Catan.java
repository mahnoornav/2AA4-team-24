public class Catan {

    private Player[] players;
    private Board board;
    private int currentTurn;
    private int maxRounds;

    public Catan() {
        players = new Player[4];
        players[0] = new Player("Red");
        players[1] = new Player("Blue");
        players[2] = new Player("Green");
        players[3] = new Player("Yellow");

        board = new Board();
        currentTurn = 0;
        maxRounds = 100;
    }

    public void startGame() {
        play();
    }

    public void play() {

        int round = 0;

        while (round < maxRounds && !checkWin(false)) {
            playRound();
            round++;
        }

        displayWinner();
    }

    public void playRound() {

        Player player = players[currentTurn];

        int action = player.takeTurn();

        System.out.println("Player " + player.getUserColor() +
                " performed action: " + action +
                " Victory Points: " + player.getVictoryPoints());

        currentTurn = (currentTurn + 1) % players.length;
    }

    public boolean checkWin(boolean win) {

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];

            if (player.getVictoryPoints() >= 10) {
                return true;
            }
        }

        return false;
    }

    public void displayWinner() {

        Player winner = players[0];

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];

            if (player.getVictoryPoints() > winner.getVictoryPoints()) {
                winner = player;
            }
        }

        System.out.println("Winner is: " + winner.getUserColor() +
                " with " + winner.getVictoryPoints() + " victory points.");
    }
}

