public class BuildRoadCommand implements Command {
    private Board board;
    private Player player;
    private int edge;

    public BuildRoadCommand(Board board, Player player, int edge) {
        this.board = board;
        this.player = player;
        this.edge = edge;
    }

    @Override
    public boolean execute() {
        if (!ValidateMove.canBuildSettlement(player)) return false;

        boolean placed = board.placeRoad(player, edge);
        if (placed) {
            player.removeResources(ResourceType.BRICK, 1);
            player.removeResources(ResourceType.LUMBER, 1);
            return true;
        }
        return false;
    }

    @Override
    public void undo() {
        board.removeRoad(player, edge);
        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.LUMBER, 1);
    }
}