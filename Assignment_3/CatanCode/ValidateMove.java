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

    // Check if player can build a city (need 3 ORE, 2 GRAIN)
    public static boolean canBuildCity(Player player) {
        int oreCount = 0;
        int grainCount = 0;

        for (ResourceType resource : player.getResources()) {
            if (resource == ResourceType.ORE) {
                oreCount++;
            }
            else if (resource == ResourceType.GRAIN) {
                grainCount++;
            }
        }

        if (oreCount >= 3 && grainCount >=2) {
            return true;
        }

        return false;
    }
}