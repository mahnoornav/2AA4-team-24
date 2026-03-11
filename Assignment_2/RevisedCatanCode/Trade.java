import java.util.Collections;
import java.util.Map;

public interface Trade {
    
    // store info about the current trade
    public void proposeTrade(Player recievesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered);

    public void receiveTrade(Player proposer, Map<ResourceType,Integer> requested, Map<ResourceType,Integer> offered);

    private void tradeBank(Player requestsTrade, ResourceType offer, ResourceType recieve) {
    }

    private void accept(){
    }

    private void reject() {
    }
}