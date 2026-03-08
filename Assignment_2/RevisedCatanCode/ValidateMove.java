/**
 * ValidateMove class contains static methods to check whether a player has the required resources to build roads, settlements, or cities.
 */

public class ValidateMove {

    // Check if player can build a road
    public static boolean canBuildRoad(Player player) {
        return player.hasResource(ResourceType.BRICK) &&
               player.hasResource(ResourceType.LUMBER);
    }

    // Check if player can build a settlement
    public static boolean canBuildSettlement(Player player) {
        return player.hasResource(ResourceType.BRICK) &&
               player.hasResource(ResourceType.LUMBER) &&
               player.hasResource(ResourceType.WOOL) &&
               player.hasResource(ResourceType.GRAIN);
    }

    // Check if player can build a city
    public static boolean canBuildCity(Player player) {
        return player.hasResource(ResourceType.ORE) &&
            player.hasResource(ResourceType.ORE) &&
            player.hasResource(ResourceType.ORE) &&
            player.hasResource(ResourceType.GRAIN) &&
            player.hasResource(ResourceType.GRAIN);
    }   
}
