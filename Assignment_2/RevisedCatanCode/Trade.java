import java.util.Collections;
import java.util.Map;

public interface Trade {
    
    // store info about the current trade
    public void proposeTrade(Player recievesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered);

    public void receiveTrade(Player proposer, Map<ResourceType,Integer> requested, Map<ResourceType,Integer> offered);

    public void tradeBank(ResourceType offer, ResourceType recieve);

    public default void accept(String color) {
        System.out.println(color + " accepted the trade!");
    }

    public default void reject(String color) {
        System.out.println(color + " rejected the trade!");
    }

}