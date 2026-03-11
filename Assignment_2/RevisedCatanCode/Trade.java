import java.util.Map;

public interface Trade {
    
    // store info about the current trade
    public void proposeTrade(Player receivesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered);

    private void tradeBank(Player requestsTrade, ResourceType offer, ResourceType receive) {
    }

    private void accept(){
    }

    private void reject() {
    }
}