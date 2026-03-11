import java.util.Map;

public interface Trade {
    
    // store info about the current trade
    void proposeTrade(Player receivesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered);

    void tradeBank(ResourceType offer, ResourceType receive);

    void receiveTrade(Player proposer, Map<ResourceType, Integer> requested, Map<ResourceType, Integer> offered);

    void accept();

    void reject();
    
}