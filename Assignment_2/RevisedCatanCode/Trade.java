import java.util.Map;

public interface Trade {
    
    // store info about the current trade
    public void proposeTrade(Player receivesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered);

    private void tradeWithBank(Player requestsTrade, ResourceType receive) {

    }

    void receiveTrade(Player proposer, Map<ResourceType, Integer> requested, Map<ResourceType, Integer> offered);
    
    private void accept(){
    }

    private void reject() {
    }
}