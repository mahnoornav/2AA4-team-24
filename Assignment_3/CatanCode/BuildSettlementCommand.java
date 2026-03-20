public class BuildSettlementCommand implements Command {
    private Board board;
    private Player player;
    private int vertex;

    public BuildSettlementCommand(Board board, Player player, int vertex) {
        this.board = board;
        this.player = player;
        this.vertex = vertex;
    }

    @Override
    public boolean execute() {
        if (!player.canAffordSettlement()) return false;

        boolean placed = board.placeSettlement(player, vertex);
        if (placed) {
            player.removeResources(ResourceType.BRICK, 1);
            player.removeResources(ResourceType.LUMBER, 1);
            player.removeResources(ResourceType.WOOL, 1);
            player.removeResources(ResourceType.GRAIN, 1);
            return true;
        }
        return false;
    }

    @Override
    public void undo() {
        board.removeSettlement(player, vertex);
        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.LUMBER, 1);
        player.addResources(ResourceType.WOOL, 1);
        player.addResources(ResourceType.GRAIN, 1);
    }
}