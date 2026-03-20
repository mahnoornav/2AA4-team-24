public class BuildCityCommand implements Command {
    private Board board;
    private Player player;
    private int vertex;

    public BuildCityCommand(Board board, Player player, int vertex) {
        this.board = board;
        this.player = player;
        this.vertex = vertex;
    }

    @Override
    public boolean execute() {
        if (!player.canAffordCity()) return false;

        boolean placed = board.placeCity(player, vertex);
        if (placed) {
            player.removeResources(ResourceType.GRAIN, 2);
            player.removeResources(ResourceType.ORE, 3);
            return true;
        }
        return false;
    }

    @Override
    public void undo() {
        board.downgradeCity(player, vertex);
        player.addResources(ResourceType.GRAIN, 2);
        player.addResources(ResourceType.ORE, 3);
    }
}